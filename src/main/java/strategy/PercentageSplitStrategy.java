package strategy;

import dto.request.CreateExpenseRequest;
import entity.User;

import java.util.Map;

public class PercentageSplitStrategy implements ISplitStrategy {
    @Override
    public Map<User, Double> calculateSplit(CreateExpenseRequest createExpenseRequest) {
        return Map.of();
    }
}
