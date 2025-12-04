package strategy;

import dto.request.CreateExpenseRequest;
import entity.User;

import java.util.Map;

public interface ISplitStrategy {

    Map<User, Double> calculateSplit(CreateExpenseRequest createExpenseRequest);

}
