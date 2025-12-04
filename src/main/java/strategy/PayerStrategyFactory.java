package strategy;

import constants.PaymentStrategyType;

public class PayerStrategyFactory {

    public static IPayerStrategy get(PaymentStrategyType paymentStrategyType) {
        return switch (paymentStrategyType) {
            case SELF -> new SelfPayerStrategy();
            case MULTI -> new MultiPayersStrategy();
        };
    }
}
