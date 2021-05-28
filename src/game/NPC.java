package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import ch.aplu.jgamegrid.Actor;

import java.util.ArrayList;

public class NPC extends Player {

    private IFilterBehaviour filterBehaviour;
    private ISelectBehaviour selectBehaviour;

    public NPC(int playerNumber, IFilterBehaviour filterBehaviour, ISelectBehaviour selectBehaviour){
        super(playerNumber);
        this.filterBehaviour = filterBehaviour;
        this.selectBehaviour = selectBehaviour;
    }

    /**
     * filter assigned from player factory and implements
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    public ArrayList<Card> filter(Hand hand, Hand trick, Whist.Suit trumps) {
        return filterBehaviour.filter(hand, trick, trumps);
    }

    /**
     * select assigned from player/select factory and implements select strategy
     * @param hand
     * @param trick
     * @param trumps
     * @return
     */
    public Card select(ArrayList<Card> hand, Hand trick, Whist.Suit trumps) {
        System.out.println(selectBehaviour);
        return selectBehaviour.select(hand, trick, trumps);
    }

}
