package game;

import ch.aplu.jcardgame.CardGame;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;

import java.awt.Color;
import java.awt.Font;

/**
 * uses observer pattern to watch player and updates the score graphic accordingly
 */
public class ScoreGraphic implements IPlayerObserver {



    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            new Location(650, 575)
    };
    Font bigFont = new Font("Serif", Font.BOLD, 36);

    private Player player;
    private CardGame cardgame;

    public ScoreGraphic(Player player, CardGame cardgame) {
        this.player = player;
        this.cardgame = cardgame;
    }

    /**
     * overriden methods from IPlayerObserver
     */
    @Override
    public void update() {
       this.player.getScore();
       System.out.println(this.player.getPlayerNumber()+":"+this.player.getScore());
       updateScore(this.player, this.cardgame);
    }


    /**
     * updates the score when it is 'notified' by the observable
     * @param player
     * @param cardgame
     */
    private void updateScore(Player player, CardGame cardgame) {
        cardgame.removeActor(player.getScoreActor());
        Actor newActor = new TextActor(String.valueOf(player.getScore()), Color.WHITE, cardgame.bgColor, bigFont);
        player.setScoreActor(newActor);
        cardgame.addActor(player.getScoreActor(), scoreLocations[player.getPlayerNumber()]);
    }
}
