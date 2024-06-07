package com.example.comp2008j_group13_majong.MasterControll;

import com.example.comp2008j_group13_majong.User.Computer;
import com.example.comp2008j_group13_majong.User.Player;
import com.example.comp2008j_group13_majong.User.User;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GameEndChecker {
    private GameRules gameRules;

    public GameEndChecker(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    public static void checkWin(User user) {
        if (user instanceof Player) {
            if (user.ifWin()) {
                System.out.println( " 人类玩家赢了！");
            }
        } else if (user instanceof Computer) {
            if (user.ifWin()) {
                System.out.println(user.getName() + "赢了！");
            }
        }
    }

    public static void endGame() {
        // 实现游戏结束的逻辑，例如清理资源、重置游戏等
        System.out.println("游戏结束。");
        // 在这里你可以做一些额外的清理工作或者游戏重置工作
    }
}
