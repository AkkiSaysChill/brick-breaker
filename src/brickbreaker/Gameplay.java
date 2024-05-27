package brickbreaker;

import org.w3c.dom.css.Rect;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
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

    private int ballposX = 350;
    private int ballposY = 530;
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

        playSound("sound/mujic.wav");
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

        // score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 640, 30);

        // paddle
        g.setColor(Color.CYAN);
        g.fillRect(playerx, 550,100, 8);

        // ball
        g.setColor(Color.pink);
        g.fillOval(ballposX, ballposY,20, 20);

        if (ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER!, SCORE: "+ score, 170, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 220, 330);
        }

        if (totalBricks == 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON!!, SCORE: "+ score, 170, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 220, 330);
        }

        g.dispose();
    }

    public static void playSound(String filePath) {
        try {
            // Open an audio input stream
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            // Get a sound clip resource
            Clip clip = AudioSystem.getClip();

            // Open the audio clip and load samples from the audio input stream
            clip.open(audioIn);

            // Play the audio clip
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if(play){
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerx, 550,100,8))){
                ballYdir = -ballYdir;
                playSound("sound/hitHurt.wav");
            }

            A: for (int i = 0; i<map.map.length; i++){
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

                            playSound("sound/explosion.wav");


                            if (ballposX + 19 <= brickRect.x || ballposY + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;

                            }else {

                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0){
                ballXdir = -ballXdir;
                playSound("sound/hitHurt.wav");
            }
            if (ballposY < 0){
                ballYdir = -ballYdir;
                playSound("sound/hitHurt.wav");
            }
            if (ballposX > 670){
                ballXdir = -ballXdir;
                playSound("sound/hitHurt.wav");
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play = true;
                ballposY = 530;
                ballposX = 350;

                ballXdir = -3;
                ballYdir = -3;

                playerx = 310;
                score = 0;

                totalBricks = 21;

                map = new MapGen(3, 7);

                repaint();
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
