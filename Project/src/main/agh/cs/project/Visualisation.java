package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

public class Visualisation extends JPanel implements KeyListener, ActionListener {
    private ImageIcon animal = new ImageIcon("animal.svg");
    private ImageIcon jungle = new ImageIcon("jungle.svg");
    private ImageIcon grass = new ImageIcon("grass.svg");

    public static boolean paused=true;
    public static boolean drawn=true;
    private boolean closed=false;
    private boolean showingLeft=false;
    private boolean showingRight=false;

    private Vector2d field;
    private int width=851;
    private int height=555;
    private LoopedMap leftMap;
    private LoopedMap rightMap;


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
            g.drawRect(24, 10, this.width * 2 + 48, 155);

            //pause
            g.setColor(Color.RED);
            g.drawRect(24 + width, 10, 48, 155);
            g.fillRect(25 + width, 11, 47, 154);
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.PLAIN, 30));
            if(!paused) {
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
        drawn=true;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(this.closed)
            repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.closed=true;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_P || keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
            paused=!paused;
//            System.out.println(paused);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public void paintHeader(Graphics g, int x, int y, int width, int height, LoopedMap map){
        //All animals with the same genotype
        g.setColor(Color.GREEN);
        g.fillRect(x+width-150-200, y+20, 150, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("arial", Font.PLAIN, 20));
        g.drawString("Top Genes", x+width-150-200+20, y+20+31);


        //Print
        g.setColor(Color.GREEN);
        g.fillRect(x+width-150, y+20, 100, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("arial", Font.PLAIN, 25));
        g.drawString("Print", x+width-150+20, y+20+33);


        //Statistics
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Epoch: "+map.statistics.getEpochs(), x+80, 30);
        g.drawString("Animals: "+map.statistics.getCurrentAnimalCount(), x+71, 50);
        g.drawString("Weeds: "+map.statistics.getCurrentWeedsCount(), x+76, 70);
        g.drawString("Genotype: "+map.statistics.getMostCommonGenotype(), x+56,90);
        g.drawString("Average Energy: "+String.format("%.3f", map.statistics.getCurrentAvgEnergy()), x+16,110);
        g.drawString("Average Life: "+String.format("%.3f", map.statistics.getCurrentAvgLife()), x+41,130);
        g.drawString("Average Kids: "+String.format("%.3f", map.statistics.getCurrentKidsCount()), x+36,150);


        //Selected Animal
        if(map.isFollowed){
            g.drawString("That animal's Genotype: "+map.following.getParent().getGenes(), x+250, 110);
            g.drawString("That animal's Kids Count: "+map.following.getKidsCount(), x+250, 150);
            g.drawString("That animal's Descendants Count: "+map.following.getDescendantsCount(), x+500, 150);
            g.drawString("Epochs being followed: " + (map.statistics.getEpochs()-map.following.getStartEpoch()), x+250, 130);
            if(map.following.getDeathEpoch()!=-1)
                g.drawString("That animal's Death Epoch: " + map.following.getDeathEpoch(), x + 500, 130);
        }
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
            }
        }

        if(map.isFollowed){
            int sizex=field.x/3;
            Animal parent=map.following.getParent();
            g.setColor(Color.WHITE);
            g.fillRect(x+parent.getPosition().x*field.x+sizex, y+parent.getPosition().y*field.y, sizex, field.y);
        }

        if((showingLeft && map.equals(leftMap)) || (showingRight && map.equals(rightMap))){
            int sizey=field.y/3;
            g.setColor(Color.WHITE);
            HashSet<Animal> animals = map.statistics.getMostCommonAnimals();
            for(Animal animal: animals)
                g.fillRect(x+animal.getPosition().x*field.x, y+animal.getPosition().y*field.y+sizey, field.x, sizey);
        }
    }

    public void addMouseClickListener(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
//                System.out.println(x + "," + y);
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
                Vector2d pos=new Vector2d(x, y);
                if(pos.follows(new Vector2d(24+width, 10)) && pos.precedes(new Vector2d(24+width+48, 10+155))){//pause
                    Visualisation.paused=!Visualisation.paused;
//                    System.out.println(Visualisation.paused);
                }
                else if(pos.follows(new Vector2d(24+width-150, 10+20)) && pos.precedes(new Vector2d(24+width-150+100, 10+20+50))){//leftprint
//                    System.out.println("Left print: "+x +","+ y);
                    leftMap.statistics.saveStatistics("Left");
                }
                else if(pos.follows(new Vector2d(24+width+48+width-150, 10+20)) && pos.precedes(new Vector2d(24+width+48+width-150+100, 10+20+50))){//rightprint
//                    System.out.println("Right print: "+x +","+ y);
                    rightMap.statistics.saveStatistics("Right");
                }
                else if(pos.follows(new Vector2d(24+width-150-200, 10+20)) && pos.precedes(new Vector2d(24+width-150-200+150, 10+20+50))) {//leftallgenes
//                    System.out.println("Left most Top Genes: " + x + "," + y);
                    showingLeft=!showingLeft;
                }
                else if(pos.follows(new Vector2d(24+width+48+width-150-200, 10+20)) && pos.precedes(new Vector2d(24+width+48+width-150-200+150, 10+20+50))) {//rightallgenes
//                    System.out.println("Right most Top Genes: " + x + "," + y);
                    showingRight=!showingRight;
                }
                else if(pos.follows(new Vector2d(25, 185)) && pos.precedes(new Vector2d(25+width, 185+height))){//leftmap
//                    System.out.print("Left map: "+x +","+ y);
                    int ix=(x-25)/field.x;
                    int iy=(y-185)/field.y;
//                    System.out.println("\tLeft map: "+ ix +","+iy);
                    clickedOnMap(ix, iy, leftMap);
                }
                else if(pos.follows(new Vector2d(25+width+48, 185)) && pos.precedes(new Vector2d(25+width+48+width, 185+height))){//rightmap
//                    System.out.print("Right map: "+x +","+ y);
                    int ix=(x-25-width-48)/field.x;
                    int iy=(y-185)/field.y;
//                    System.out.println("\tRight map: "+ ix +","+ iy);
                    clickedOnMap(ix, iy, rightMap);
                }
            }
        });
    }

    private void clickedOnMap(int x, int y, LoopedMap map) {
        boolean f=true;
        if(map.isFollowed) {
            map.resetFollowing();
            if(!map.following.getParent().getPosition().equals(new Vector2d(x, y)))
                map.isClicked(x, y);
        }
        else
            f= map.isClicked(x, y);
        if(f)
            repaint();
    }

}
