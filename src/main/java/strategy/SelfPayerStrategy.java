package strategy;

import dto.request.CreateExpenseRequest;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class SelfPayerStrategy implements IPayerStrategy {

    @Override
    public Map<User, Double> calculatePayerContributions(CreateExpenseRequest expenseRequest) {
        Map<User, Double> paidAmounts = new HashMap<>();
        paidAmounts.put(expenseRequest.getCreator(), expenseRequest.getTotalAmount());
        return paidAmounts;
    }
}
