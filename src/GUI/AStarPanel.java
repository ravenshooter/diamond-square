/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import JOGL.Camera;
import Tools.GV;
import Tools.Vertex;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Steve
 */
public class AStarPanel extends BorderPanel3D {
    TextField xStartAStarTextArea,yStartAStarTextArea,xEndAStarTextArea,yEndAStarTextArea;
    public AStarPanel() {
        super(10);
        setPreferredSize(new Dimension(180,100));
        //AStarPanel = new Panel();
        this.setLayout(new GridLayout(1, 2));
        Panel aStarPositionPanel = new Panel();
        aStarPositionPanel.setLayout(new GridLayout(2, 3));

        aStarPositionPanel.add(new Label("Start:"));
        xStartAStarTextArea = new TextField();
        xStartAStarTextArea.setText("0");
        aStarPositionPanel.add(xStartAStarTextArea);
        yStartAStarTextArea = new TextField();
        yStartAStarTextArea.setText("1");
        aStarPositionPanel.add(yStartAStarTextArea);

        aStarPositionPanel.add(new Label("End:"));
        xEndAStarTextArea = new TextField();
        xEndAStarTextArea.setText("2");
        aStarPositionPanel.add(xEndAStarTextArea);
        yEndAStarTextArea = new TextField();
        yEndAStarTextArea.setText("3");
        aStarPositionPanel.add(yEndAStarTextArea);

        this.add(aStarPositionPanel);


        Button aStarButton = new Button("Find Path");
        aStarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GV.get().getGraph().calcPath(new Integer(xStartAStarTextArea.getText()), new Integer(yStartAStarTextArea.getText()), new Integer(xEndAStarTextArea.getText()), new Integer(yEndAStarTextArea.getText()));
                GV.get().getGraph().calcPathWithouthHeight(new Integer(xStartAStarTextArea.getText()), new Integer(yStartAStarTextArea.getText()), new Integer(xEndAStarTextArea.getText()), new Integer(yEndAStarTextArea.getText()));
            }
        });
        this.add(aStarButton);
    }
    
    
    


    
    
}
