package dto.request;

import constants.PayerStrategy;
import constants.SplitStrategyType;
import entity.User;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Getter
@ToString
public class CreateExpenseRequest {
    private String desc;
    private User creator;
    private double totalAmount;
    private Set<User> participants;
    private UUID groupId;
    private PayerStrategy payerStrategy;
    private SplitStrategyType splitStrategyType;
    private Map<User, Double> payersMap;
    private Map<User, Double> splitExactAmountMap;
    private Map<User, Double> spiltPercentageMap;

    private CreateExpenseRequest(Builder builder) {
        this.desc = builder.desc;
        this.creator = builder.creator;
        this.totalAmount = builder.totalAmount;
        this.participants = ConcurrentHashMap.newKeySet();
        this.groupId = builder.groupId;
        this.participants.addAll(builder.participants);
        this.payerStrategy = builder.payerStrategy;
        this.splitStrategyType = builder.splitStrategyType;
        this.payersMap = builder.payersMap;
        this.spiltPercentageMap = builder.spiltPercentageMap;
        this.splitExactAmountMap = builder.splitExactAmountMap;
    }

    public void addParticipant(User user) {
        this.participants.add(user);
    }



    public static class Builder {
        private String desc;
        private User creator;
        private double totalAmount;
        private Set<User> participants;
        private UUID groupId;
        private PayerStrategy payerStrategy;
        private SplitStrategyType splitStrategyType;
        private Map<User, Double> payersMap;
        private Map<User, Double> splitExactAmountMap;
        private Map<User, Double> spiltPercentageMap;

        public Builder() {
            participants = ConcurrentHashMap.newKeySet();
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder creator(User creator) {
            this.creator = creator;
            this.participants.add(creator);
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder participant(User user) {
            // thread safe since participants is concurrent set
            this.participants.add(user);
            return this;
        }

        public Builder groupId(UUID groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder paymentStrategyType(PayerStrategy payerStrategy) {
            this.payerStrategy = payerStrategy;
            return this;
        }

        public Builder splitStrategyType(SplitStrategyType splitStrategyType) {
            this.splitStrategyType = splitStrategyType;
            return this;
        }

        public Builder splitExactAmountMap(Map<User, Double> splitExactAmountMap) {
            this.splitExactAmountMap = new HashMap<>(splitExactAmountMap);
            return this;
        }

        public Builder splitPercentageMap(Map<User, Double> spiltPercentageMap) {
            this.spiltPercentageMap = new HashMap<>(spiltPercentageMap);
            return this;
        }

        public Builder payersMap(Map<User, Double> payersMap) {
            this.payersMap = new HashMap<>(payersMap);
            return this;
        }

        public CreateExpenseRequest build() {
            if (this.creator == null) throw new IllegalArgumentException("creator cannot be null");
            if (this.desc == null || this.desc.isBlank()) throw new IllegalArgumentException("description cannot be null");
            if (this.totalAmount <= 0) throw new IllegalArgumentException("amount must be positive");
            if (this.splitStrategyType == null) throw new IllegalArgumentException("splitStrategyType cannot be null");
            if (this.payerStrategy == null) throw new IllegalArgumentException("payerStrategy cannot be null");

            if (this.payerStrategy == PayerStrategy.MULTI && this.payersMap == null)
                throw new IllegalArgumentException(PayerStrategy.MULTI + " requires payersMap");

            if (this.splitStrategyType == SplitStrategyType.EXACT_AMOUNT && splitExactAmountMap == null)
                throw new IllegalArgumentException(SplitStrategyType.EXACT_AMOUNT + " requires splitExactAmountMap");

            if (this.splitStrategyType == SplitStrategyType.PERCENTAGE && this.spiltPercentageMap == null)
                throw new IllegalArgumentException(SplitStrategyType.EXACT_AMOUNT + " requires splitPercentageMap");

            return new CreateExpenseRequest(this);
        }

    }


}
