package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Visualisation extends JPanel implements KeyListener, ActionListener {
    private ImageIcon animal = new ImageIcon("animal.svg");
    private ImageIcon jungle = new ImageIcon("jungle.svg");
    private ImageIcon grass = new ImageIcon("grass.svg");

    private Timer timer;
    private int delay=100;

    private int epoch=0;

    private boolean paused=true;

    public Visualisation(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //header
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 155);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Epoch: "+epoch, 780, 30);
        g.drawString("Animals: "+0, 780, 50);

        //map
        g.setColor(Color.RED);
        g.drawRect(24, 184, 851, 557);

        g.setColor(Color.BLACK);
        g.fillRect(25, 185, 850, 555);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.KEY_PRESSED){
            paused=!paused;
            if(paused)
                timer.stop();
            else
                timer.restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
