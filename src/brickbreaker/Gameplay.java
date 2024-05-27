package brickbreaker;

import org.w3c.dom.css.Rect;

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
    private int ballXdir = -3;
    private int ballYdir = -3;

    private MapGen map;

    public Gameplay(){
        map = new MapGen(3, 7);
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

        // Map Gen
        map.draw((Graphics2D) g);

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

        if(play){
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerx, 550,100,8))){
                ballYdir = -ballYdir;
            }

            for (int i = 0; i<map.map.length; i++){
                for (int j = 0; j < map.map[0].length; j++){
                    if (map.map[i][j] > 0){
                        int brickX = j * map.brickwidth + 80;
                        int brickY = i * map.brickheight + 50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20,20);
                        Rectangle brickRect = rect;

                        if (ballrect.intersects(brickRect)){
                            map.setBrickValue(0 , i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposY + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;

                            }else {

                                ballYdir = -ballYdir;
                            }
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0){
                ballXdir = -ballXdir;
            }
            if (ballposY < 0){
                ballYdir = -ballYdir;
            }
            if (ballposX > 670){
                ballXdir = -ballXdir;
            }
            // System.out.println(ballposY);
        }
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
        playerx -= 25;
    }

    public void moveRight() {
        play = true;
        playerx += 25;

        // System.out.println(playerx);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
