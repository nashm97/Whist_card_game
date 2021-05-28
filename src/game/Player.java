package game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.util.*;



public abstract class Player implements IPlayerObservable {
    private Hand hand;
    private int playerNumber;
    private int score;
    private Actor scoreActor;

    private List<IPlayerObserver> observers;

    public Player(int playerNumber) {
        this.hand = null;
        this.playerNumber = playerNumber;
        this.score = 0;
        this.scoreActor = null;
        observers = new ArrayList<>();
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public Actor getScoreActor() {
        return this.scoreActor;
    }

    public void setScoreActor(Actor actor) {
        this.scoreActor = actor;
    }

    public int getScore() {
        return this.score;
    }

    public Hand getHand() {
        return this.hand;
    }

    public void incrementScore() {
        this.score++;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }



    /**
     * methods from observable interface
     */
    @Override
    public void add(IPlayerObserver o) {
        this.observers.add(o);
    }

    @Override
    public void remove(IPlayerObserver o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (IPlayerObserver o : observers) {
            o.update();
        }
    }

    /**
     * prints hand as a string result for the log
     * @return
     */
    public String printHand() {
        String out = "";
        for (int i = 0; i < this.getHand().getCardList().size(); i++) {
            out += this.getHand().getCardList().get(i).toString();
            if (i < this.getHand().getCardList().size() - 1) out += ",";
        }
        return (out);
    }


}
