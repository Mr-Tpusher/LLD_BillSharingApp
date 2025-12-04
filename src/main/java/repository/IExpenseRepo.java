package repository;

import entity.Expense;

import java.util.UUID;

public interface IExpenseRepo {

    Expense get(UUID id);

    Expense create(Expense expense);


}
