
package JOGL;

import TileMap.Marker;
import GUI.Editor;
import Game.Canon;
import Tools.GV;
import Tools.SceneManagment.Scene;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;

public class JOGLScene extends Scene{
    Frame frame;
    JOGLWindow canvas;

    public JOGLScene(Frame frame) {
        super("JOGLScene");
        this.frame = frame;
    }
    
    @Override
    public void stop(){
        canvas.animator.stop();
    }
    
    /**
     * called when the scene get's added to a frame; 
     * funktioniert!
     */
    @Override
    public void sceneAdded(){
        canvas = new JOGLWindow();
        frame.removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(canvas,BorderLayout.CENTER);
        Editor e = new Editor(); 
        
        frame.add(e.getScrollPane(),BorderLayout.EAST);
        
            }
    
    /*
    @Override
    public void sceneAdded(){
        canvas = new JOGLWindow();
        frame.setLayout(new GridLayout(1,1));
        //frame.setVisible(false);
        
        frame.setSize(getXRes(), getYRes());
        //frame.add(canvas);
        Panel p = new Panel(new GridLayout(1,1));
        p.add(new Label("TEST"));
        frame.add(p);
        frame.setVisible(true);
        //canvas.setVisible(true);

    }*/
    


}

