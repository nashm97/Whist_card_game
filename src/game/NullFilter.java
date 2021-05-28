package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class NullFilter implements IFilterBehaviour {

    /**
     * used when null is read from properties file and just returns the hand as an ArrayList
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    @Override
    public ArrayList<Card> filter(Hand hand, Hand trick, Whist.Suit trumps) {
        return hand.getCardList();
    }
}
