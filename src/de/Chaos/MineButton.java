package de.Chaos;

import javafx.scene.control.Button;

/**
 * Created by Laupwolf on 4/1/15.
 */
public class MineButton extends Button {
    public Button button;
    public boolean bomb;
    public int x;
    public int y;
    public int numberOfBombs;
    public boolean open;

    public MineButton(boolean bomb, int x, int y, int numberOfBombs) {
        super();
        this.bomb = bomb;
        this.x = x;
        this.y = y;
        this.numberOfBombs = numberOfBombs;
        this.open = false;
    }

}
