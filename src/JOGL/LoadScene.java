
package JOGL;

import Game.Canon;
import Tools.GV;
import Tools.SceneManagment.Scene;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoadScene extends Scene{
    Label loadState;
    Button startButton;
    Panel centerPanel;
    public LoadScene(){
        super("LoadScene",500,600);
        GV.get().setXRes(500);
        GV.get().setYRes(600);
        
        //getContentPane().setBackground(Color.red);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new Label(),BorderLayout.SOUTH);
        getContentPane().add(new Label(),BorderLayout.EAST);
        getContentPane().add(new Label(),BorderLayout.WEST);
        
        centerPanel = new Panel(new GridLayout(3,1));
        loadState = new Label("Loading...");
        loadState.setFont(new Font("Arial", Font.BOLD, 30));
        loadState.setAlignment(Label.CENTER);
        TextArea explanation = new TextArea("Instructions: "+  System.getProperty("line.separator")+System.getProperty("line.separator") +" Press w,a,s,d to move focus,"+System.getProperty("line.separator") + "press up,down,left and right to move camera "+System.getProperty("line.separator")+"and press + and - to zoom."+System.getProperty("line.separator")+"Press B to set Marker (unpassable!)",1,1,TextArea.SCROLLBARS_NONE);
        explanation.setEditable(false);
        centerPanel.add(loadState,0);
        centerPanel.add(new Label(),1);
        centerPanel.add(explanation,2);
        getContentPane().add(centerPanel,BorderLayout.CENTER);
        
    }
    
    @Override
    public void sceneAdded(){
       //getContentPane().setLayout(null);
       //getContentPane().setBackground(Color.red);
       //getContentPane().add(new Label("TEST")); 
        //try {
            //Thread.sleep(1000);
        //} catch (InterruptedException ex) {
        //    Logger.getLogger(LoadScene.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
       
    }
    
    public void setLoadState(String state){
        if(loadState != null){
            loadState.setText(state);
        }
    }
    
    public void setDone(){
        loadState.setText("Loading complete"); 
        startButton = new Button("Start Animation");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GV.get().getGameWindow().loadScene("JOGLScene");
            }
        });
        centerPanel.remove(1);
        centerPanel.add(startButton,1);
        centerPanel.validate();
        
    }
}


