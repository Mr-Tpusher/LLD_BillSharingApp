import constants.PaymentStrategyType;
import constants.SplitStrategyType;
import controller.ExpenseController;
import controller.GroupController;
import controller.UserController;
import dto.request.CreateExpenseRequest;
import entity.Group;
import entity.User;
import repository.ExpenseRepoInMemory;
import repository.GroupRepoInMemory;
import repository.IExpenseRepo;
import repository.IGroupRepo;
import service.ExpenseService;
import service.GroupService;

import java.util.List;

public class SettlementMain {
    public static void main(String[] args) throws IllegalAccessException {

        UserController userController = UserController.getInstance();

        IGroupRepo groupRepoInMemory = new GroupRepoInMemory();
        IExpenseRepo expenseRepo = new ExpenseRepoInMemory();
        GroupService groupService = new GroupService(groupRepoInMemory);
        ExpenseService expenseService = new ExpenseService(expenseRepo, groupRepoInMemory);
        GroupController groupController = new GroupController(groupService);
        ExpenseController expenseController = new ExpenseController(expenseService);

        User ada = userController.registerUser("ada", "ada", "ada");
        User alan = userController.registerUser("alan", "alan", "alan");
        User charles = userController.registerUser("charles", "charles", "charles");
        User claude = userController.registerUser("claude", "claude", "claude");
        User grace = userController.registerUser("grace", "grace", "grace");

        Group oGs = groupController.createGroup(alan, "OGs", List.of(ada, alan, charles, claude, grace));


        CreateExpenseRequest createExpenseRequest1 = new CreateExpenseRequest.Builder()
                .creator(ada)
                .desc("First Meet!")
                .totalAmount(1000)
                .groupId(oGs.getId())
                .paymentStrategyType(PaymentStrategyType.SELF)
                .splitStrategyType(SplitStrategyType.EQUAL)
                .build();
        expenseController.createExpense(createExpenseRequest1);

        CreateExpenseRequest expenseRequest2 = new CreateExpenseRequest.Builder()
                .creator(alan)
                .desc("Lunch")
                .totalAmount(2000)
                .groupId(oGs.getId())
                .paymentStrategyType(PaymentStrategyType.SELF)
                .splitStrategyType(SplitStrategyType.EQUAL)
                .build();
        expenseController.createExpense(expenseRequest2);

        expenseController.createExpense(
                new CreateExpenseRequest.Builder()
                        .creator(charles)
                        .desc("Dinner")
                        .totalAmount(5000)
                        .groupId(oGs.getId())
                        .paymentStrategyType(PaymentStrategyType.SELF)
                        .splitStrategyType(SplitStrategyType.EQUAL)
                        .build()
        );

        System.out.println(groupController.getAllGroupExpense(alan.getId(), oGs.getId()));

        System.out.println("--------------------------------------");

        System.out.println(groupController.settleUp(oGs.getId()));

    }
}