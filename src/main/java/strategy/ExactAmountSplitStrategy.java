package strategy;

import dto.request.CreateExpenseRequest;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class ExactAmountSplitStrategy implements ISplitStrategy {
    @Override
    public Map<User, Double> calculateSplit(CreateExpenseRequest createExpenseRequest) {
        return Map.of();
    }
}
