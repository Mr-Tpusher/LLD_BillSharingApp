package strategy;

import dto.request.CreateExpenseRequest;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class MultiPayersStrategy implements IPayerStrategy {
    @Override
    public Map<User, Double> calculatePayerContributions(CreateExpenseRequest expenseRequest) {
        return new HashMap<>(expenseRequest.getPayersMap());
    }
}
