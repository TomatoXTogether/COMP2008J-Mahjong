package com.example.comp2008j_group13_majong.MasterControll;
import com.example.comp2008j_group13_majong.User.Player;
public class GameController {
    //private Deck deck;
    private TurnManager turnManager;
    private ScoreCalculator scoreCalculator;
    //private List<Player> players;
    public GameController() {
        //this.deck = new Deck();
        this.turnManager = new TurnManager();
        this.scoreCalculator = new ScoreCalculator();
    }

    public void startGame() {
//        initializePlayers();
//        deck.shuffle();
//        dealTiles();
//        playGame();
//        calculateScores();
    }
}
