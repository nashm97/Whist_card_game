package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;


public class NaiveLegal implements IFilterBehaviour {
    /**
     *
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    @Override
    public ArrayList<Card> filter(Hand hand, Hand trick, Whist.Suit trumps){
        ArrayList<Card> filteredHand= new ArrayList<>();
        if (trick.getCardList().isEmpty()) {
            System.out.println("read");
             for(int i =0; i< hand.getCardList().size();i++) {
                filteredHand.add(hand.getCardList().get(i));
            }
             return filteredHand;
        }
        for(int i =1; i< hand.getCardList().size();i++) {
            if((hand.getCardList().get(i).getSuit() == trumps)||(hand.getCardList().get(i).getSuit() == trick.get(0).getSuit())){
                filteredHand.add(hand.getCardList().get(i));
            }
        }
        if(filteredHand.isEmpty()){
            filteredHand = hand.getCardList();
        }
        return filteredHand;
    }
}
