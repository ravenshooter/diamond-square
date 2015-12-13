/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;

/**
 *
 * @author Steve
 */


public class BorderPanel3D extends Panel{
    private  Insets insets;

    public BorderPanel3D(int inset){
        insets = new Insets(inset, inset, inset, inset);
    }
    
    public BorderPanel3D(int north, int east, int south, int west){
        insets = new Insets(north, east, south, west);
    }
    
    @Override
    public Insets getInsets() {
        return insets;
    }
    
    @Override
    public void paint(Graphics g) {
      super.paint(g);
      Dimension size = getSize();
      g.setColor(getBackground());
      g.draw3DRect(
        insets.top/2,insets.left/2,size.width-insets.right, size.height-insets.bottom, true);
    }
    
    
}
