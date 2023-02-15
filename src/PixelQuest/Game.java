package PixelQuest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener {
    private int playerX = 50;
    private int playerY = 50;
    private BufferedImage playerTexture;
    private int screenWidth;
    private int screenHeight;

    public Game() {
        JFrame frame = new JFrame("My Game");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.addKeyListener(this);

        try {
            playerTexture = ImageIO.read(new File("image/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        screenWidth = frame.getContentPane().getWidth();
        screenHeight = frame.getContentPane().getHeight();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(playerTexture, playerX, playerY, null);
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
        repaint();
    }

    public void keyTyped(KeyEvent e) {
        // not used
    }

    public void keyReleased(KeyEvent e) {
        // not used
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
