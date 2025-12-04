package strategy;

import constants.SplitStrategyType;

public class SplitStrategyFactory {

    public static ISplitStrategy get(SplitStrategyType splitStrategyType) {
        return switch (splitStrategyType) {
            case EQUAL -> new EqualSplitStrategy();
            case PERCENTAGE -> new PercentageSplitStrategy();
            case EXACT_AMOUNT -> new ExactAmountSplitStrategy();
        };
    }
}
