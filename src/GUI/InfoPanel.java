/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tools.GV;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

/**
 *
 * @author Steve
 */
public class InfoPanel extends BorderPanel3D{
    TextField pointerXTextfield,pointerYTextfield,pointerZTextfield;
    TextField cameraXTextfield,cameraYTextfield,cameraZTextfield;
    public InfoPanel(){
        super(10);
            setPreferredSize(new Dimension(180,100));    
        this.setLayout(new GridLayout(3,4));
        this.add(new Label(""));
        this.add(new Label("X"));
        this.add(new Label("Y"));
        this.add(new Label("Z"));
        this.add(new Label("Camera:"));
        cameraXTextfield = new TextField();
        cameraXTextfield.setEditable(false);
        cameraXTextfield.setText(""+GV.get().getMarker().getXPos());
        this.add(cameraXTextfield);    
        cameraYTextfield = new TextField();
        cameraYTextfield.setEditable(false);
        cameraYTextfield.setText(""+GV.get().getMarker().getYPos());
        this.add(cameraYTextfield);  
        cameraZTextfield = new TextField();
        cameraZTextfield.setEditable(false);
        cameraZTextfield.setText(""+GV.get().getMarker().getMarkedTile().height);
        this.add(cameraZTextfield);  
        
        //Adds pointer info-line
        this.add(new Label("Pointer:"));
        pointerXTextfield = new TextField();
        pointerXTextfield.setEditable(false);
        pointerXTextfield.setText(""+GV.get().getMarker().getXPos());
        this.add(pointerXTextfield);    
        pointerYTextfield = new TextField();
        pointerYTextfield.setEditable(false);
        pointerYTextfield.setText(""+GV.get().getMarker().getYPos());
        this.add(pointerYTextfield);  
        pointerZTextfield = new TextField();
        pointerZTextfield.setEditable(false);
        pointerZTextfield.setText(""+GV.get().getMarker().getMarkedTile().height);
        this.add(pointerZTextfield);
        
    }
    
    
    
    public void updatePointerLocation(int x, int y, double height){
        pointerXTextfield.setText(x+"");
        pointerYTextfield.setText(y+"");
        pointerZTextfield.setText(height+"");
    }
    
    public void updateCameraLocation(int x, int y, double height){
        cameraXTextfield.setText(x+"");
        cameraYTextfield.setText(y+"");
        cameraZTextfield.setText(height+"");
    }
    
}
