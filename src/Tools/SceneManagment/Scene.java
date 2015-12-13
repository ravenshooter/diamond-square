/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.SceneManagment;

import Tools.GV;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.util.ArrayList;



/**
 *
 * @author Steve
 */
public class Scene {
    private int xRes;
    private int yRes;
    String name;
    Panel contentPane;
    ArrayList<drawable> drawables; 
    
    /**
     * Creates a new Scene with the specified values for name, xRes and yRes;
     * @param name
     * @param xRes
     * @param yRes 
     */
    public Scene(String name, int xRes, int yRes){
        this.name = name;
        this.xRes = xRes;
        this.yRes = yRes;
        contentPane = new Panel();
        contentPane.setSize(xRes, yRes);
        contentPane.setLayout(null);
        SceneManager.get().addScene(name, this);
        GV.get().setXRes(xRes);
        GV.get().setYRes(yRes);
    }
    
    
    /**
     * Creates a new scene in fullscreen
     * @param name name of the scene, to load it with the scene Manager
     */
    public Scene(String name){
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize ();
        this.name = name;
        this.xRes = screensize.width;
        this.yRes = screensize.height;
        contentPane = new Panel();
        contentPane.setSize(xRes, yRes);
        contentPane.setLayout(null);
        SceneManager.get().addScene(name, this);
        GV.get().setXRes(xRes);
        GV.get().setYRes(yRes);
    }
   
    
    
    /**
     * Called by drawloop to draw the scene
     * @param g Graphics Object
     */
    public void draw(Graphics2D g){
        synchronized(drawables){
            for(int i = 0; i < drawables.size();i++){
                drawables.get(i).draw(g);
            }
        }
    }
    

    public void addDrawable(drawable d){
        synchronized(drawables){
            drawables.add(d);
        }
    }
    
    public void addComponent(Component c){
        contentPane.add(c);
    }
    
    public void removeDrawable(drawable d){
        synchronized(drawables){
            drawables.remove(drawables.indexOf(d));
        }
    }
    
    public int getXRes(){
        return xRes;
    }
    
    public int getYRes(){
        return yRes;
    }
    
    public Panel getContentPane(){
        return contentPane;
    }
    
    public void setContentPane(Panel container){
        this.contentPane = container;
    }
    
    /**
     * Called if the system is about to exit
     */
    public void stop(){
        
    }
    
    public void sceneAdded(){}
}
