/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import JOGL.Camera;
import JOGL.cameraMover;
import TileMap.Entities.Entity;
import TileMap.Tile;
import Tools.GV;
import Tools.Vertex;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 *
 * @author Steve
 */
public class Canon extends Entity implements cameraMover, Runnable {

    Tile tile;
    Camera camera;
    int xPos;
    int yPos;
    ArrayList<Ball> balls;
    int timer;
    int t = 0;
    float radius = 5;
    Vertex rohrDir;
    float rohrRot;
    float rohrZRot;

    public Canon(Tile tile) {
        super(tile);
        this.tile = tile;
        rohrDir = new Vertex();
        camera = GV.get().getCamera();
        camera.setCameraMover(this);
        xPos = tile.getXPos();
        yPos = tile.getYPos();
        camera.setY(yPos);
        camera.setX(xPos);
        camera.setHeight((int) (tile.getHeight() + radius));
        camera.setFocusX(tile.getNeighbour(0).getXPos());
        camera.setFocusY(tile.getNeighbour(0).getYPos());
        camera.setFocusZ((int) (tile.getHeight() + radius));
        balls = new ArrayList();

        new Thread(this).start();
    }

    @Override
    public void draw(GL gl) {
        GLU glu = new GLU();

        gl.glColor3f(1, 0, 0);
        gl.glTranslatef(tile.getX(), tile.getY(), (float) tile.getHeight() );

        gl.glPushMatrix();
            GLUquadric earth = glu.gluNewQuadric();
            glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
            glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
            glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
            radius = 5f;
            int slices = 16;
            int stacks = 16;
            glu.gluSphere(earth, radius, slices, stacks);
            glu.gluDeleteQuadric(earth);

            
            
            GLUquadric camSphere = glu.gluNewQuadric();
            glu.gluQuadricDrawStyle(camSphere, GLU.GLU_LINE);
            glu.gluQuadricNormals(camSphere, GLU.GLU_FLAT);
            glu.gluQuadricOrientation(camSphere, GLU.GLU_OUTSIDE);
            radius = 20f;
            slices = 16;
            stacks = 16;
            glu.gluSphere(camSphere, radius, slices, stacks);
            glu.gluDeleteQuadric(camSphere);

            
            
            
            gl.glRotated(-90, 1, 0, 0);
            //gl.glPushMatrix();
            gl.glRotated(rohrRot * 180 / Math.PI, 0, 1, 0);
            gl.glPushMatrix();
                gl.glRotated(rohrZRot*180/Math.PI, -1, 0, 0);
                GLUquadric rohr = glu.gluNewQuadric();
                glu.gluQuadricDrawStyle(rohr, GLU.GLU_FILL);
                glu.gluQuadricNormals(rohr, GLU.GLU_FLAT);
                glu.gluQuadricOrientation(rohr, GLU.GLU_OUTSIDE);
                radius = 3f;
                slices = 16;
                stacks = 16;
                glu.gluCylinder(rohr, radius, radius, 20, slices, stacks);
                glu.gluDeleteQuadric(rohr);
            gl.glPopMatrix();
        gl.glPopMatrix();
        gl.glTranslatef(-tile.getX(), -tile.getY(), (float) -tile.getHeight() );


        t++;
    }

    public void newFlag(Camera camera) {
        if(camera.getX()+50 > tile.getXPos() && camera.getX()-50 < tile.getXPos() && camera.getY()+50 > tile.getYPos() && camera.getY()-50 < tile.getYPos()){
        }else{
            
        }
        double xylength = Math.cos(camera.getRotZDeg()) * 40;
        if (camera.isFocUp()) {
            camera.setRotZDeg(camera.getRotZDeg() + 0.005);  
            if(rohrZRot< Math.PI/2){
                rohrZRot += 0.005;
            }
        }
        if (camera.isFocDown()) {
            camera.setRotZDeg(camera.getRotZDeg() - 0.005);
            if(rohrZRot> -Math.PI/2){
                rohrZRot -= 0.005;
            }
        }
        if (camera.isFocLeft()) {
            camera.setRotDeg(camera.getRotDeg() - 0.01);
            rohrRot -= 0.01;
        }
        if (camera.isFocRight()) {
            camera.setRotDeg(camera.getRotDeg() + 0.01);
            rohrRot += 0.01;
        }



        if (camera.isMoveUp()) {
            if (timer <= 0) {
                balls.add(new Ball(tile, rohrDir, this));
                timer = 100;
                System.out.println("New shot");
                //camera.setCameraMover(balls.get(balls.size()-1));
            }
        }
        
        if(camera.isRotLeft()){
            camera.setRotDeg(camera.getRotDeg() - 0.01);
        }

        if(camera.isRotRight()){
            camera.setRotDeg(camera.getRotDeg() + 0.01);
        }
        
        calcNewCameraPosition(camera);
        calcNewRohrPosition();

    }
    
    
    public void calcNewRohrPosition(){
        double xylength = Math.cos(rohrZRot) * 40;
        rohrDir.z= (float)(Math.sin(rohrZRot + Math.PI) * 40);
        rohrDir.x= (float)(Math.sin(rohrRot) * xylength );
        rohrDir.y= (float)(Math.cos(rohrRot) * xylength );
        rohrDir.length = rohrDir.calcDistance(rohrDir, new Vertex());
        System.out.println(rohrDir.x + "  " + rohrDir.y);
    }
    
     public void calcNewCameraPosition(Camera camera){
        double xylength = Math.cos(camera.getRotZDeg()/2+Math.PI/4) * 40;
        camera.setHeight((int) (Math.sin(camera.getRotZDeg()/2+ Math.PI/8) * 40 + tile.getHeight()));
        camera.setFocusZ((int) (Math.sin(camera.getRotZDeg()/2+ Math.PI/8 + Math.PI) * 40 + tile.getHeight()));
        camera.setFocusX((int) (Math.sin(camera.getRotDeg()) * xylength + tile.getXPos()));
        camera.setFocusY((int) (Math.cos(camera.getRotDeg()) * xylength + tile.getYPos()));
        camera.setX((int) (Math.sin(camera.getRotDeg() + Math.PI) * xylength + tile.getXPos()));
        camera.setY((int) (Math.cos(camera.getRotDeg() + Math.PI) * xylength + tile.getYPos()));
        //rohrDir = new Vertex((int) camera.getFocusX() - (int) camera.getX(), (int) camera.getFocusY() - (int) camera.getY(), (int) camera.getFocusZ() - (int) camera.getHeight()).getUnitVertex();
    }   

    public void run() {
        while (true) {
            for (int i = 0; i < balls.size(); i++) {
                balls.get(i).move();
            }
            newFlag(GV.get().getCamera());
            if (timer > 0) {
                timer--;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
