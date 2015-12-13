/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import TileMap.Entities.Unit;
import Tools.AStar.Graph;
import Tools.AStar.Knoten;
import Tools.GV;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Steve
 */
public class PathSpeedPanel extends BorderPanel3D{
    public PathSpeedPanel(){
        super(10);
        setPreferredSize(new Dimension(180,100));    
        this.setLayout(new BorderLayout());
        Button aStarTest = new Button("Path Speedtest");
        aStarTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                class t extends Thread{
                    @Override
                    public void run(){
                        Graph graph1 = new Graph(GV.get().getTileMap().getTileMap());
                        Knoten k1 = graph1.calcPath(0, 0, (GV.get().getXTiles()-1)*GV.get().getXTileSize(), (GV.get().getYTiles()-1)*GV.get().getYTileSize());
                        Graph graph2 = new Graph(GV.get().getTileMap().getTileMap());
                        Knoten k2 = graph2.calcPathWithouthHeight(0, 0, (GV.get().getXTiles()-1)*GV.get().getXTileSize(), (GV.get().getYTiles()-1)*GV.get().getYTileSize());
                        Unit unit1 = new Unit(k2, GV.get().getTileMap().getTile(1, 1));
                        Unit unit2 = new Unit(k1, GV.get().getTileMap().getTile(0, 0));
                        GV.get().getTileMap().getTile(1, 1).addEntity(unit1);
                        GV.get().getTileMap().getTile(0, 0).addEntity(unit2);
                        unit1.start();
                        unit2.start();
                    }
                }
                new t().start();
                
            }
        });
        this.add(aStarTest,BorderLayout.CENTER);
        this.add(new Label("may takes some time"),BorderLayout.SOUTH);
    }
}
