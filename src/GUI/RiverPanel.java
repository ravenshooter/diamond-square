/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tools.GV;
import Tools.River.River;
import Tools.River.RiverTile;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Steve
 */
public class RiverPanel extends BorderPanel3D{
    TextField xStartRiverTextArea,yStartRiverTextArea,lengthTextArea;
    public RiverPanel(){
        super(10);
        setPreferredSize(new Dimension(180,100));
        this.setLayout(new GridLayout(1,2));
        Panel riverPositionPanel = new Panel();
        riverPositionPanel.setLayout(new GridLayout(3,2));
        riverPositionPanel.add(new Label("X Start:"));
        xStartRiverTextArea = new TextField();
        xStartRiverTextArea.setText("0");
        riverPositionPanel.add(xStartRiverTextArea);
        riverPositionPanel.add(new Label("Y Start:"));
        yStartRiverTextArea = new TextField();
        yStartRiverTextArea.setText("0");
        riverPositionPanel.add(yStartRiverTextArea);
        riverPositionPanel.add(new Label("Length:"));
        lengthTextArea = new TextField();
        lengthTextArea.setText("400");
        riverPositionPanel.add(lengthTextArea);
        
        this.add(riverPositionPanel);
        Button riverButton = new Button("Create River");
        riverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int xStart = (new Integer(xStartRiverTextArea.getText())) / GV.get().getXTileSize();
                int yStart = (new Integer(yStartRiverTextArea.getText())) / GV.get().getYTileSize();
                int length = new Integer(lengthTextArea.getText());
                if (xStart < GV.get().getXTiles() && xStart >= 0 && yStart < GV.get().getYTiles() && yStart >= 0 && length >=0) {
                    River river = new River(GV.get().getTileMap().getHeightMap(), xStart, yStart, length);
                    RiverTile start = river.createRiver();
                    start.setColorAtTileMap(GV.get().getTileMap());
                    GV.get().addRiver(start);
                    new RiverWindow(start, GV.get().getRivers().size(), GV.get().getEditor());
                } else {
                    System.err.println("The specified position for this river is out of the tilemap or length is <0");
                }
            }
        });
        this.add(riverButton);
    }
    

    
    class RiverWindow extends Frame{
        int width;
        int height;
        Editor parent;
        RiverTile start;
        int nr;
        RiverWindow(RiverTile startn, int nr, Editor parentn) {
            this.parent = parentn;
            this.start = startn;
            this.nr = nr;
            width = 100;
            height = 200;
            this.setTitle("River"+nr+" Editor");
            this.setBounds((int)(parent.getBounds().getX()+parent.getBounds().width),(int) parent.getBounds().getY()+(GV.get().getRivers().size()-1)*height, width, height);
            this.setPreferredSize(new Dimension(width, height));

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    GV.get().getRivers().remove(GV.get().getRivers().indexOf(start));
                    parent.update();
                    setVisible(false);
                }
            });

            this.setBackground(Color.LIGHT_GRAY);
            
            
            this.setLayout(new GridLayout(4,1));
            this.add(new Label("River "+nr));
            
            Panel positionPanel = new Panel(new GridLayout(2,2));
            positionPanel.add(new Label("Start X: "));
            TextField startXTF = new TextField(start.getX()*GV.get().getXTileSize()+"");
            startXTF.setEditable(false);
            positionPanel.add(startXTF);
            positionPanel.add(new Label("Start Y: "));
            TextField startYTF = new TextField(start.getY()*GV.get().getYTileSize()+"");
            startYTF.setEditable(false);
            positionPanel.add(startYTF);
            this.add(positionPanel);
            
            
            Button deleteButton = new Button("Delete River");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GV.get().getRivers().remove(GV.get().getRivers().indexOf(start));
                    parent.update();
                    setVisible(false);
                }
            });
            this.add(deleteButton);


            
            this.setVisible(true);
        }
    }
    
    
}
