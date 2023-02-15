package PixelQuest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Game extends JPanel implements KeyListener {
    private int playerX = 50;
    private int playerY = 50;
    private BufferedImage playerTexture;
    private BufferedImage backgroundImage;
    private int screenWidth;
    private int screenHeight;

    private ArrayList<Coin> coins = new ArrayList<Coin>();
    private BufferedImage coinTexture;
    private int coinsCollected = 0;

    public Game() {
        JFrame frame = new JFrame("My Game");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.addKeyListener(this);

        try {
            playerTexture = ImageIO.read(new File("image/player.png"));
            backgroundImage = ImageIO.read(new File("image/background.png"));
            coinTexture = ImageIO.read(new File("image/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        screenWidth = frame.getContentPane().getWidth();
        screenHeight = frame.getContentPane().getHeight();

        // Add some coins to the game
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * (screenWidth - 50));
            int y = (int) (Math.random() * (screenHeight - 50));
            coins.add(new Coin(x, y));
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(playerTexture, playerX, playerY, null);

        // Draw the coins on the screen
        for (Coin coin : coins) {
            g.drawImage(coinTexture, coin.getX(), coin.getY(), null);
        }

        // Draw the coins collected counter
        g.setColor(Color.WHITE);
        g.drawString("Coins Collected: " + coinsCollected, 10, 20);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (playerY > 0) {
                    playerY -= 10;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (playerY < screenHeight - 50) {
                    playerY += 10;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (playerX > 0) {
                    playerX -= 10;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (playerX < screenWidth - 50) {
                    playerX += 10;
                }
                break;
        }

        // Check for collisions with coins
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);
            if (playerX < coin.getX() + 50 && playerX + 50 > coin.getX() && playerY < coin.getY() + 50
                    && playerY + 50 > coin.getY()) {
                coins.remove(coin);
                coinsCollected++;
            }
        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {
        // not used
    }

    public void keyReleased(KeyEvent e) {
        // not used
    }

    public static void main(String[] args) {
        new Game();
    }
}

