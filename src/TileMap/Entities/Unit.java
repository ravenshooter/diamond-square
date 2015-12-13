/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap.Entities;

import TileMap.Tile;
import Tools.Vertex;
import Tools.AStar.Knoten;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 *
 * @author Steve
 */
public class Unit extends Entity implements Runnable{

    Knoten curNode;
    Knoten nextNode;
    Knoten goal;
    double speed;
    boolean atTarget;
    Vertex curPos;
    Vertex nextPos;
    float colora,colorb,colorc;
    
    public Unit(Knoten path,Tile tile){
        super(tile);
        this.curNode = path;
        curPos = new Vertex((float)curNode.getX(),(float)curNode.getY(),(float)curNode.getHeight());
        while(path.getVorgänger()!=null){
            path = path.getVorgänger();
        }
        goal = path;
        nextNode = curNode.getVorgänger();
        nextPos = new Vertex((float)nextNode.getX(),(float)nextNode.getY(),(float)nextNode.getHeight());
        speed = 1;
        atTarget = false;
        colora = (float) Math.random();
        colorb = (float) Math.random();
        colorc = (float) Math.random();

    }
    
    
    @Override
    public void draw(GL gl) {
        GLU glu = new GLU();
        
        gl.glColor3f(colora, colorb, colorc);
        
        GLUquadric earth = glu.gluNewQuadric();
        gl.glTranslatef(curPos.x, curPos.y, curPos.z+2);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 2f;
        final int slices = 16;
        final int stacks = 16;
        glu.gluSphere(earth, radius, slices, stacks);
        gl.glTranslatef(-curPos.x, -curPos.y, -curPos.z-2);
        glu.gluDeleteQuadric(earth);
        
    }

    public void run() {
        Vertex stepVertex = calcStepVertex(curPos,nextPos);
        while(!atTarget){      
            if(Vertex.calcDistance(curPos,nextPos)<speed&&Vertex.calcDistance(curPos,nextPos)>-speed){
                if(nextNode.getVorgänger()==null){
                    atTarget = true;
                }else{
                    curPos = nextPos;
                    curNode = nextNode;
                    nextNode = nextNode.getVorgänger();
                    nextPos = new Vertex((float)nextNode.getX(),(float)nextNode.getY(),(float)nextNode.getHeight());
                    stepVertex = calcStepVertex(curPos,nextPos);
                }
            }else{
                curPos.x += stepVertex.x;
                curPos.y += stepVertex.y;
                curPos.z += stepVertex.z;
            }
            try{
                Thread.sleep(30);
            }catch(InterruptedException e){
            }
        }
       
        
        
    }
    
    public void start(){
         new Thread(this).start();
    }

    Vertex calcStepVertex(Vertex curPos, Vertex nextPos){
        Vertex dirVertex = Vertex.calcDirVertex(curPos,nextPos).getUnitVertex();
        Vertex stepVertex = new Vertex((float)(dirVertex.x*speed),(float)(dirVertex.y*speed),(float)(dirVertex.z*speed));
        return stepVertex;
    }
    
    

    
}
