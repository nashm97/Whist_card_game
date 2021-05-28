package game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;


import java.util.*;
import java.util.Random;

public class Round {

    private IPlayerObserver observer;

    private Hand[] hands;
    private int nbStartCards;
    private int nbPlayers;
    private final int thinkingTime = 2000;
    public final int winningScore;

    static Random random = new Random();

    final String trumpImage[] = {"bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif"};

    private final Location trickLocation = new Location(350, 350);
    private final int handWidth = 400;
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };

    private final int trickWidth = 40;
    private Location hideLocation = new Location(-500, -500);
    private Location trumpsActorLocation = new Location(50, 50);

    public Round(int nbStartCards, int nbPlayers, int winningScore) {

        this.nbStartCards = nbStartCards;
        this.nbPlayers = nbPlayers;
        this.winningScore = winningScore;
    }

    private Card selected;

    /**
     * receives players that are initialised in Whist and deals them cards
     * also assigns Users double click fucntionality
     * which should be in User but couldnt work out how the adapter worked
     *  @param deck
     * @param players
     * @param cardgame
     * @param seed
     */
    public void initRound(Deck deck, ArrayList<Player> players, CardGame cardgame, HashMap<Boolean, Integer> seed) {
        /**
         * if specified seed, use that
         */
        if(seed.containsKey(true)){
            this.random = new Random((long) seed.get(true));
        }
        else{
            this.random = new Random();
        }

        hands = deck.dealingOut(players.size(), nbStartCards);

        for (int i = 0; i < players.size(); i++) {
            hands[i].sort(Hand.SortType.SUITPRIORITY, true);
            players.get(i).setHand(hands[i]);

            if (players.get(i) instanceof User) {
                int finalI = i;
                CardListener cardListener = new CardAdapter()  // Human Player plays card
                {
                    public void leftDoubleClicked(Card card) {
                        selected = card;
                        players.get(finalI).getHand().setTouchEnabled(false);
                    }
                };
                players.get(i).getHand().addCardListener(cardListener);

            }
        }

        // graphics
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < players.size(); i++) {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            players.get(i).getHand().setView(cardgame, layouts[i]);
            players.get(i).getHand().setTargetArea(new TargetArea(trickLocation));
            players.get(i).getHand().draw();
        }
    }

    /**
     * method probaby to big but it does hold all the relevant information
     * gameplay same as the original code with facored out 'stakeholders'
     * observer watches player to update scoreboard
     * takes information set up in Whist
     *
     * @param players
     * @param deck
     * @param enforceRules
     * @param cardgame
     * @return
     */
    public Optional<Integer> playRound(ArrayList<Player> players, Deck deck, Boolean enforceRules, CardGame cardgame) {  // Returns winner, if any
        // Select and display trump suit
        final Whist.Suit trumps = randomEnum(Whist.Suit.class);
        final Actor trumpsActor = new Actor("sprites/" + trumpImage[trumps.ordinal()]);
        System.out.println(trumpsActor);
        cardgame.addActor(trumpsActor, trumpsActorLocation);
        Hand trick;
        Player winner;
        Card winningCard;
        Whist.Suit lead;

        Player nextPlayer = players.get(random.nextInt(nbPlayers));

        System.out.println("starting player: " + nextPlayer.getPlayerNumber());

        for (int i = 0; i < nbStartCards; i++) {
            trick = new Hand(deck);
            selected = null;
            /**
             * uses casting to use different qualities of Player subclasses
             * had I more time I would have used composition to make both use
             * something like nextPlayer.play()
             */
            if (nextPlayer instanceof User) {
                User userNp = (User) nextPlayer;
                userNp.clickCapability(cardgame);
                while (null == selected) cardgame.delay(100);
            } else {
                NPC npcNp = (NPC) nextPlayer;
                cardgame.setStatusText("Player " + npcNp + " thinking...");
                cardgame.delay(thinkingTime);
                ArrayList<Card> filteredHand = npcNp.filter(nextPlayer.getHand(), trick, trumps);
                selected = npcNp.select(filteredHand, trick, trumps);
            }
            // Lead with selected card
            trick.setView(cardgame, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
            trick.draw();
            selected.setVerso(false);
            // No restrictions on the card being lead
            lead = (Whist.Suit) selected.getSuit();
            selected.transfer(trick, true); // transfer to trick (includes graphic effect)
            winner = nextPlayer;
            winningCard = selected;
            System.out.println("New trick: Lead Player = " + nextPlayer.getPlayerNumber() + ", Lead suit = " + selected.getSuit() + ", Trump suit = " + trumps);
            System.out.println("Player " + nextPlayer.getPlayerNumber() + " play: " + selected.toString() + " from [" + nextPlayer.printHand() + "]");
            // End Lead

            for (int j = 1; j < nbPlayers; j++) {
                if (nextPlayer.getPlayerNumber() >= nbPlayers - 1) {
                    nextPlayer = players.get(0);
                } else {
                    nextPlayer = players.get(nextPlayer.getPlayerNumber() + 1);
                }
                selected = null;
                if (nextPlayer instanceof User) {
                    User userNp = (User) nextPlayer;
                    userNp.clickCapability(cardgame);
                    while (null == selected) cardgame.delay(100);
                } else {
                    NPC npcNp = (NPC) nextPlayer;
                    cardgame.setStatusText("Player " + npcNp + " thinking...");
                    cardgame.delay(thinkingTime);
                    ArrayList<Card> filteredHand = npcNp.filter(nextPlayer.getHand(), trick, trumps);
                    selected = npcNp.select(filteredHand, trick, trumps);
                }
                // Follow with selected card
                trick.setView(cardgame, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
                trick.draw();
                selected.setVerso(false);  // In case it is upside down
                // Check: Following card must follow suit if possible
                if (selected.getSuit() != lead && players.get(nextPlayer.getPlayerNumber()).getHand().getNumberOfCardsWithSuit(lead) > 0) {
                    // Rule violation
                    String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
                    //System.out.println(violation);
                    if (enforceRules)
                        try {
                            throw (new BrokeRuleException(violation));
                        } catch (BrokeRuleException e) {
                            e.printStackTrace();
                            System.out.println("A cheating player spoiled the game!");
                            System.exit(0);
                        }
                }
                // End Check
                System.out.println("player num" + nextPlayer.getPlayerNumber() + "card in hand: " + selected.isInHand(nextPlayer.getHand()));
                selected.transfer(trick, true); // transfer to trick (includes graphic effect)
                System.out.println("Winning card: " + winningCard.toString());
                System.out.println("Player " + nextPlayer.getPlayerNumber() + " play: " + selected.toString() + " from [" + nextPlayer.printHand() + "]");
                if ( // beat current winner with higher card
                        (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
                                // trumped when non-trump was winning
                                (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
                    winner = nextPlayer;
                    winningCard = selected;
                }
                // End Follow
            }
            cardgame.delay(600);
            trick.setView(cardgame, new RowLayout(hideLocation, 0));
            trick.draw();
            nextPlayer = winner;
            System.out.println("Winner: " + winner.getPlayerNumber());
            cardgame.setStatusText("Player " + nextPlayer.getPlayerNumber() + " wins trick.");
            nextPlayer.incrementScore();

            observer = new ScoreGraphic(nextPlayer, cardgame);
            nextPlayer.add(observer);
            nextPlayer.notifyObserver();
            nextPlayer.remove(observer);

            if (winningScore == nextPlayer.getScore()) return Optional.of(nextPlayer.getPlayerNumber());
        }
        cardgame.removeActor(trumpsActor);
        return Optional.empty();
    }

    /**
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * @param card1
     * @param card2
     * @return
     */
    public boolean rankGreater(Card card1, Card card2) {
        return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
    }


}
