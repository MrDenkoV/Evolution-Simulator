package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

public class Visualisation extends JPanel implements KeyListener, ActionListener {
    private ImageIcon animal = new ImageIcon("animal.svg");
    private ImageIcon jungle = new ImageIcon("jungle.svg");
    private ImageIcon grass = new ImageIcon("grass.svg");

    private Timer timer;
    private int delay=100;

//    private int epoch=0;

    public static boolean paused=true;
    private boolean closed=false;

    private Vector2d field;
    private int width=851;
    private int height=555;
    private LoopedMap leftMap;
    private LoopedMap rightMap;

//    public Visualisation(LoopedMap leftMap, LoopedMap rightMap){
//        this.field = new Vector2d(width/(leftMap.upperRight.x+1), height/(leftMap.upperRight.y+1));
//        this.leftMap=leftMap;
//        this.rightMap=rightMap;
//        this.width=this.width-this.width%(leftMap.upperRight.x+1);
//        this.height=this.height-this.height%(leftMap.upperRight.y+1);
//        addKeyListener(this);
//        setFocusable(true);
//        setFocusTraversalKeysEnabled(false);
//        timer = new Timer(delay, this);
//        timer.start();
//    }

    public Visualisation(LoopedMap leftMap, LoopedMap rightMap) {
        this.field = new Vector2d(width / (leftMap.upperRight.x + 1), height / (leftMap.upperRight.y + 1));
        this.leftMap = leftMap;
        this.rightMap = rightMap;
        this.width = this.width - this.width % (leftMap.upperRight.x + 1);
        this.height = this.height - this.height % (leftMap.upperRight.y + 1);
        addKeyListener(this);
        // BADE
        addMouseClickListener();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paint(Graphics g){
        if(!this.closed) {
            //header border
            g.setColor(Color.WHITE);
            g.drawRect(24, 10, this.width * 2 + 48 + 40, 155);

            //pause
            g.setColor(Color.RED);
            g.drawRect(24 + width, 10, 48, 155);
            g.fillRect(25 + width, 11, 47, 154);
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.PLAIN, 30));
            if(paused) {
                g.drawString("P", 24 + width + 14, 40);
                g.drawString("A", 24 + width + 14, 70);
                g.drawString("U", 24 + width + 14, 100);
                g.drawString("S", 24 + width + 14, 130);
                g.drawString("E", 24 + width + 14, 160);
            }
            else{
                g.drawString("S", 24 + width + 14, 40);
                g.drawString("T", 24 + width + 14, 70);
                g.drawString("A", 24 + width + 14, 100);
                g.drawString("R", 24 + width + 14, 130);
                g.drawString("T", 24 + width + 14, 160);
            }

            //left header
            paintHeader(g, 24, 10, this.width, 155, this.leftMap);

            //right header
            paintHeader(g, 24 + this.width + 48, 10, this.width, 155, this.rightMap);

            //left map
            paintMap(g, 25, 185, this.width, this.height, this.leftMap);

            //right map
            paintMap(g, 25 + width + 48, 185, this.width, this.height, this.rightMap);

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

    public void addMouseClickListener(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println(x + "," + y);
                try {
                    this.classifyAndAct(x, y);
                }catch (IOException ex){
                    System.out.println(ex + "File? Path? IOException!");
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Saving exception " + ex);
                }
            }

            private void classifyAndAct(int x, int y) throws Exception {
//                g.drawRect(24 + width, 10, 48, 155);
                Vector2d pos=new Vector2d(x, y);
                if(pos.follows(new Vector2d(24+width, 10)) && pos.precedes(new Vector2d(24+width+48, 10+155))){//pause
                    Visualisation.paused=!Visualisation.paused;
                    System.out.println(Visualisation.paused);
                }
                else if(pos.follows(new Vector2d(25+width-250, 10+20)) && pos.precedes(new Vector2d(25+width-250+200, 10+20+70))){//leftprint
                    System.out.println("Left print: "+x +","+ y);
                    leftMap.statistics.saveStatistics("Left");
                }
                else if(pos.follows(new Vector2d(25+width+48+width-250, 10+20)) && pos.precedes(new Vector2d(25+width+48+width-250+200, 10+20+70))){//rightprint
                    System.out.println("Right print: "+x +","+ y);
                    rightMap.statistics.saveStatistics("Right");
                }
                else if(pos.follows(new Vector2d(25, 185)) && pos.precedes(new Vector2d(25+width-1, 185+height-1))){//leftmap
                    System.out.print("Left map: "+x +","+ y);
                    System.out.println("\tLeft map: "+(x-25)/field.x +","+ (y-185)/field.y);
                }
                else if(pos.follows(new Vector2d(25+width+48, 185)) && pos.precedes(new Vector2d(25+width+48+width-1, 185+height-1))){//rightmap
                    System.out.print("Right map: "+x +","+ y);
                    System.out.println("\tRight map: "+(x-25-width-48)/field.x +","+ (y-185)/field.y);
                }
            }
        });
    }

}
