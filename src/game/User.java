package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardGame;
import ch.aplu.jcardgame.CardListener;

public class User extends Player {



    public User(int playerNumber) {
        super(playerNumber);
    }

    /**
     *User also extends player yet is given click skills as opposed to filter/select algorithms
     * @param cardgame
     */
    public void clickCapability(CardGame cardgame) {
        this.getHand().setTouchEnabled(true);
        setStatus("Player 0 double-click on card to lead.", cardgame);
    }

    /**
     *
     * @param string
     * @param cardgame
     */
    public void setStatus(String string, CardGame cardgame) {
        cardgame.setStatusText(string);
    }

}