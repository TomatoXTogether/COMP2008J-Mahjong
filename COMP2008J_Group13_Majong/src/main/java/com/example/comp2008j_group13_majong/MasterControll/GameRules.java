package com.example.comp2008j_group13_majong.MasterControll;

import com.example.comp2008j_group13_majong.GameScreenController;
import com.example.comp2008j_group13_majong.Tile.MahjongTileComparator;
import com.example.comp2008j_group13_majong.User.Computer;
import com.example.comp2008j_group13_majong.User.Player;
import com.example.comp2008j_group13_majong.User.User;
import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;
import javafx.event.ActionEvent;

import java.util.*;

import static com.example.comp2008j_group13_majong.Tile.MahjongTile.Suit.发财;

public class GameRules {
    public Player humanPlayer;
    public Computer computer1;
    public Computer computer2;
    public Computer computer3;

    public List<Computer> computers;
    private User dealer;
    private List<User> players;
    private MahjongDeck deck;
    private ArrayList<MahjongTile> remainingTiles;
    public int currentPlayerIndex;
    private MahjongTile lastDiscardedTile;

    public int dealerIndex;
    private GameEndChecker gameEndChecker;

    public GameRules(ScoreCalculator scoreCalculator) {
        deck = new MahjongDeck();
        remainingTiles = new ArrayList<>();
        initializePlayers();
        dealTiles();
        printPlayerHands();
        this.gameEndChecker = new GameEndChecker(this, scoreCalculator);
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

        // test
        String[] numberValues = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        for (MahjongTile.Suit suit : MahjongTile.Suit.values()) {
            if (suit == MahjongTile.Suit.饼) {
//                for (int index = 1; index < 10; index++) {
//                    String value = numberValues[index - 1];
//                    MahjongTile tile = new MahjongTile(suit, value, index);
//                    computer1.handTiles.add(tile);
//                }
                for (int index = 1; index < 14; index++) {
                    //String value = numberValues[index - 1];
                    MahjongTile t1 = new MahjongTile(MahjongTile.Suit.发财);
                    //MahjongTile t2 = new MahjongTile(MahjongTile.Suit.白板);
                    computer1.handTiles.add(t1);
                }
                humanPlayer.handTiles.add(new MahjongTile(suit, numberValues[0], 1));
                humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.发财));
                humanPlayer.handTiles.add(new MahjongTile(MahjongTile.Suit.发财));
            }
        }
        for (int i = 0; i < 14; i++) {

            computer3.handTiles.add(remainingTiles.remove(0));
            computer2.handTiles.add(remainingTiles.remove(0));
        }
        for(int i = 0; i < 12; i++){
            humanPlayer.handTiles.add(remainingTiles.remove(0));
        }

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

    public ArrayList<MahjongTile> getHumanPlayerHand() {
        return humanPlayer.handTiles;
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

    public ArrayList<MahjongTile> getRemainingTiles() {
        return remainingTiles;
    }

    public int getRemainingTilesNumber(){return remainingTiles.size();}


    public void dealerNextRound(GameScreenController gameScreenController, ActionEvent event) {
        if (!remainingTiles.isEmpty()) {
            // 获取当前玩家
            User currentPlayer = players.stream()
                    .filter(player -> player.getIndex() == currentPlayerIndex)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Invalid player index"));

            // 检查当前玩家是否已经进行了吃、碰或胡牌操作
            if (!currentPlayer.justChi && !currentPlayer.justPenged) {
                // 给当前玩家发一张牌
                MahjongTile tile = remainingTiles.remove(0);
                currentPlayer.handTiles.add(tile);

                // 打印当前玩家信息和收到的牌
                System.out.println(currentPlayer.getName() + " is player " + currentPlayer.getIndex() + ", received: " + tile.getValue() + tile.getSuit());
            } else {
                // 重置标志
                if (currentPlayer.justChi) {
                    currentPlayer.justChi = false;
                }
                if (currentPlayer.justPenged) {
                    currentPlayer.justPenged = false;
                }
            }

            User last = last(currentPlayerIndex);
            // 更新当前玩家的手牌列表
            if (currentPlayer instanceof Computer) {
                //if (currentPlayer.isChi){
                //    MahjongTile chiTile = last.usedTiles.remove(last.usedTiles.size() - 1);
                //    currentPlayer.chi(chiTile);
                //}

                // 电脑从牌堆中随机出一张牌
                int discardedTileIndex = new Random().nextInt(currentPlayer.handTiles.size());
                MahjongTile discardedTile = currentPlayer.removeTile(discardedTileIndex);
                System.out.println(currentPlayer.getName() + " discarded: " + discardedTile.getValue() + discardedTile.getSuit());

                // 在界面上显示这张牌
                gameScreenController.updateUsedTiles(discardedTile, currentPlayer.getIndex());
                gameScreenController.updateUsedTiles(discardedTile, last(currentPlayerIndex).getIndex());
                gameScreenController.updateInOrderTiles(currentPlayer.getIndex());

                //next(currentPlayerIndex).ifChi(discardedTile);

                if (!gameScreenController.huTestAction(event, discardedTile, currentPlayer)) {
                    boolean humanPeng = false;
                    for(int tempt = 0;tempt < 3; tempt ++){
                        int newIndex = (currentPlayerIndex + tempt) % 4;
                        User nextUser = next(newIndex);
                        if(nextUser.ifPeng(discardedTile) != null){
                            if(nextUser == humanPlayer){
                                gameScreenController.peng.setVisible(true);
                                humanPeng = true;
                                //currentPlayerIndex = humanPlayer.index;
                                break;
                            }
                        }
                    }
                    if (!humanPeng){
                        // 更新currentPlayerIndex，使其在0到3之间循环
                        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                    }
                    next(currentPlayerIndex).ifPeng(discardedTile);
                }

            } else {
                // 人类玩家不需要出牌，只更新currentPlayerIndex
                currentPlayerIndex = (currentPlayerIndex + 1) % 4;
            }
            //else {
            //    if (currentPlayer.isChi){
            //        currentPlayer.chi(last(currentPlayerIndex).usedTiles.get(last(currentPlayerIndex).usedTiles.size()-1));
            //    }
                // 更新currentPlayerIndex，使其在0到3之间循环
            //    currentPlayerIndex = (currentPlayerIndex + 1) % 4;
            //}

            // 更新currentPlayerIndex，使其在0到3之间循环
            //currentPlayerIndex = (currentPlayerIndex + 1) % 4;
            printPlayerHands();
        } else {
            gameEndChecker.checkGameEnd();
        }
    }

    public void pengAction(GameScreenController gameScreenController, User currentPlayer, User lastPlayer) {
        if (currentPlayer.isPeng) {
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
                gameScreenController.updateUsedTiles(null, lastPlayer.getIndex());

                if (currentPlayer == humanPlayer) {
                    // 更新真人玩家手牌
                    gameScreenController.updateOnePlayerHand(gameScreenController.playerHandPile, currentPlayer.handTiles);
                    currentPlayer.justPenged = true;
                }

                if(currentPlayer != humanPlayer){
                    //电脑随机出牌
                    int discardedTileIndex = new Random().nextInt(currentPlayer.handTiles.size());
                    MahjongTile discardedTile = currentPlayer.removeTile(discardedTileIndex);
                    gameScreenController.updateUsedTiles(discardedTile, currentPlayer.getIndex());
                    gameScreenController.updateUsedTiles(discardedTile, last(currentPlayerIndex).getIndex());
                    currentPlayerIndex = (currentPlayer.getIndex() +1) % 4;
                }
            } else {
                System.out.println(currentPlayer.getName() + " does not have valid tiles for peng");
            }
        } else {
            System.out.println(currentPlayer.getName() + " isPeng is false");
        }
    }

    public void huAction(GameScreenController gameScreenController, User currentPlayer, User lastPlayer) {
        if (currentPlayer.isHu) {
            MahjongTile huTile = lastPlayer.usedTiles.get(lastPlayer.usedTiles.size() - 1);
            ArrayList<MahjongTile> huTiles = currentPlayer.ifHu(huTile);
            if (huTiles != null) {
                currentPlayer.hu(huTile);

                // 在界面上更新胡操作后的牌
                gameScreenController.updateInOrderTiles(currentPlayer.getIndex());
                lastPlayer.usedTiles.remove(huTile);

                // 在界面上显示赢家
                gameEndChecker.checkWin(currentPlayer);

                // 结束游戏
                gameEndChecker.endGame();
            }
        }
    }

    public MahjongTile getLastDiscardedTile() {
        return lastDiscardedTile;
    }


    private void handleComputerHand(Computer computer, MahjongTile tile) {
        int computerIndex = computers.indexOf(computer);
        switch (computerIndex) {
            case 0:
                computer1.handTiles.add(tile);
                break;
            case 1:
                computer2.handTiles.add(tile);
                break;
            case 2:
                computer3.handTiles.add(tile);
                break;
        }
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
}
