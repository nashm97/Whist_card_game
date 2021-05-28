package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public interface ISelectBehaviour {
    /**
     * strategy pattern used for select
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    public Card select(ArrayList<Card> hand, Hand trick, Whist.Suit trumps);

}
