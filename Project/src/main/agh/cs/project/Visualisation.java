package agh.cs.project;

import sun.awt.X11.XSystemTrayPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Visualisation extends JPanel implements KeyListener, ActionListener {
    private ImageIcon animal = new ImageIcon("animal.svg");
    private ImageIcon jungle = new ImageIcon("jungle.svg");
    private ImageIcon grass = new ImageIcon("grass.svg");

    private Timer timer;
    private int delay=100;

//    private int epoch=0;

    public boolean paused=true;
    private boolean closed=false;

    private Vector2d field;
    private int width=851;
    private int height=555;
    private LoopedMap leftMap;
    private LoopedMap rightMap;

    public Visualisation(LoopedMap leftMap, LoopedMap rightMap){
        this.field = new Vector2d(width/(leftMap.upperRight.x+1), height/(leftMap.upperRight.y+1));
        this.leftMap=leftMap;
        this.rightMap=rightMap;
        this.width=this.width-this.width%(leftMap.upperRight.x+1);
        this.height=this.height-this.height%(leftMap.upperRight.y+1);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        if(!this.closed) {
            //header border
            g.setColor(Color.WHITE);
            g.drawRect(24, 10, this.width * 2 + 48 + 40, 155);

            //pause
            g.setColor(Color.RED);
            g.drawRect(24 + 851, 10, 48, 155);
            g.fillRect(25 + 851, 11, 47, 154);
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.PLAIN, 30));
            g.drawString("P", 24 + 851 + 14, 40);
            g.drawString("A", 24 + 851 + 14, 70);
            g.drawString("U", 24 + 851 + 14, 100);
            g.drawString("S", 24 + 851 + 14, 130);
            g.drawString("E", 24 + 851 + 14, 160);

            //left header
            paintHeader(g, 24, 10, this.width, 155, this.leftMap);

            //right header
            paintHeader(g, 24 + 851 + 48, 10, this.width, 155, this.rightMap);

            //left map
            paintMap(g, 25, 185, this.width, this.height, this.leftMap);

            //right map
            paintMap(g, 25 + 851 + 48, 185, this.width, this.height, this.rightMap);

            g.dispose();
        }
        if(this.closed) {
            g.dispose();
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(this.closed)
            repaint();
        //timer.start();
//        if(this.paused)
//            repaint();
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
        if(keyEvent.getKeyCode() == KeyEvent.VK_P || keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
            paused=!paused;
            System.out.println(paused);
            /*if(paused)
                timer.stop();
            else
                timer.restart();*/
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public void paintHeader(Graphics g, int x, int y, int width, int height, LoopedMap map){
        g.setColor(Color.GREEN);
        g.fillRect(x+width-250, y+20, 200, 70);
        g.setColor(Color.BLACK);
        g.setFont(new Font("arial", Font.PLAIN, 30));
        g.drawString("Print", x+width-250+65, y+20+43);

        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Epoch: "+map.statistics.getEpochs(), x+80, 30);
        g.drawString("Animals: "+map.statistics.getCurrentAnimalCount(), x+71, 50);
        g.drawString("Weeds: "+map.statistics.getCurrentWeedsCount(), x+76, 70);
        g.drawString("Genotype: "+map.statistics.getMostCommonGenotype(), x+56,90);
        g.drawString("Average Energy: "+map.statistics.getCurrentAvgEnergy(), x+16,110);
        g.drawString("Average Life: "+map.statistics.getCurrentAvgLife(), x+41,130);
        g.drawString("Average Kids: "+map.statistics.getCurrentKidsCount(), x+36,150);
    }

    public void paintMap(Graphics g, int x, int y, int width, int height, LoopedMap map){
        g.setColor(Color.RED);
        g.drawRect(x-1, y-1, width+1, height+1);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLUE);
        for(int i=0; i<=map.upperRight.x; i++){
            for(int j=0; j<=map.upperRight.y; j++){
                LinkedList<IMapElement> currentField=map.objectsAt(new Vector2d(i, j));
                if(currentField==null||currentField.size()==0) {
                    Vector2d pos=new Vector2d(i, j);
                    g.setColor(Color.YELLOW.darker());
                    if(pos.precedes(map.upperRightJungle) && pos.follows(map.lowerLeftJungle))
                        g.setColor(Color.CYAN.darker());
                }
                else if(currentField.getFirst() instanceof Weeds)
                    g.setColor(Color.GREEN.darker());
                else if(currentField.size()>1)
                    g.setColor(Color.BLACK);
                else {
                    if(((Animal)currentField.getFirst()).getEnergy()<Animal.threshold)
                        g.setColor(Color.RED);
                    else if(((Animal)currentField.getFirst()).getEnergy()>Json.startEnergy)
                        g.setColor(Color.MAGENTA.darker());
                    else
                        g.setColor(Color.MAGENTA.brighter());
                }
                g.fillRect(x+i*this.field.x, y+j*this.field.y, this.field.x, this.field.y);
                //g.drawRect(x+i*this.field.x, y+j*this.field.y, this.field.x, this.field.y);
            }
        }

    }

}
