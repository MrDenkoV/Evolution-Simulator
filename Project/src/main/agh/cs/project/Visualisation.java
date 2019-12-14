package agh.cs.project;

import javax.swing.*;
import java.awt.*;

public class Visualisation extends  JPanel{

    public Visualisation(){

    }

    public void paint(Graphics g){
        //header
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 155);

        //map
        g.setColor(Color.RED);
        g.drawRect(24, 184, 851, 557);

        g.setColor(Color.BLACK);
        g.fillRect(25, 185, 850, 555);
    }
}
