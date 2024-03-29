package com.gdgouravdey.brick.breaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score= 0;
    private  int totalBricks = 21;
    private final Timer timer;
    private int playerX = 310;
    private int ballPosX = ThreadLocalRandom.current().nextInt(100, 400 + 1);
    private int ballPosY = ThreadLocalRandom.current().nextInt(300, 450 + 1);
    private int ballXDir = Math.random()>0.5?1:-1;
    private int ballYDir = -2;
    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int delay = 0;
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 595);
        map.draw((Graphics2D)g);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana",Font.BOLD,25));
        g.drawString(""+score,590,30);

        g.setColor(Color.YELLOW);
        g.fillRect(playerX,550,80,8);

        g.setColor(Color.GREEN);
        g.fillOval(ballPosX,ballPosY,15,15);

        if(ballPosY>=543) {
            play=false;
            ballPosX=0;
            ballYDir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana",Font.BOLD,30));
            g.drawString("Game Over! Score: "+score,190,300);

            g.setFont(new Font("Verdana",Font.BOLD,30));
            g.drawString("Press Enter to Restart ",190,340);
        }
        if(totalBricks == 0) {
            play = false;
            ballYDir = -2;
            ballXDir = Math.random()>0.5?1:-1;
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana",Font.BOLD,30));
            g.drawString("Game Over! Score: "+score,190,300);

            g.setFont(new Font("Verdana",Font.BOLD,30));
            g.drawString("Press Enter to Restart ",190,340);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
                ballYDir = -ballYDir;
            }
            outer:
            for(int i=0;i<map.map.length;i++) {
                for(int j=0;j<map.map[0].length;j++) {
                    if(map.map[i][j]>0) {
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i*map.brickHeight + 50;
                        int bricksWidth = map.brickWidth;
                        int bricksHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,bricksWidth,bricksHeight);
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                        if(ballRect.intersects(rect)) {
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;
                            if(ballPosX+19 <= rect.x || ballPosX+1 >= rect.x+bricksWidth) {
                                ballXDir = -ballXDir;
                            }
                            else {
                                ballYDir = -ballYDir;
                            }
                            break outer;
                        }
                    }
                }
            }
            ballPosX += ballXDir;
            ballPosY += ballYDir;
            if(ballPosX<0) {
                ballXDir = -ballXDir;
            }
            if(ballPosY<0) {
                ballYDir = -ballYDir;
            }
            if(ballPosX>670) {
                ballXDir = -ballXDir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 520) {
                playerX=600;
            }
            else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX <= 90) {
                playerX=10;
            }
            else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                ballPosX = ThreadLocalRandom.current().nextInt(100, 400 + 1);
                ballPosY = ThreadLocalRandom.current().nextInt(300, 450 + 1);
                ballXDir = Math.random()>0.5?1:-1;
                ballYDir = -2;
                score = 0;
                playerX = 310;
                totalBricks = 21;
                map = new MapGenerator(3,7);
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveRight() {
        play = true;
        for(int i=0;i<40;i++) {
            playerX += 2;
            repaint();
        }
    }

    public void moveLeft() {
        play = true;
        for(int i=0;i<40;i++) {
            playerX -= 2;
            repaint();
        }
    }
}
