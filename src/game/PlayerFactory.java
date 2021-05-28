package game;

import java.util.Properties;
import java.util.Random;

/**
 * class takes in player type from properties than send of the filter and select types
 * to the select and filter strategy and ultimatley returns an functioning NPC or User
 */
public class PlayerFactory {

    FilterStrategyFactory filterFactory = new FilterStrategyFactory();
    SelectStrategyFactory selectFactory = new SelectStrategyFactory();

    String filterType;
    String selectType;

    /**
     * receives the properties from Whist to produce playes
     * @param playerType
     * @param playerNumber
     * @param gameProps
     * @return
     */
    public Player createPlayer(String playerType, int playerNumber, Properties gameProps) {

        filterType = gameProps.getProperty("playerFilter" + playerNumber);
        selectType = gameProps.getProperty("playerSelect" + playerNumber);

        IFilterBehaviour filter = filterFactory.createFilterStrategy(filterType);
        ISelectBehaviour select = selectFactory.createSelectStrategy(selectType);

        if ("User".equals(playerType)) {
            System.out.println("player " + playerNumber + " is a " + playerType);
        }
        if ("NPC".equals(playerType)) {
            System.out.println("player "+playerNumber+ " is a "+playerType+" filterType "+filterType+" selectType "+selectType);
        }

        if (playerType == null || playerType.isEmpty())
            return null;
        if ("NPC".equals(playerType)) {
            return new NPC(playerNumber, filter, select);
        } else if ("User".equals(playerType)) {
            return new User(playerNumber);
        }

        return null;
    }
}
