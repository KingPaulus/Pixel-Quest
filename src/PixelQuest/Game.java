package PixelQuest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener {
    private int playerX = 50;
    private int playerY = 50;

    public Game() {
        JFrame frame = new JFrame("My PixelQuest.Game");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.addKeyListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, 50, 50);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                playerY -= 10;
                break;
            case KeyEvent.VK_DOWN:
                playerY += 10;
                break;
            case KeyEvent.VK_LEFT:
                playerX -= 10;
                break;
            case KeyEvent.VK_RIGHT:
                playerX += 10;
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
