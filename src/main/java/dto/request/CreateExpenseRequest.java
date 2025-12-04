package dto.request;

import constants.PaymentStrategyType;
import constants.SplitStrategyType;
import entity.User;
import lombok.Getter;
import lombok.ToString;

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
    private PaymentStrategyType paymentStrategyType;
    private SplitStrategyType splitStrategyType;

    private CreateExpenseRequest(Builder builder) {
        this.desc = builder.desc;
        this.creator = builder.creator;
        this.totalAmount = builder.totalAmount;
        this.participants = ConcurrentHashMap.newKeySet();
        this.groupId = builder.groupId;
        this.participants.addAll(builder.participants);
        this.paymentStrategyType = builder.paymentStrategyType;
        this.splitStrategyType = builder.splitStrategyType;
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
        private PaymentStrategyType paymentStrategyType;
        private SplitStrategyType splitStrategyType;

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

        public Builder paymentStrategyType(PaymentStrategyType paymentStrategyType) {
            this.paymentStrategyType = paymentStrategyType;
            return this;
        }

        public Builder splitStrategyType(SplitStrategyType splitStrategyType) {
            this.splitStrategyType = splitStrategyType;
            return this;
        }

        public CreateExpenseRequest build() {
            if (this.creator == null) throw new IllegalArgumentException("creator cannot be null");
            if (this.desc == null || this.desc.isBlank()) throw new IllegalArgumentException("description cannot be null");
            if (this.totalAmount <= 0) throw new IllegalArgumentException("amount must be positive");
            if (this.splitStrategyType == null) throw new IllegalArgumentException("splitStrategyType cannot be null");
            if (this.paymentStrategyType == null) throw new IllegalArgumentException("paymentStrategyType cannot be null");
            
            return new CreateExpenseRequest(this);
        }

    }


}
