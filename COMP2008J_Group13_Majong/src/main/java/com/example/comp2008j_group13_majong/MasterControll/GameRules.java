package com.example.comp2008j_group13_majong.MasterControll;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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
        printPlayerHands();
    }

    public static GameRules getInstance() {
        return gameRules;
    }

    private void initializePlayers() {
        ArrayList<MahjongTile> humanPlayerHand = new ArrayList<>();
        ArrayList<MahjongTile> computer1Hand = new ArrayList<>();
        ArrayList<MahjongTile> computer2Hand = new ArrayList<>();
        ArrayList<MahjongTile> computer3Hand = new ArrayList<>();
        // 创建玩家
        humanPlayer = new Player("Player", humanPlayerHand, "South");

        computers = new ArrayList<>();
        computer1 = new Computer("Computer1", computer1Hand, "North");
        computer2 = new Computer("Computer2", computer2Hand, "East");
        computer3 = new Computer("Computer3", computer3Hand, "West");
        computers.add(computer1);
        computers.add(computer2);
        computers.add(computer3);

        // 设置玩家索引
        humanPlayer.setIndex(3);
        computer1.setIndex(1);
        computer2.setIndex(0);
        computer3.setIndex(2);

        // 将所有玩家添加到players列表
        players = new ArrayList<>();
        players.add(humanPlayer);
        players.addAll(computers);

        // 随机选择庄家
        selectDealer();

        humanPlayer.addObserver(this);
        computer1.addObserver(this);
        computer2.addObserver(this);
        computer3.addObserver(this);
    }


    public void selectDealer() {
        Random random = new Random();
        //dealerIndex = random.nextInt(4) ; //0-3
        dealerIndex = 3;

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
        System.out.println("庄家是：" + dealer.getPosition());
        currentPlayerIndex = dealerIndex;
    }

    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }

    private void dealTiles() {
        // 打乱牌堆中的牌的顺序
        deck.shuffle();
        remainingTiles.addAll(deck.getAllTiles());
        System.out.println("剩余牌堆里有： " + remainingTiles.size() + " 张牌");


        // 给每个玩家发放14张随机的牌
        /** for (int i = 0; i < 14; i++) {
         humanPlayer.handTiles.add(remainingTiles.remove(0));
         computer1.handTiles.add(remainingTiles.remove(0));
         computer2.handTiles.add(remainingTiles.remove(0));
         computer3.handTiles.add(remainingTiles.remove(0));
         }**/


//        String[] numberValues = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
//        for (MahjongTile.Suit suit : MahjongTile.Suit.values()) {
//            if (suit == MahjongTile.Suit.饼) {
////                for (int index = 1; index < 7; index++) {
////                    String value = numberValues[index - 1];
////                    MahjongTile tile = new MahjongTile(suit, value, index);
////                    humanPlayer.handTiles.add(tile);
////                }
//                for (int index = 1; index < 13; index++) {
//                    //String value = numberValues[index - 1];
//                    MahjongTile t1 = new MahjongTile(MahjongTile.Suit.白板);
//                    //MahjongTile t2 = new MahjongTile(MahjongTile.Suit.白板);
//                    computer2.handTiles.add(t1);
//                }
//                //computer2.handTiles.add(new MahjongTile(MahjongTile.Suit.白板));
//                //humanPlayer.handTiles.add(new MahjongTile(suit, numberValues[0], 1));
//                humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.发财));
//                humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.发财));
//                humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.白板));
//                humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.白板));
//                //humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.白板));
//                humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.饼,numberValues[8],9));
//                humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.饼,numberValues[8],9));
//
//            }
//        }
        for (int i = 0; i < 13; i++) {
            computer3.handTiles.add(remainingTiles.remove(0));
            computer1.handTiles.add(remainingTiles.remove(0));
            computer2.handTiles.add(remainingTiles.remove(0));
            humanPlayer.handTiles.add(remainingTiles.remove(0));
        }
        /**for(int i = 0; i < 12; i++){
            humanPlayer.handTiles.add(remainingTiles.remove(0));
        }**/

 }

    private void printPlayerHands() {
        MahjongTileComparator comparator = new MahjongTileComparator();
        humanPlayer.handTiles.sort(comparator);

        // 打印玩家的手牌
        System.out.print(humanPlayer.getName() + " 的手牌: ");
        for (MahjongTile tile : humanPlayer.handTiles) {
            System.out.print(tile.toString() + ", ");
        }
        System.out.println();

        // 打印庄家的手牌
        System.out.print(dealer.getName() + " 的手牌: ");
        for (MahjongTile tile : dealer.getTiles()) {
            System.out.print(tile.toString() + ", ");
        }
        System.out.println();

        // 打印电脑玩家的手牌
        for (int i = 0; i < computers.size(); i++) {
            Computer computer = computers.get(i);
            ArrayList<MahjongTile> hand = getComputerHand(i);
            computer.handTiles.sort(comparator);
            System.out.print(computer.getName() + " 的手牌: ");
            for (MahjongTile tile : hand) {
                System.out.print(tile.toString() + ", ");
            }
            System.out.println();
        }

        // print inorder tiles
        for (int i = 0; i < players.size(); i++) {
            User user = players.get(i);
            ArrayList<MahjongTile[]> inOrderTiles = user.getInOrderTiles();
            System.out.print(user.getName() + " 的顺子牌: ");
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


    public void dealerNextRound(GameScreenController gameScreenController) {
        if (!remainingTiles.isEmpty()) {
            int maybeIndex = -1;
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

            // 获取当前玩家
            User currentPlayer = players.stream()
                    .filter(player -> player.getIndex() == currentPlayerIndex)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Invalid player index"));

            // 更新当前玩家的手牌列表
            if (currentPlayer instanceof Computer) {
                if (currentPlayer.isHu) {
                    gameScreenController.huAction(gameScreenController, currentPlayer, lastPlayer);
                    gameScreenController.animation("hu", currentPlayerIndex);
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
                    // 给当前玩家发一张牌
                    MahjongTile tile = remainingTiles.remove(0);
                    currentPlayer.handTiles.add(tile);

                    // 打印当前玩家信息和收到的牌
                    System.out.println(currentPlayer.getName() + " is player " + currentPlayer.getIndex() + ", received: " + tile.getValue() + tile.getSuit());
                }

                // 电脑从牌堆中随机出一张牌
                int discardedTileIndex = new Random().nextInt(currentPlayer.handTiles.size());
                MahjongTile discardedTile = currentPlayer.removeTile(discardedTileIndex);
                System.out.println(currentPlayer.getName() + " discarded: " + discardedTile.getValue() + discardedTile.getSuit());

                // 在界面上显示这张牌
                gameScreenController.updateUsedTiles(currentPlayer.getIndex());
                gameScreenController.updateUsedTiles(last(currentPlayerIndex).getIndex());
                gameScreenController.updateInOrderTiles(currentPlayer.getIndex());


                PauseTransition pause = new PauseTransition(Duration.seconds(2)); // 2 second interval
                pause.setOnFinished(e -> {
                    gameScreenController.autoPlayAction();
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
                System.out.println("现在的玩家index（应该是真人） = " + currentPlayerIndex);
                if (currentPlayer.isHu) {
                    gameScreenController.setVisible("hu",true);
                }
                else if (currentPlayer.isGang){
                    //currentPlayer.chi(last(currentPlayerIndex).usedTiles.get(last(currentPlayerIndex).usedTiles.size()-1));
                    gameScreenController.setVisible("gang",true);
                    gameScreenController.setVisible("pass",true);

                    gameScreenController.startAnimationForOperation();
                    gameScreenController.timeline.play();
                }
                else if (currentPlayer.isPeng){
                    //currentPlayer.chi(last(currentPlayerIndex).usedTiles.get(last(currentPlayerIndex).usedTiles.size()-1));
                    gameScreenController.setVisible("peng",true);
                    gameScreenController.setVisible("pass",true);

                    gameScreenController.startAnimationForOperation();
                    gameScreenController.timeline.play();
                }
                else if (currentPlayer.isChi){
                    //currentPlayer.chi(last(currentPlayerIndex).usedTiles.get(last(currentPlayerIndex).usedTiles.size()-1));
                    gameScreenController.setVisible("chi",true);
                    gameScreenController.setVisible("pass",true);
                    gameScreenController.startAnimationForOperation();
                    gameScreenController.timeline.play();
                }
                else {
                    MahjongTile tile = remainingTiles.remove(0);
                    currentPlayer.handTiles.add(tile);

                    gameScreenController.startAnimationForPlay();
                    gameScreenController.timeline.play();
                }

            }
        }
        printPlayerHands();
    }

    public void pengAction(GameScreenController gameScreenController, User currentPlayer, User lastPlayer) {
        if (currentPlayer.isGang) {
            gangAction(gameScreenController, currentPlayer, lastPlayer);
            return;
        }

        if (currentPlayer.isPeng) {
            gameScreenController.animation("peng", currentPlayer.getIndex());
            System.out.println(currentPlayer.getName() + " isPeng is true");
            MahjongTile pengTile = lastPlayer.usedTiles.get(lastPlayer.usedTiles.size() - 1);
            System.out.println(currentPlayer.getName() + " is attempting to peng with tile: " + pengTile);
            MahjongTile[] pengTiles = currentPlayer.ifPeng(pengTile);
            if (pengTiles != null) {
                System.out.println(currentPlayer.getName() + " has valid tiles for peng: " + Arrays.toString(pengTiles));
                currentPlayer.peng(pengTile);
                System.out.println(currentPlayer.getName() + " executed peng with tiles: " + Arrays.toString(pengTiles));
                for (MahjongTile tile : pengTiles) {
                    currentPlayer.handTiles.remove(tile);
                }

                // 添加到顺序牌中
                currentPlayer.inOrderTiles.add(pengTiles);
                //currentPlayer.inOrderTiles.add(new MahjongTile[]{pengTile});

                // 更新界面上的顺序牌
                gameScreenController.updateInOrderTiles(currentPlayer.getIndex());
                lastPlayer.usedTiles.remove(pengTile);
                gameScreenController.updateUsedTiles( lastPlayer.getIndex());

                if (currentPlayer == humanPlayer) {
                    // 更新真人玩家手牌
                    gameScreenController.updateOnePlayerHand(gameScreenController.playerHandPile,currentPlayer.handTiles);

                    //gameScreenController.startAnimationForPlay();
                    //gameScreenController.timeline.play();

                    currentPlayer.justPenged = true;
                }

                if(currentPlayer != humanPlayer){
                    //电脑随机出牌
                    //int discardedTileIndex = new Random().nextInt(currentPlayer.handTiles.size());
                    //MahjongTile discardedTile = currentPlayer.removeTile(discardedTileIndex);
                    MahjongTile discardedTile = currentPlayer.playTiles();
                    gameScreenController.updateUsedTiles(currentPlayer.getIndex());
                    gameScreenController.updateUsedTiles(last(currentPlayerIndex).getIndex());
                    currentPlayerIndex = (lastPlayerIndex + 1)%4;
                    //currentPlayerIndex = (currentPlayer.getIndex() +1) % 4;
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

                // 添加到顺序牌中
                currentPlayer.inOrderTiles.add(gangTiles);

                // 更新界面上的顺序牌
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
                    //电脑随机出牌
                    int discardedTileIndex = new Random().nextInt(currentPlayer.handTiles.size()-1);
                    MahjongTile discardedTile = currentPlayer.removeTile(discardedTileIndex);
                    gameScreenController.updateUsedTiles(currentPlayer.getIndex());
                    gameScreenController.updateUsedTiles(last(currentPlayerIndex).getIndex());
                    currentPlayerIndex = (lastPlayerIndex +1) % 4;
                    gameScreenController.animation("gang", currentPlayer.getIndex());
                }
            } else {
                System.out.println(currentPlayer.getName() + " does not have valid tiles for gang");
            }
        } else {
            System.out.println(currentPlayer.getName() + " isGang is false");
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
