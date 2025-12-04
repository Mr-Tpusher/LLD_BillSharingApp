package controller;

import dto.request.CreateExpenseRequest;
import entity.Expense;
import service.ExpenseService;

public class ExpenseController {
    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    public Expense createExpense(CreateExpenseRequest createExpenseRequest) {
        return expenseService.createExpense(createExpenseRequest);
    }


}
