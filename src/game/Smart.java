package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class Smart implements ISelectBehaviour {
    /**
     *
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    @Override
    public Card select(ArrayList<Card> hand, Hand trick, Whist.Suit trumps){
        // if leading
        if (trick.isEmpty()) {

            // play an ace
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getRankId() == 0) {
                    return hand.get(i);
                }
            }
            // or a king if you can.
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getRankId() == 1) {
                    return hand.get(i);
                }
            }
            // otherwise play low off suit.
            Card lowestOffSuitCard = hand.get(0);
            Boolean selectedNewLowOffSuitCard = Boolean.FALSE;
            for (int i = 0; i < hand.size(); i++) {
                if ((hand.get(i).getRankId() > lowestOffSuitCard.getRankId()) && (hand.get(i).getSuit() != trumps)) {
                    lowestOffSuitCard = hand.get(i);
                    selectedNewLowOffSuitCard = Boolean.TRUE;
                }
            }
            if (selectedNewLowOffSuitCard) {
                return lowestOffSuitCard;
            }
            // otherwise lead with highest trump.
            Card highestTrumpCard = hand.get(0);
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getRankId() < highestTrumpCard.getRankId()) {
                    highestTrumpCard = hand.get(i);
                }
            }
            return highestTrumpCard;
        }
        // if not leading
        // check if there are trumps in the trick
        Boolean areTrumpsInTrick = Boolean.FALSE;
        for (int i = 0; i < trick.getCardList().size(); i++) {
            if (trick.getCardList().get(i).getSuit() == trumps) {
                areTrumpsInTrick = Boolean.TRUE;
            }
        }
        // if there are trumps in the trick
        if (areTrumpsInTrick) {
            // get the highest trump in the trick
            Card highestTrumpInTrick = trick.getCardList().get(0);
            for (int i = 0; i < trick.getCardList().size(); i++) {
                if ((trick.getCardList().get(i).getRankId() < highestTrumpInTrick.getRankId())
                        && (trick.getCardList().get(i).getSuit() == trumps)) {
                    highestTrumpInTrick = trick.getCardList().get(i);
                }
            }
            // get the highest trump in the hand
            Card highestTrumpInHand = hand.get(0);
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getRankId() < highestTrumpInHand.getRankId()) {
                    highestTrumpInHand = hand.get(i);
                }
            }
            // return the highest trump in the hand if it beats the highest trump in the trick
            if (highestTrumpInHand.getCardNumber() > highestTrumpInTrick.getCardNumber()) {
                return highestTrumpInHand;
            } else {
                // otherwise play low off suit.
                Card lowestOffSuitCard = hand.get(0);
                Boolean selectedNewLowOffSuitCard = Boolean.FALSE;
                for (int i = 0; i < hand.size(); i++) {
                    if ((hand.get(i).getRankId() > lowestOffSuitCard.getRankId()) && (hand.get(i).getSuit() != trumps)) {
                        lowestOffSuitCard = hand.get(i);
                        selectedNewLowOffSuitCard = Boolean.TRUE;
                    }
                }
                if(selectedNewLowOffSuitCard){
                    return lowestOffSuitCard;
                }
            }
        }
        return hand.get(0);
    }
}
