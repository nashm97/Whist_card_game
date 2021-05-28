package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public interface IFilterBehaviour {

    /**
     * Strategy pattern interface for filter
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    ArrayList<Card> filter(Hand hand, Hand trick, Whist.Suit trumps);
}
