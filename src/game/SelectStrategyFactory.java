package game;

import java.util.Random;

public class SelectStrategyFactory {

    String[] selectTypes = {"randomCard", "highestRank", "smartSelection"};

    public ISelectBehaviour createSelectStrategy(String selectType) {
        if (selectType == null || selectType.isEmpty())
            return new RandomCard();
        if ("random".equals(selectType)) {
            Random r = new Random();
            int randomNumber = r.nextInt(selectTypes.length);
            String selectType2 = selectTypes[randomNumber];
            return createSelectStrategy(selectType2);
        }
        else if ("randomCard".equals(selectType)) {
            return new RandomCard();
        }
        else if ("highestRank".equals(selectType)) {
            return new HighestRank();
        }
        else if ("smartSelection".equals(selectType)) {
            return new Smart();
        }
        return null;
    }
}
