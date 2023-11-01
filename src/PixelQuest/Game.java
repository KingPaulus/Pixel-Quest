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
    private int PlayerSpeed = 10;
    Image scaledImage;
    private BufferedImage backgroundImage;
    private BufferedImage startscreenbackground;
    private BufferedImage endscreenbackground;
    private int screenWidth;
    private int screenHeight;
    private ArrayList<Coin> coins = new ArrayList<Coin>();
    private BufferedImage coinTexture;
    private int coinsCollected = 0;
    private int availableCoins = 7;
    private int totalCoinsCollected = 0;
    private int MaxLevel = 5;
    private int Level = 1;
    private boolean startscreen = true;
    private boolean gamescreen = false;
    private boolean endscreen = false;

    public Game() {
        JFrame frame = new JFrame("Pixel Quest");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.addKeyListener(this);

        try {
            startscreenbackground = ImageIO.read(new File("image/startscreen.png"));
            endscreenbackground = ImageIO.read(new File("image/endscreen.png"));
            playerTexture = ImageIO.read(new File("image/player.png"));
            backgroundImage = ImageIO.read(new File("image/background.png"));
            coinTexture = ImageIO.read(new File("image/coin.png"));

            // Calculate the desired width and height of the scaled-down image
            int scaledWidth = playerTexture.getWidth(null) / 2;  // divide by 2 to scale down by 50%
            int scaledHeight = playerTexture.getHeight(null) / 2;

            // Create a new image that is a scaled-down version of the original
            scaledImage = playerTexture.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        screenWidth = frame.getContentPane().getWidth();
        screenHeight = frame.getContentPane().getHeight();

        this.spawnCoins();
        repaint();

    }

    private void checkLevelEnd () {
        if(coinsCollected == availableCoins) {
            if(Level != MaxLevel) {
                // Wenn game Läuft
                Level = Level + 1;
                PlayerSpeed = PlayerSpeed + 10;
                this.spawnCoins();
                totalCoinsCollected = totalCoinsCollected + coinsCollected;
                coinsCollected = 0;
                repaint();
            } else if (Level >= MaxLevel) {
                // Ende vom game
                gamescreen = false;
                endscreen = true;
                repaint();
            }
        }
    }

    public void spawnCoins () {
        // Add some coins to the game
        for (int i = 0; i < availableCoins; i++) {
            int x = (int) (Math.random() * (screenWidth - 50));
            int y = (int) (Math.random() * (screenHeight - 50));
            coins.add(new Coin(x, y));
        }
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if(startscreen) {
            g.drawImage(startscreenbackground, 0, 0, null);
        } else if (gamescreen) {
            this.checkLevelEnd();
            g.drawImage(backgroundImage, 0, 0, null);
            g.drawImage(scaledImage, playerX, playerY, null);

            // Draw the coins on the screen
            for (Coin coin : coins) {
                g.drawImage(coinTexture, coin.getX(), coin.getY(), null);
            }

            // Draw the coins collected counter
            g.setColor(Color.WHITE);
            g.drawString("Coins Collected: " + coinsCollected + " / " + availableCoins, 10, 20);
            g.drawString("Coins Saved: " + totalCoinsCollected, 10, 40);
        } else if(endscreen) {
            g.drawImage(endscreenbackground, 0, 0, null);
        }
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(startscreen) {
            if(keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE) {
                startscreen = false;
                gamescreen = true;
                repaint();
            }
        } else if (gamescreen) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (playerY > 0) {
                        playerY -= PlayerSpeed;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (playerY < screenHeight - 50) {
                        playerY += PlayerSpeed;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (playerX > 0) {
                        playerX -= PlayerSpeed;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (playerX < screenWidth - 50) {
                        playerX += PlayerSpeed;
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

