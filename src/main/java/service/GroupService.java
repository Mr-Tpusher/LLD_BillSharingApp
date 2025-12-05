package service;

import dto.response.Transaction;
import entity.Expense;
import entity.Group;
import entity.User;
import helper.entity.UserBalance;
import repository.IGroupRepo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GroupService {
    // applying dependency inversion principle
    private IGroupRepo groupRepo;

    // Ideally frameworks like spring boot take care of Dependency Injection
    public GroupService(IGroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Group createGroup(User creator, String name, List<User> participants) {
        Group group = new Group(creator, name);

        participants.forEach(user -> {
            group.addParticipant(user);
            user.addGroup(group);
        });

        groupRepo.create(group);
        return group;
    }

    public Set<Expense> getAllGroupExpense(UUID userId, UUID groupId) throws IllegalAccessException {
        Group group = groupRepo.get(groupId);

        boolean doesBelongToGroup = group.getParticipants()
                .stream()
                .anyMatch(user -> user.getId() == userId);

        if (!doesBelongToGroup) {
            throw new IllegalAccessException("user not allowed to query this group.");
        }

        return group.getExpenses();

    }

    public List<Transaction> settleUp(UUID groupId) {
        // 1. Fetch group from the repo
        Group group = groupRepo.get(groupId);
        Set<Expense> allGroupExpenses = group.getExpenses();

        // Every expense has a Map of paid amounts and split contributions.
        // For each user, we have to calculate amount they paid and amount they owe across all expenses.
        // In short, a user will either owe money or has to receive money.
        Map<User, Double> balances = new ConcurrentHashMap<>();

        group.getParticipants().forEach(user -> balances.put(user, 0.0));

        /*
         * Consider paid amounts as positive and split amounts as negative.
         * If A paid 500 and split was 250, then A's balances = 250 - 500 = -250
         * negative balances indicating that A is in loss at the moment, means A has to receive 250.
         *
         * If split was 250, for each user we will say +250, indicating the user owes 250 for this expense.
         *
         *
         * */
        for (Expense expense : allGroupExpenses) {
            // paid amounts -> add to the balances as negative entry
            for (Map.Entry<User, Double> entry : expense.getPaidAmounts().entrySet()) {
                User user = entry.getKey();
                double paidAmount = entry.getValue();
                double balanceAmount = balances.get(user);
                balances.put(user, balanceAmount - paidAmount);
            }

            // split amounts -> add to the balances as positive entry
            for (Map.Entry<User, Double> entry : expense.getSplitAmounts().entrySet()) {
                User user = entry.getKey();
                Double paidAmount = entry.getValue();
                Double balanceAmount = balances.get(user);
                balances.put(user, balanceAmount + paidAmount);
            }
        }


        // Now sort out users with negative balances and positive balances.
        // Users with positive balances will pay to the user with negative balances.


        /*
        In order to minimize the number of transactions, we will pick the User with the highest
        positive balance to pay the user with the lowest negative balance.

        We will use,
        MinHeap -> to store the negative balances and return the min.
        MaxHeap -> to store the positive balances and return the min.

         */

        PriorityQueue<UserBalance> receivers = new PriorityQueue<>(Comparator.comparingDouble(UserBalance::getBalance));
        PriorityQueue<UserBalance> payers = new PriorityQueue<>(Comparator.comparingDouble(UserBalance::getBalance).reversed());

        for (Map.Entry<User, Double> entry : balances.entrySet()) {
            if (entry.getValue() < 0) {
                receivers.add(new UserBalance(entry.getKey(), entry.getValue()));
            } else if (entry.getValue() > 0) {
                payers.add(new UserBalance(entry.getKey(), entry.getValue()));
            }
        }

        // create paying transactions
        List<Transaction> settlementTransactions = new ArrayList<>();

        while (!receivers.isEmpty() && !payers.isEmpty()) {
            UserBalance receiver = receivers.poll();
            UserBalance payer = payers.poll();


            /*
             * 2 cases:
             *
             * 1. A = 1000   B = -1200   -> Payer(A) will be settled after the txn, add B back in receivers with updated balance.
             * 2. A = 1500   B = -1200   -> Receiver(B) will be settle after the txn, add A back in payers with update balance.
             *
             * */

            double txnBalance = payer.getBalance() + receiver.getBalance();
            double txnAmount = Math.min(payer.getBalance(), Math.abs(receiver.getBalance()));

            if (txnBalance < 0) {
                // payer will be settled
                receiver.setBalance(txnBalance);
                receivers.add(receiver);
            } else if (txnBalance > 0) {
                // receiver will be settled
                payer.setBalance(txnBalance);
                payers.add(payer);
            }
            // when txnBalance = 0, both are settled

            //Transaction transaction = new Transaction(payer, receiver, Math.min(payer.getBalance(), Math.abs(receiver.getBalance())))
            Transaction transaction = Transaction.builder()
                    .payer(payer.getUser())
                    .receiver(receiver.getUser())
                    .amount(txnAmount)
                    .build();

            settlementTransactions.add(transaction);

        }

        return settlementTransactions;
    }
}
