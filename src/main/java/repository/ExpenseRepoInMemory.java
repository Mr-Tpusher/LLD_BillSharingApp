package repository;

import entity.Expense;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ExpenseRepoInMemory implements IExpenseRepo {
    private final ConcurrentMap<UUID, Expense> expenses;

    public ExpenseRepoInMemory() {
        this.expenses = new ConcurrentHashMap<>();
    }

    public Expense get(UUID id) {
        return expenses.get(id);
    }

    public Expense create(Expense expense) {
        expenses.put(expense.getId(), expense);
        return expense;
    }
}
