package strategy;

import constants.PayerStrategy;

public class PayerStrategyFactory {

    public static IPayerStrategy get(PayerStrategy payerStrategy) {
        return switch (payerStrategy) {
            case SELF -> new SelfPayerStrategy();
            case MULTI -> new MultiPayersStrategy();
        };
    }
}
