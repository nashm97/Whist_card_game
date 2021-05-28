package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;
import java.util.Random;

public class RandomCard implements ISelectBehaviour {
    /**
     *
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    @Override
    public Card select(ArrayList<Card> hand, Hand trick, Whist.Suit trumps){
        Random rand = new Random(); //instance of random class
        int upperbound = hand.size();
        int randomInt = rand.nextInt(upperbound);
        return hand.get(randomInt);
    }
}
