package com.example.comp2008j_group13_majong.MasterControll;

import com.example.comp2008j_group13_majong.User.User;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GameEndChecker {
    private User user;
    private GameRules gameRules;
    private ScoreCalculator scoreCalculator;

    public GameEndChecker(GameRules gameRules, ScoreCalculator scoreCalculator) {
        this.gameRules = gameRules;
        this.scoreCalculator = scoreCalculator;
    }

    public void checkGameEnd() {
        // 检查人类玩家是否赢了
        if (checkWin(gameRules.getHumanPlayerHand())) {
            System.out.println(gameRules.getHumanPlayerHand() + " 赢了！");
            endGame();
            return;
        }

        // 检查电脑玩家是否赢了
        for (int i = 0; i < 3; i++) { // 电脑玩家数量为3
            ArrayList<MahjongTile> hand = gameRules.getComputerHand(i);
            if (checkWin(hand)) {
                System.out.println("电脑 " + (i + 1) + " 赢了！");
                endGame();
                return;
            }
        }

        // 检查是否牌堆发光牌无法继续
        if (gameRules.getRemainingTiles().isEmpty()) {
            System.out.println("牌堆已经发光，游戏结束！");
            determineWinner();
            endGame();
        }
    }

    private boolean checkWin(ArrayList<MahjongTile> hand) {
        // 检查手牌是否赢的逻辑
        // 一对加四小组
        return user.isHu(hand);
    }

    private void endGame() {
        // 实现游戏结束的逻辑，例如清理资源、重置游戏等
        System.out.println("游戏结束。");
        // 在这里你可以做一些额外的清理工作或者游戏重置工作
    }

    private void determineWinner() {
        // 计算所有玩家的最终得分
        int humanPlayerScore = scoreCalculator.calculateHandScore(gameRules.getHumanPlayerHand());
        scoreCalculator.updateScore("Human Player", humanPlayerScore);

        for (int i = 0; i < 3; i++) {
            int computerScore = scoreCalculator.calculateHandScore(gameRules.getComputerHand(i));
            scoreCalculator.updateScore("Computer " + (i + 1), computerScore);
        }

        // 获取所有玩家的最终得分
        Map<String, Integer> scores = scoreCalculator.getAllScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        // 找出最高得分的玩家
        String winner = scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No winner");

        System.out.println("赢家是：" + winner);
    }
}
