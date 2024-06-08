package com.example.comp2008j_group13_majong.MasterControll;

import com.example.comp2008j_group13_majong.EndScreenController;
import com.example.comp2008j_group13_majong.GameScreenController;
import com.example.comp2008j_group13_majong.Tile.MahjongTileComparator;
import com.example.comp2008j_group13_majong.User.Computer;
import com.example.comp2008j_group13_majong.User.Player;
import com.example.comp2008j_group13_majong.User.PlayerActionObserver;
import com.example.comp2008j_group13_majong.User.User;
import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

import static com.example.comp2008j_group13_majong.Tile.MahjongTile.Suit.发财;

public class GameRules implements PlayerActionObserver {
    public Player humanPlayer;
    public Computer computer1;
    public Computer computer2;
    public Computer computer3;

    public List<Computer> computers;
    private User dealer;
    public ArrayList<User> players;
    private MahjongDeck deck;
    private ArrayList<MahjongTile> remainingTiles;
    public int currentPlayerIndex;
    private MahjongTile lastDiscardedTile;
    public int lastPlayerIndex;


    public int dealerIndex;
    public User lastPlayer;

    private static GameRules gameRules = new GameRules();

    private GameRules() {
        deck = new MahjongDeck();
        remainingTiles = new ArrayList<>();
        initializePlayers();
        dealTiles();
    }

    public static GameRules getInstance() {
        return gameRules;
    }

    private void initializePlayers() {
        ArrayList<MahjongTile> humanPlayerHand = new ArrayList<>();
        ArrayList<MahjongTile> computer1Hand = new ArrayList<>();
        ArrayList<MahjongTile> computer2Hand = new ArrayList<>();
        ArrayList<MahjongTile> computer3Hand = new ArrayList<>();

        humanPlayer = new Player("Player", humanPlayerHand, "South");

        computers = new ArrayList<>();
        computer1 = new Computer("Computer1", computer1Hand, "North");
        computer2 = new Computer("Computer2", computer2Hand, "East");
        computer3 = new Computer("Computer3", computer3Hand, "West");
        computers.add(computer1);
        computers.add(computer2);
        computers.add(computer3);

        humanPlayer.setIndex(3);
        computer1.setIndex(1);
        computer2.setIndex(0);
        computer3.setIndex(2);

        players = new ArrayList<>();
        players.add(humanPlayer);
        players.addAll(computers);

        selectDealer();

        humanPlayer.addObserver(this);
        computer1.addObserver(this);
        computer2.addObserver(this);
        computer3.addObserver(this);
    }


    public void selectDealer() {
        Random random = new Random();
        dealerIndex = random.nextInt(4) ; //0-3

        if (dealerIndex == 3) {
            dealer = humanPlayer;
        } else {
            for (Computer computer : computers) {
                if (computer.getIndex() == dealerIndex) {
                    dealer = computer;
                    break;
                }
            }
        }

        dealer.setTurn(true);
        currentPlayerIndex = dealerIndex;
    }

    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }

    private void dealTiles() {
        deck.shuffle();
        remainingTiles.addAll(deck.getAllTiles());

        for (int i = 0; i < 13; i++) {
            computer3.handTiles.add(remainingTiles.remove(0));
            computer1.handTiles.add(remainingTiles.remove(0));
            computer2.handTiles.add(remainingTiles.remove(0));
            humanPlayer.handTiles.add(remainingTiles.remove(0));
        }

 }
 // Print to test tiles of player and computers
    private void printPlayerHands() {
        MahjongTileComparator comparator = new MahjongTileComparator();
        humanPlayer.handTiles.sort(comparator);

        for (MahjongTile tile : humanPlayer.handTiles) {
            System.out.print(tile.toString() + ", ");
        }
        System.out.println();

        for (MahjongTile tile : dealer.getTiles()) {
            System.out.print(tile.toString() + ", ");
        }
        System.out.println();

        for (int i = 0; i < computers.size(); i++) {
            Computer computer = computers.get(i);
            ArrayList<MahjongTile> hand = getComputerHand(i);
            computer.handTiles.sort(comparator);
            for (MahjongTile tile : hand) {
                System.out.print(tile.toString() + ", ");
            }
            System.out.println();
        }

        // print inorder tiles
        for (int i = 0; i < players.size(); i++) {
            User user = players.get(i);
            ArrayList<MahjongTile[]> inOrderTiles = user.getInOrderTiles();
            for (MahjongTile[] tiles : inOrderTiles) {
                for (MahjongTile tile : tiles) {
                    System.out.print(tile.toString() + ",");
                }
            }
            System.out.println();
        }
    }

    public ArrayList<MahjongTile> getComputerHand(int index) {
        switch (index) {
            case 0:
                return computer1.handTiles;
            case 1:
                return computer2.handTiles;
            case 2:
                return computer3.handTiles;
            default:
                return new ArrayList<>();
        }
    }

    public int getRemainingTilesNumber(){return remainingTiles.size();}


    public void dealerNextRound(GameScreenController gameScreenController) throws IOException {
        if (!remainingTiles.isEmpty()) {
            lastPlayer = current(currentPlayerIndex);
            List<User> players = new ArrayList<>();
            players.add(humanPlayer);
            players.add(computer1);
            players.add(computer2);
            players.add(computer3);
            boolean found = false;
            for (User player : players) {
                if (player != lastPlayer && player.isHu) {
                    currentPlayerIndex = player.getIndex();
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (User player : players) {
                    if (player != lastPlayer && player.isGang) {
                        currentPlayerIndex = player.getIndex();
                        lastPlayerIndex = lastPlayer.index;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    for (User player : players) {
                        if (player != lastPlayer && player.isPeng) {
                            currentPlayerIndex = player.getIndex();
                            lastPlayerIndex = lastPlayer.index;
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                    }
                }
            }

            User currentPlayer = players.stream()
                    .filter(player -> player.getIndex() == currentPlayerIndex)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Invalid player index"));

            if (currentPlayer instanceof Computer) {
                if (currentPlayer.isHu) {
                    gameScreenController.huAction(gameScreenController, currentPlayer, lastPlayer);
                    gameScreenController.handleHu(gameScreenController, currentPlayer);
                    return;
                } else if (currentPlayer.isGang) {
                    gangAction(gameScreenController, currentPlayer, lastPlayer);
                    gameScreenController.animation("gang", currentPlayerIndex);
                } else if (currentPlayer.isPeng) {
                    pengAction(gameScreenController, currentPlayer, lastPlayer);
                    gameScreenController.animation("peng", currentPlayerIndex);
                } else if (currentPlayer.isChi){
                    User last = last(currentPlayerIndex);
                    gameScreenController.animation("chi", currentPlayer.getIndex());
                    MahjongTile chiTile = last.usedTiles.remove(last.usedTiles.size() - 1);
                    currentPlayer.chi(chiTile);
                } else {
                    MahjongTile tile = remainingTiles.remove(0);
                    currentPlayer.ifHu(tile);
                    currentPlayer.handTiles.add(tile);

                    if (currentPlayer.isHu) {
                        currentPlayer.hu(tile);
                        gameScreenController.updateInOrderTiles(currentPlayer.getIndex());
                        currentPlayer.notifyHu();
                        gameScreenController.handleHu(gameScreenController, currentPlayer);
                        return;
                    }
                }

                int discardedTileIndex = new Random().nextInt(currentPlayer.handTiles.size());
                MahjongTile discardedTile = currentPlayer.removeTile(discardedTileIndex);

                gameScreenController.updateUsedTiles(currentPlayer.getIndex());
                gameScreenController.updateUsedTiles(last(currentPlayerIndex).getIndex());
                gameScreenController.updateInOrderTiles(currentPlayer.getIndex());


                PauseTransition pause = new PauseTransition(Duration.seconds(2)); // 2 second interval
                pause.setOnFinished(e -> {
                    try {
                        gameScreenController.autoPlayAction();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                pause.play();

                for (int tempt = 0; tempt < 3; tempt++) {
                    int newIndex = (currentPlayerIndex + tempt) % 4;
                    User nextUser = next(newIndex);
                    nextUser.ifHu(discardedTile);
                    nextUser.ifGang(discardedTile);
                    nextUser.ifPeng(discardedTile);
                }
                next(currentPlayerIndex).ifChi(discardedTile);

            } else {
                if (currentPlayer.isHu) {
                    gameScreenController.setVisible("hu",true);
                }
                else if (currentPlayer.isGang){
                    gameScreenController.setVisible("gang",true);
                    gameScreenController.setVisible("pass",true);

                    gameScreenController.startAnimationForOperation();
                    gameScreenController.timeline.play();
                }
                else if (currentPlayer.isPeng){
                    gameScreenController.setVisible("peng",true);
                    gameScreenController.setVisible("pass",true);

                    gameScreenController.startAnimationForOperation();
                    gameScreenController.timeline.play();
                }
                else if (currentPlayer.isChi){
                    gameScreenController.setVisible("chi",true);
                    gameScreenController.setVisible("pass",true);
                    gameScreenController.startAnimationForOperation();
                    gameScreenController.timeline.play();
                }
                else {
                    MahjongTile tile = remainingTiles.remove(0);
                    currentPlayer.ifHu(tile);
                    currentPlayer.handTiles.add(tile);
                    if (currentPlayer.isHu) {
                        currentPlayer.hu(tile);
                        gameScreenController.updateInOrderTiles(currentPlayer.getIndex());
                        gameScreenController.updateScore();
                        currentPlayer.notifyHu();
                        gameScreenController.handleHu(gameScreenController, currentPlayer);
                    } else {
                        gameScreenController.startAnimationForPlay();
                        gameScreenController.timeline.play();
                    }

                }

            }
        }
    }

    public void pengAction(GameScreenController gameScreenController, User currentPlayer, User lastPlayer) {
        if (currentPlayer.isGang) {
            gangAction(gameScreenController, currentPlayer, lastPlayer);
            return;
        }

        if (currentPlayer.isPeng) {
            gameScreenController.animation("peng", currentPlayer.getIndex());
            MahjongTile pengTile = lastPlayer.usedTiles.get(lastPlayer.usedTiles.size() - 1);
            MahjongTile[] pengTiles = currentPlayer.ifPeng(pengTile);
            if (pengTiles != null) {
                currentPlayer.peng(pengTile);
                for (MahjongTile tile : pengTiles) {
                    currentPlayer.handTiles.remove(tile);
                }

                currentPlayer.inOrderTiles.add(pengTiles);

                gameScreenController.updateInOrderTiles(currentPlayer.getIndex());
                lastPlayer.usedTiles.remove(pengTile);
                gameScreenController.updateUsedTiles( lastPlayer.getIndex());

                if (currentPlayer == humanPlayer) {
                    gameScreenController.updateOnePlayerHand(gameScreenController.playerHandPile,currentPlayer.handTiles);

                    currentPlayer.justPenged = true;
                }

                if(currentPlayer != humanPlayer){
                    MahjongTile discardedTile = currentPlayer.playTiles();
                    gameScreenController.updateUsedTiles(currentPlayer.getIndex());
                    gameScreenController.updateUsedTiles(last(currentPlayerIndex).getIndex());
                    currentPlayerIndex = (lastPlayerIndex + 1)%4;
                    gameScreenController.animation("peng", currentPlayer.getIndex());
                }
            } else {
                System.out.println(currentPlayer.getName() + " does not have valid tiles for peng");
            }
        } else {
            System.out.println(currentPlayer.getName() + " isPeng is false");
        }
    }

    public void gangAction(GameScreenController gameScreenController, User currentPlayer, User lastPlayer) {
        if (currentPlayer.isGang) {
            gameScreenController.animation("gang", currentPlayer.getIndex());
            System.out.println(currentPlayer.getName() + " isGang is true");
            System.out.println(currentPlayerIndex);
            MahjongTile gangTile = lastPlayer.usedTiles.get(lastPlayer.usedTiles.size() - 1);
            System.out.println(currentPlayer.getName() + " is attempting to gang with tile: " + gangTile);
            MahjongTile[] gangTiles = currentPlayer.ifGang(gangTile);
            if (gangTiles != null) {
                System.out.println(currentPlayer.getName() + " has valid tiles for gang: " + Arrays.toString(gangTiles));
                currentPlayer.gang(gangTile);
                System.out.println(currentPlayer.getName() + " executed gang with tiles: " + Arrays.toString(gangTiles));

                for (MahjongTile tile : gangTiles) {
                    currentPlayer.handTiles.remove(tile);
                }

                currentPlayer.inOrderTiles.add(gangTiles);

                gameScreenController.updateInOrderTiles(currentPlayer.getIndex());
                lastPlayer.usedTiles.remove(gangTile);
                gameScreenController.updateUsedTiles( lastPlayer.getIndex());

                if (currentPlayer == humanPlayer) {
                    // 更新真人玩家手牌
                    MahjongTile tile = remainingTiles.remove(0);
                    currentPlayer.handTiles.add(tile);
                    gameScreenController.updateOnePlayerHand(gameScreenController.playerHandPile, currentPlayer.handTiles);
                    currentPlayer.justGangged = true;

                    // 更新真人玩家顺序牌
                    gameScreenController.updateInOrderTiles(currentPlayer.getIndex());
                }

                if(currentPlayer != humanPlayer){
                    int discardedTileIndex = new Random().nextInt(currentPlayer.handTiles.size()-1);
                    MahjongTile discardedTile = currentPlayer.removeTile(discardedTileIndex);
                    gameScreenController.updateUsedTiles(currentPlayer.getIndex());
                    gameScreenController.updateUsedTiles(last(currentPlayerIndex).getIndex());
                    currentPlayerIndex = (lastPlayerIndex +1) % 4;
                    gameScreenController.animation("gang", currentPlayer.getIndex());
                }
            }
        }
    }


    public String getDealerName(){
        int realDealerIndex = (dealerIndex +1 ) % 4;
        return realDealerIndex == 1 ? "North" : realDealerIndex == 0 ? "East" : realDealerIndex == 3 ? "South" : "West";
    }

    public User next(int currentIndex){
        User next = null;
        for (User user : players){
            if (user.getIndex() == (currentIndex + 1 ) % 4){
                next = user;
            }
        }
        return next;
    }

    public User last(int currentIndex) {
        User last = null;
        for (User user : players){
            if (user.getIndex() == (currentIndex + 3) % 4){
                last = user;
            }
        }
        return last;
    }
    public User current(int currentIndex){
        User current = null;
        for (User user : players){
            if (user.getIndex() == currentIndex){
                current = user;
            }
        }
        return current;
    }

    public ArrayList<User> sortPlayers(ArrayList<User> players){
        Collections.sort(players, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Integer.compare(u2.getScore(), u1.getScore());
            }
        });
        return players;
    }

    @Override
    public void onPeng(User player) {
        currentPlayerIndex = player.getIndex();
        player.score += 20;
    }

    @Override
    public void onGang(User player) {
        currentPlayerIndex = player.getIndex();
        player.score += 50;
    }

    @Override
    public void onPass(User player) {
        currentPlayerIndex = player.getIndex();
    }

    @Override
    public void onChi(User player) {
        player.score += 10;
    }

    @Override
    public void onHU(User player) {
        player.score += 100;
    }
}
