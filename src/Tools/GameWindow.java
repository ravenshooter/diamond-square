
package Tools;

import Tools.SceneManagment.Scene;
import Tools.SceneManagment.SceneManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GameWindow {

    
    Frame frame;
    Scene scene;
    
    
    
    public GameWindow(){
        GV.get().setGameWindow(this);
        frame = new Frame();
        frame.setResizable(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        scene.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });

    }
    
    /**
     * loads the specified scene into the window
     * @param key name of the scene
     */
    public void loadScene(String key){
        frame.removeAll();
        frame.setLayout(null);
        
        scene = SceneManager.get().getScene(key);
        if(scene.getContentPane()!= null){
            frame.add(scene.getContentPane());
        }
        frame.setSize(new Dimension(scene.getXRes(),scene.getYRes()));
        
        frame.setVisible(true); 
        scene.sceneAdded();
    }

    public Frame getFrame() {
        return frame;
    }
    
    public void setFrame(Frame frame){
        this.frame = frame;
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        scene.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
    }
    
    
    

}
