package service;

import entity.Expense;
import entity.User;
import exception.IncorrectExistingPassword;
import repository.IUserRepo;
import repository.UserRepoInMemory;

import java.util.Set;
import java.util.UUID;


public class UserService {
    private final IUserRepo userRepo;
    private static UserService userService = null;

    private UserService(IUserRepo userRepo) {
       this.userRepo = userRepo;
    }

    public static UserService getInstance() {
        if (userService == null) {
            synchronized (UserService.class) {
                if (userService == null) {
                    IUserRepo userRepo = UserRepoInMemory.getInstance();
                    userService = new UserService(userRepo);
                }
            }
        }
        return userService;
    }


    public User registerUser(String name, String phone, String password) {
        User user = new User(name, phone, password);
        userRepo.save(user);
        return user;
    }


    public boolean updatePassword(UUID userId, String oldPassword, String newPassword) throws IncorrectExistingPassword {
        User user = userRepo.get(userId);
        return user.updatePassword(oldPassword, newPassword);
    }

    public Set<Expense> getAllExpenses(UUID userId) {
        User user = userRepo.get(userId);
        return user.getExpenses();
    }

    public Double currentBalanceAmount(UUID userId) {
        User user = userRepo.get(userId);
        Set<Expense> allExpenses = user.getExpenses();

        double balance = 0;
        for (Expense expense : allExpenses) {
            // if the user has paid amount in this expense, then it is should be subtracted from the current balance
            // negative balance indicates - money to be received.
            balance -= expense.getPaidAmounts().getOrDefault(user, 0.0);

            // contribution has to be paid by all the users,
            // positive since it is outgoing
            balance += expense.getSplitAmounts().getOrDefault(user, 0.0);
        }

        return balance;
    }
}
