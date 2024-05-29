package com.example.comp2008j_group13_majong.MasterControll;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreCalculator {
    private Map<String, Integer> playerScores;

    public ScoreCalculator() {
        playerScores = new HashMap<>();
        playerScores.put("Human Player", 0);
        playerScores.put("Computer 1", 0);
        playerScores.put("Computer 2", 0);
        playerScores.put("Computer 3", 0);
    }

    // 初始化玩家分数
    public void initializeScores() {
        playerScores.put("Human Player", 0);
        playerScores.put("Computer 1", 0);
        playerScores.put("Computer 2", 0);
        playerScores.put("Computer 3", 0);
    }

    // 计算手牌的得分
    public int calculateHandScore(ArrayList<MahjongTile> hand) {
        int score = 0;
        // 根据实际麻将规则计算手牌得分的逻辑
        // 例如，根据牌的组合计算得分，这里需要你实现具体的逻辑

        // 示例逻辑：每张牌计1分
        for (MahjongTile tile : hand) {
            score += 1;
        }
        return score;
    }

    // 更新某个玩家的累计得分
    public void updateScore(String playerName, int roundScore) {
        int currentScore = playerScores.getOrDefault(playerName, 0);
        playerScores.put(playerName, currentScore + roundScore);
    }

    // 获取某个玩家的累计得分
    public int getScore(String playerName) {
        return playerScores.getOrDefault(playerName, 0);
    }

    // 获取所有玩家的得分
    public Map<String, Integer> getAllScores() {
        return playerScores;
    }

    // 打印所有玩家的得分
    public void printScores() {
        System.out.println("当前玩家得分：");
        for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
