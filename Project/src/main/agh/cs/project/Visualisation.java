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
    private boolean closed=false;

    private Vector2d field;
    private int width=851;
    private int height=555;
    private LoopedMap map;

    public Visualisation(LoopedMap map){
        this.field = new Vector2d(width/(map.upperRight.x+1), height/(map.upperRight.y+1));
        this.map=map;
        this.width=this.width-this.width%(map.upperRight.x+1);
        this.height=this.height-this.height%(map.upperRight.y+1);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //header border
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, this.width*2+48+40, 155);

        //pause
        g.setColor(Color.RED);
        g.drawRect(24+851, 10, 48, 155);
        g.fillRect(25+851, 11, 47, 154);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 30));
        g.drawString("P", 24+851+14, 40);
        g.drawString("A", 24+851+14, 70);
        g.drawString("U", 24+851+14, 100);
        g.drawString("S", 24+851+14, 130);
        g.drawString("E", 24+851+14, 160);

        //left header
        paintHeader(g, 24, 10, this.width, 155);

        //right header
        paintHeader(g, 24+851+48, 10, this.width, 155);

        //left map
        paintMap(g, 25, 185, this.width, this.height);

        //right map
        paintMap(g, 25+851+48, 185, this.width, this.height);

        g.dispose();
        if(this.closed)
            System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.closed=true;
            //paint();
            //g.dispose();
            //System.exit(0);
        }
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

    public void paintHeader(Graphics g, int x, int y, int width, int height){
        g.setColor(Color.GREEN);
        g.fillRect(x+width-250, y+20, 200, 70);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 30));
        g.drawString("Print", x+width-250+65, y+20+43);

        g.setColor(Color.BLUE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Epoch: "+epoch, x+80, 30);
        g.drawString("Animals: "+0, x+71, 50);
        g.drawString("Weeds: "+0, x+76, 70);
        g.drawString("Genotype: "+"11111111111111111111111111111111", x+56,90);
        g.drawString("Average Energy: "+0, x+16,110);
        g.drawString("Average Life: "+0, x+41,130);
        g.drawString("Average Kids: "+0, x+36,150);
    }

    public void paintMap(Graphics g, int x, int y, int width, int height){
        g.setColor(Color.RED);
        g.drawRect(x-1, y-1, width+2, height+2);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLUE);
        for(int i=0; i<=this.map.upperRight.x; i++){
            for(int j=0; j<=this.map.upperRight.y; j++){
                g.drawRect(x+i*this.field.x, y+j*this.field.y, this.field.x, this.field.y);
            }
        }

    }

}
