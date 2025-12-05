import constants.PaymentStrategyType;
import constants.SplitStrategyType;
import controller.ExpenseController;
import controller.GroupController;
import controller.UserController;
import dto.request.CreateExpenseRequest;
import entity.Expense;
import entity.Group;
import entity.User;
import exception.IncorrectExistingPassword;
import repository.ExpenseRepoInMemory;
import repository.GroupRepoInMemory;
import repository.IExpenseRepo;
import repository.IGroupRepo;
import service.ExpenseService;
import service.GroupService;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        //---------------------------------------------------------------
        // controller, services, repo all should be singleton
        UserController userController = UserController.getInstance();

        IGroupRepo groupRepoInMemory = new GroupRepoInMemory();
        IExpenseRepo expenseRepo = new ExpenseRepoInMemory();
        GroupService groupService = new GroupService(groupRepoInMemory);
        ExpenseService expenseService = new ExpenseService(expenseRepo, groupRepoInMemory);
        GroupController groupController = new GroupController(groupService);
        ExpenseController expenseController = new ExpenseController(expenseService);

        User alan = userController.registerUser("Alan", "1234", "Turing");
        System.out.println(alan);

        try {
            userController.updatePassword(alan.getId(), "Turing", "Enigma");
        } catch (IncorrectExistingPassword e) {
            System.out.println(e.getMessage());
        }

        User charles = userController.registerUser("Charles", "4567", "Babbage");
        System.out.println(charles);

        System.out.println("------------------------------------------------");
        //---------------------------------------------------------------


        // not ideal - just for the testing purpose
        User grace = new User("Grace", "1111", "Hopper");
        User ada = new User("Ada", "2222", "Lovelace");
        User claude = new User("Claude", "3333", "Shannon");

        Group oGs = groupController.createGroup(charles, "OGs",
                List.of(grace, alan, grace, ada, claude));
        System.out.println(oGs);

        System.out.println("------------------------------------------");

        // ------------------------------------------------------------------------------

        double totalAmount = 1000;

        CreateExpenseRequest createExpenseRequest = new CreateExpenseRequest.Builder()
                .creator(alan)
                .desc("dinner with ogs")
                .totalAmount(totalAmount)
                .participant(charles)
                .participant(grace)
                .participant(ada)
                .paymentStrategyType(PaymentStrategyType.SELF)
                .splitStrategyType(SplitStrategyType.EQUAL)
                .build();

        System.out.println("createExpenseRequest = " + createExpenseRequest);

        Expense expense = expenseController.createExpense(createExpenseRequest);
        System.out.println("expense = " + expense);
        System.out.println("----------------------------------------------------");
        //----------------------------------------------------------------------

        CreateExpenseRequest createExpenseRequest1 = new CreateExpenseRequest.Builder()
                .creator(alan)
                .desc("Dinner")
                .groupId(oGs.getId())
                .totalAmount(5000)
                .paymentStrategyType(PaymentStrategyType.SELF)
                .splitStrategyType(SplitStrategyType.EQUAL)
                .build();

        Expense expense1 = expenseController.createExpense(createExpenseRequest1);
        System.out.println(expense1);

        System.out.println(userController.getAllExpense(alan.getId()));

        System.out.println("-----------------------------------------------");


        Set<Expense> expenses = groupController.getAllGroupExpense(alan.getId(), oGs.getId());
        System.out.println(expenses);


    }

}
