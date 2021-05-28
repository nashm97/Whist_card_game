package game;

public interface IPlayerObservable {
    /**
     * Observable component of observer pattern used to decouple Player and ScoreGraphic
     * ScoreGraphic observes the players and updates the score
     */

    /**
     *
     * @param o
     */
    void add(IPlayerObserver o);

    /**
     *
     * @param o
     */
    void remove(IPlayerObserver o);

    /**
     *
     */
    void notifyObserver();
}
