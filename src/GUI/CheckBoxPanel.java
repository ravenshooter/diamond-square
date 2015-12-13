/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tools.GV;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Steve
 */
public class CheckBoxPanel extends BorderPanel3D{
    Checkbox colorByHeightCheckbox,smoothCheckbox;
    public CheckBoxPanel(){
        super(10,10,10,10);
        setPreferredSize(new Dimension(180,100));
        this.setLayout(new GridLayout(3,1));
        smoothCheckbox = new Checkbox();
        smoothCheckbox.setLabel("Smooth Corners");
        smoothCheckbox.setState(true);
        this.add(smoothCheckbox);
        
        colorByHeightCheckbox = new Checkbox();
        colorByHeightCheckbox.setLabel("Color by height");
        this.add(colorByHeightCheckbox);
        
        Button updateButton = new Button("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        this.add(updateButton);
    }
    
    
    public void update(){
        if (smoothCheckbox.getState()) {
                    GV.get().getTileMap().doSmoothCorners();
                } else {
                    GV.get().getTileMap().unDoSmoothCorners();
                }
                if(colorByHeightCheckbox.getState()){
                    GV.get().getTileMap().doColorByHeight();
                }else{
                    GV.get().getTileMap().doCalcShade();
                }
                for(int i = 0; i < GV.get().getRivers().size();i++){
                    GV.get().getRivers().get(i).setColorAtTileMap(GV.get().getTileMap());
                }
                for(int i = 0; i < GV.get().getPaths().size();i++){
                    GV.get().getPaths().get(i).setColorAtTileMap(GV.get().getTileMap());
                }
    }
}
