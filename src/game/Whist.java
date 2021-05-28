package game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("serial")
public class Whist extends CardGame {

    public enum Suit {
        SPADES, HEARTS, DIAMONDS, CLUBS
    }

    public enum Rank {
        // Reverse order of rank importance (see rankGreater() below)
        // Order of cards is tied to card images
        ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO;
    }

    final String trumpImage[] = {"bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif"};

    private final String version = "1.0";

    //configurables
    private int nbPlayers;
    private int nbStartCards;
    private int winningScore;
    private boolean enforceRules;

    HashMap<Boolean, Integer> seedMap;
    private String randomSeed;

    private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");

    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            new Location(650, 575)
    };

    private Actor scoreActor = null;
    private final Location textLocation = new Location(350, 450);
    private ArrayList<Player> players;
    private Round round;

    Font bigFont = new Font("Serif", Font.BOLD, 36);

    PropertiesReader gameProperties = new PropertiesReader();

    PlayerFactory playerFactory = new PlayerFactory();

    /**
     * initialise players and scores on the screen
     * Whist intitalises the players, reading the game properties
     * sends off the info to the factories to produce the desired players
     * then passess off the completed players to round for gameplay
     */
    public void initGame() throws IOException {
        /**
         * takes properties file to set-up game with
         */
        Properties whistProperties = gameProperties.setUpProperties("whist.properties");

        winningScore = Integer.parseInt(whistProperties.getProperty("winningScore"));
        nbPlayers = Integer.parseInt(whistProperties.getProperty("nbPlayers"));
        nbStartCards = Integer.parseInt(whistProperties.getProperty("nbStartCards"));
        enforceRules = Boolean.parseBoolean((whistProperties.getProperty("enforceRules")));
        randomSeed = whistProperties.getProperty("Seed");

        /**
         * pulls random seed from properties file and sets up a hash map
         */
        seedMap = new HashMap<>();

        if (randomSeed == null) {
            seedMap.put(false, 0);
        } else {
            seedMap.put(true, Integer.parseInt(randomSeed));
        }

        /**
         * init players
         */
        players = new ArrayList<>();

        for (int i = 0; i < nbPlayers; i++) {

            String playerType = whistProperties.getProperty("playerType" + i);

            Player player = playerFactory.createPlayer(playerType, i, whistProperties);

            players.add(player);
            scoreActor = new TextActor("0", Color.WHITE, bgColor, bigFont);
            player.setScoreActor(scoreActor);
            addActor(player.getScoreActor(), scoreLocations[i]);
        }
    }

    public Whist() throws IOException {
        super(700, 700, 30);
        setTitle("Whist (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");
        initGame();
        round = new Round(nbStartCards, nbPlayers, winningScore);
        Optional<Integer> winner;
        do {
            round.initRound(deck, players, this, seedMap);
            winner = round.playRound(players, deck, enforceRules, this);
        } while (!winner.isPresent());
        addActor(new Actor("sprites/gameover.gif"), textLocation);
        setStatusText("Game over. Winner is player: " + winner.get());
        refresh();
    }

    public static void main(String[] args) throws IOException {
        new Whist();
    }

}
