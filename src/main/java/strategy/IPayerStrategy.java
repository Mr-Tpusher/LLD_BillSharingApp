package strategy;

import dto.request.CreateExpenseRequest;
import entity.User;

import java.util.Map;

public interface IPayerStrategy {
    Map<User, Double> calculatePayerContributions(CreateExpenseRequest expenseRequest);
}
