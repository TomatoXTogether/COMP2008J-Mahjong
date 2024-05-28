package com.example.comp2008j_group13_majong.Tile;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class TilesDisplay {
    private MahjongTile tile;
    private Image image;

    public void TileDisplay(MahjongTile tile) {
        this.tile = tile;
        Image file = new Image("file:resources\\image\\" + tile.getSuit() + tile.getValue() + ".png", 50, 150, true, true);
        this.image = file;
    }

    public void setDisplay(GridPane parent, int row, int col) {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(50);
        iv.setFitHeight(100); // Adjust dimensions as needed
        parent.add(iv, col, row);
        // Add mouse events or behaviors here if necessary
    }

}
