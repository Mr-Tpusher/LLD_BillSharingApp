package strategy;

import dto.request.CreateExpenseRequest;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class EqualSplitStrategy implements ISplitStrategy {

    @Override
    public Map<User, Double> calculateSplit(CreateExpenseRequest createExpenseRequest) {
        HashMap<User, Double> split = new HashMap<>();

        double userContribution = createExpenseRequest.getTotalAmount() / createExpenseRequest.getParticipants().size();
        createExpenseRequest.getParticipants().forEach(u -> split.put(u, userContribution));

        return split;
    }
}
