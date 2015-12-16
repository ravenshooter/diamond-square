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
import java.util.Timer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import javax.swing.text.Position;

/**
 *
 * @author Steve
 */
public class Ball extends Entity implements cameraMover{
    
    /**
     * The tile where the canon stands
     */
    Tile tile;
    
    /**
     * The tile right beneath the ball
     */
    Tile groundTile;
    
    float xPos,yPos;
    float xStart,yStart;
    float height,startHeight;
    
    float color,rad;
    
    Vertex start;
    float t;
    long lastTime;
    
    Camera camera;
    Canon canon;
    boolean leftRohr;
    
    public Ball(Tile tile, Vertex start, Canon canon){
        super(tile);
        tile.addEntity(this);
        this.tile = tile;
        this.start = start.getUnitVertex();
        this.start.scale(100f);
        this.canon = canon;
        xPos = tile.getXPos();
        yPos = tile.getYPos();
        rad = 3f;
        height = (float)tile.getHeight()-10;
        color = 0.1f;
        xStart = xPos;
        yStart = yPos;
        startHeight = height;
        lastTime = System.currentTimeMillis();
    }
    
    @Override
    public synchronized void draw(GL gll) {
        GL2 gl = gll.getGL2();
        GLU glu = new GLU();
        
        gl.glColor3f(color, 0.1f, 0.1f);
        
        GLUquadric earth = glu.gluNewQuadric();
        gl.glTranslatef(xPos, yPos, height);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = rad;
        final int slices = 16;
        final int stacks = 16;
        glu.gluSphere(earth, radius, slices, stacks);
        gl.glTranslatef(-xPos, -yPos, -height);
        glu.gluDeleteQuadric(earth);
    }
    
    public synchronized void move() {
        t = System.currentTimeMillis() - lastTime;
        t = t / 1000;
        
        if (start.getLength() * t > 50) {
            leftRohr = true;
            startHeight = height;
            lastTime = System.currentTimeMillis();
            t = 0;
        }
        groundTile = GV.get().getTileMap().getTile((int) xPos / GV.get().getXTileSize(), (int) yPos / GV.get().getYTileSize());

        if (height < groundTile.getHeight() && leftRohr) {
            color = 1;
            rad = rad * 2;
        } else {
            xPos = xStart + start.x * t;
            yPos = yStart + start.y * t;

            if (leftRohr) {
                height = 0.5f * -9.81f * t * t + start.z * t + startHeight;
            } else {
                height = start.z * t + startHeight+10;
            }
        }
        if (rad > 100) {
            tile.remove(this);
            //camera.setCameraMover(canon);
        }

    }

    public void newFlag(Camera camera) {
        this.camera = camera;
        camera.setFocusX(xPos);
        camera.setFocusY(yPos);
        camera.setFocusZ(height);
        camera.setX(xPos-start.x);
        camera.setY(yPos-start.y);
        camera.setHeight(height-start.z);
    }
    
}
