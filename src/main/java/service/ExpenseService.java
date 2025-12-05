package service;

import dto.request.CreateExpenseRequest;
import entity.Expense;
import entity.Group;
import entity.User;
import repository.IExpenseRepo;
import repository.IGroupRepo;
import strategy.IPayerStrategy;
import strategy.ISplitStrategy;
import strategy.PayerStrategyFactory;
import strategy.SplitStrategyFactory;

import java.util.Map;

public class ExpenseService {
    private IExpenseRepo expenseRepo;
    private IGroupRepo groupRepo;

    public ExpenseService(IExpenseRepo expenseRepo, IGroupRepo groupRepo) {
        this.expenseRepo = expenseRepo;
        this.groupRepo = groupRepo;
    }

    public  Expense createExpense(CreateExpenseRequest createExpenseRequest) {

        // populate the participants list if expense is for the group
        Group group = null;
        if (createExpenseRequest.getGroupId() != null) {
            group = groupRepo.get(createExpenseRequest.getGroupId());
            group.getParticipants().forEach(createExpenseRequest::addParticipant);
        }

        IPayerStrategy payerStrategy = PayerStrategyFactory.get(createExpenseRequest.getPaymentStrategyType());
        ISplitStrategy splitStrategy = SplitStrategyFactory.get(createExpenseRequest.getSplitStrategyType());

        Map<User, Double> paidAmounts = payerStrategy.calculatePayerContributions(createExpenseRequest);
        Map<User, Double> splitAmounts = splitStrategy.calculateSplit(createExpenseRequest);

        Expense expense = new Expense(
                createExpenseRequest.getCreator(),
                createExpenseRequest.getDesc(),
                createExpenseRequest.getParticipants(),
                createExpenseRequest.getGroupId(),
                paidAmounts,
                splitAmounts
                );

        expenseRepo.create(expense);

        // add expense in each user's set
        expense.getParticipants().forEach(user -> user.addExpense(expense));

        // if it is a group expense,add it in the group
        if (group != null) {
            group.addExpense(expense);
        }

        return expense;
    }


}
