package game;

import java.util.Random;

public class FilterStrategyFactory {

    String[] filterTypes = {"naiveLegal", "trumpSaving"};


    /**
     * responds to .properties file and called from PlayerFactory to dynamically
     * produce filters
     * @param filterType
     * @return
     */
    public IFilterBehaviour createFilterStrategy(String filterType) {

        if (filterType == null || filterType.isEmpty()) {
            return new NullFilter();
        }
        if ("random".equals(filterType)) {
            Random r = new Random();
            int randomNumber = r.nextInt(filterTypes.length);
            String filterType2 = filterTypes[randomNumber];
            return createFilterStrategy(filterType2);
        }
        if ("naiveLegal".equals(filterType)) {
            return new NaiveLegal();
        }
        else if ("trumpSaving".equals(filterType)) {
            return new TrumpSaving();
        }
        return null;
    }
}
