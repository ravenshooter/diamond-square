/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;



import Game.Canon;
import Tools.GV;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Steve
 */
public class Editor extends Frame {
    
    int width, height;
    
    

    

    InfoPanel infoPanel;
    CheckBoxPanel checkBoxPanel;
    ScrollPane scrollPane;
    BorderPanel contentPane;
    BorderPanel3D tileMapEditor;
    BorderPanel3D tileEditor;
    
    public Editor(){
        
        width = 400;
        height = GV.get().getYRes();
        this.setTitle("Parameter Editor");
        scrollPane = new ScrollPane();
        scrollPane.setPreferredSize(new Dimension(width+scrollPane.getVScrollbarWidth()+10,height));
        contentPane = new BorderPanel(0,0,0,0);
        contentPane.setLayout(new GridLayout(1,2));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        

        initTileMapEditor();
        contentPane.add(tileMapEditor);
        
        initTileEditor();
        contentPane.add(tileEditor);
        
        scrollPane.add(contentPane);
        
        this.add(scrollPane);
        GV.get().setEditor(this);
    }
    
    
    
    public void initTileMapEditor(){
        tileMapEditor = new BorderPanel3D(2,2,2,2);
        tileMapEditor.setPreferredSize(new Dimension(width/2,height));
        tileMapEditor.setBackground(Color.LIGHT_GRAY);
        tileMapEditor.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int i = 0; //Y Position für alle Elemente
        
        c.anchor = c.NORTH;
        Label header = new Label("TileMap Editor");
        header.setFont(new Font("arial", Font.PLAIN, 16));
        header.setAlignment(header.CENTER);
        header.setPreferredSize(new Dimension(180,100));
        c.gridx = 0;
        c.gridy = i;
        tileMapEditor.add(header,c);
        i++;
        
        
        c.anchor = c.NORTHWEST;
        //Adds camera info-line
        infoPanel = new InfoPanel();
        c.gridx = 0;
        c.gridy = i;
        tileMapEditor.add(infoPanel,c);
        i++;
        

        //
        checkBoxPanel = new CheckBoxPanel();
        c.gridx = 0;
        c.gridy = i;
        tileMapEditor.add(checkBoxPanel,c);
        i++;
        

        c.gridx = 0;
        c.gridy = i;
        tileMapEditor.add(new RiverPanel(),c);
        i++;

        c.gridx = 0;
        c.gridy = i;
        tileMapEditor.add(new AStarPanel(),c);
        i++;
        
        c.gridx = 0;
        c.gridy = i;
        tileMapEditor.add(new PathSpeedPanel(),c);
        i++;
        
        c.gridy = i;    
        c.weighty = 1;
        tileMapEditor.add(new Label(""),c);
        i++;   
    }
    
    public void initTileEditor(){
        tileEditor = new BorderPanel3D(2);
        tileEditor.setLayout(new GridBagLayout());
        tileEditor.setBackground(new Color(64,138,204));
        
        GridBagConstraints c = new GridBagConstraints();

        int i = 0; //Y Position für alle Elemente
        c.gridx = 0;
        c.gridy = i;
        c.weighty = 0;
        i++;
        
        Label title = new Label("Tile Editor");
        title.setFont(new Font("arial", Font.PLAIN, 16));
        title.setAlignment(title.CENTER);
        title.setPreferredSize(new Dimension(180,100));
        tileEditor.add(title,c);
        
        c.gridy = i;
        i++;
        Panel heightPanel = new BorderPanel3D(10);
        heightPanel.setLayout(new BorderLayout());
        heightPanel.setPreferredSize(new Dimension(180,100));
        heightPanel.add(new Label("Set Height:"),BorderLayout.NORTH);
        final TextField heightField = new TextField("0.0");
        heightPanel.add(heightField,BorderLayout.CENTER);
        Button update = new Button("Update");
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                GV.get().getMarker().getMarkedTile().updateHeight(new Double(heightField.getText()));
            }
        });
        heightPanel.add(update,BorderLayout.EAST);
        tileEditor.add(heightPanel,c);
        
        c.gridy = i;
        i++;
        Panel colorCornerPanel = new BorderPanel3D(10);
        colorCornerPanel.setPreferredSize(new Dimension(180,100));
        colorCornerPanel.add(new Label("Color Corner:"));
        final TextField cornerNr = new TextField("0");
        colorCornerPanel.add(cornerNr);
        Button drawCorner = new Button("Update");
        drawCorner.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                GV.get().getMarker().getMarkedTile().drawCorner(new Integer(cornerNr.getText()));
            }
        });
        colorCornerPanel.add(drawCorner);
        tileEditor.add(colorCornerPanel,c);
        
        
        c.gridy = i;
        i++;
        Panel neighbourCornerPanel = new BorderPanel3D(10);
        neighbourCornerPanel.setPreferredSize(new Dimension(180,100));
        neighbourCornerPanel.add(new Label("Color Neighbour:"));
        final TextField neighbourNr = new TextField("0");
        neighbourCornerPanel.add(neighbourNr);
        Button drawNeighbour = new Button("Update");
        drawNeighbour.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                GV.get().getMarker().getMarkedTile().drawNeighbour(new Integer(neighbourNr.getText()));
            }
        });
        neighbourCornerPanel.add(drawNeighbour);
        tileEditor.add(neighbourCornerPanel,c);
        
        
        c.gridy = i;
        i++;       
        c.weighty = 0;
        Panel bindCameraPanel = new BorderPanel3D(10);
        Button bindCamera = new Button("Bind camera");
        bindCamera.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(GV.get().getCamera().getCameraMover()!=null){
                    GV.get().getCamera().setCameraMover(null);
                }else{
                    GV.get().getTileMap().getHighestTile().addEntity(new Canon(GV.get().getTileMap().getHighestTile()));
                }
            }
        });
        bindCameraPanel.add(bindCamera);        
        tileEditor.add(bindCameraPanel,c);
        
        c.gridy = i;
        i++;       
        c.weighty = 0;
        Button hideButton = new Button("Hide");
        hideButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                hideEditor();
            }
        });     
        tileEditor.add(hideButton,c);        
        
        c.gridy = i;
        i++;       
        c.weighty = 1;
        tileEditor.add(new Label(),c);
        
        
        tileEditor.setVisible(true);
    }

    public ScrollPane getScrollPane(){
        return scrollPane;
    }
    
    public Panel getContentPane(){
        return contentPane;
    }
    
    
    public void hideEditor(){
        final Container parent = scrollPane.getParent();
        parent.remove(scrollPane);
        parent.revalidate();
        Button showEditor = new Button();
        showEditor.setPreferredSize(new Dimension(15,GV.get().getYRes()));
        showEditor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.add(scrollPane,BorderLayout.EAST);
                parent.revalidate();
            }
        });
        parent.add(showEditor, BorderLayout.EAST);
        parent.revalidate();
    }
    
    
    public void updatePointerLocation(int x, int y, double height){
        infoPanel.updatePointerLocation(x, y, height);
    }
    
    public void updateCameraLocation(int x, int y, double height){
        infoPanel.updateCameraLocation(x, y, height);
    }
    
    public void update(){
        checkBoxPanel.update();
    }
    
  

   

    
    
    
    
    
    
}
