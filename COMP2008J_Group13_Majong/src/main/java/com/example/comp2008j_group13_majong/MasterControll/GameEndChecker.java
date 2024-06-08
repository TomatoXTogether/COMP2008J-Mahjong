package com.example.comp2008j_group13_majong.MasterControll;

import com.example.comp2008j_group13_majong.User.Computer;
import com.example.comp2008j_group13_majong.User.Player;
import com.example.comp2008j_group13_majong.User.User;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

// Test to check if game end
public class GameEndChecker {
    private GameRules gameRules;

    public GameEndChecker(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    public static void checkWin(User user) {
        if (user instanceof Player) {
            if (user.ifWin()) {
                System.out.println( " Human win！");
            }
        } else if (user instanceof Computer) {
            if (user.ifWin()) {
                System.out.println(user.getName() + "win!！");
            }
        }
    }

    public static void endGame() {
    }
}
