package PixelQuest;

import javax.swing.*;
import java.awt.*;

public class Coin {
    private int x;
    private int y;
    private boolean collected;
    private Rectangle hitbox;
    private Image coinImage;
    private int value;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = value;
        collected = false;
        coinImage = new ImageIcon("coin.png").getImage();
        hitbox = new Rectangle(x, y, coinImage.getWidth(null), coinImage.getHeight(null));
    }

    public void draw(Graphics g) {
        if (!collected) {
            g.drawImage(coinImage, x, y, null);
        }
    }

    public boolean checkCollision(Rectangle playerHitbox) {
        if (!collected && hitbox.intersects(playerHitbox)) {
            collected = true;
            return true;
        }
        return false;
    }

    public int getValue() {
        return value;
    }

    public boolean isCollected() {
        return collected;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        hitbox.setLocation(x, y);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        hitbox.setLocation(x, y);
    }
}
