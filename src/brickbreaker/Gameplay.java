package brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerx = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1,692,592);

        //borders
        g.setColor(Color.GRAY);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(683,0,3,592);

        // paddle
        g.setColor(Color.CYAN);
        g.fillRect(playerx, 550,100, 8);

        // ball
        g.setColor(Color.pink);
        g.fillOval(ballposX, ballposY,20, 20);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerx >= 580){
                playerx = 580;
            }else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerx <= 10){
                playerx = 10;
            }else {
                moveLeft();
            }
        }
    }

    public void moveLeft()
    {
        play = true;
        playerx -= 20;
    }

    public void moveRight() {
        play = true;
        playerx += 20;

        // System.out.println(playerx);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
