package com.gdgouravdey.brick.breaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score= 0;
    private  int totalBricks = 21;
    private Timer timer;
    private int delay = 0;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(ballPosX,ballPosY);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
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
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        g.setColor(Color.YELLOW);
        g.fillRect(playerX,550,100,8);

        g.setColor(Color.GREEN);
        g.fillOval(ballPosX,ballPosY,20,20);

        if(ballPosY>570) {
            play=false;
            ballPosX=0;
            ballYDir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over! Score: "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Press Enter to Restart ",190,340);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not Supported yet.");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not Supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
