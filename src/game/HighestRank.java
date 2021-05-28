package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;


public class HighestRank implements ISelectBehaviour {

    /**
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    @Override
    public Card select(ArrayList<Card> hand, Hand trick, Whist.Suit trumps) {
        Card highestRank = hand.get(0);
        int highestRankIndex = 0;
        // get highest ranked card
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRankId() < highestRank.getRankId()) {
                highestRank = hand.get(i);
                highestRankIndex = i;
            }
        }
        // go back over hand and get all cards with same rank
        ArrayList<Card> candidateCards = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRankId() == highestRank.getRankId()) {
                candidateCards.add(hand.get(i));
            }
        }
        // if there are multiple highest cards select the one that is a trump
        for (int i = 0; i < candidateCards.size(); i++) {
            if (candidateCards.get(i).getSuit().equals(trumps)) {
                return candidateCards.get(i);
            }
        }
        return hand.get(highestRankIndex);
    }
}
