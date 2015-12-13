package JOGL;

import Game.Canon;
import JOGL.Camera.KeyHandler;
import TileMap.Tile;
import TileMap.TileMap;
import Tools.Vertex;
import Tools.GV;
import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;



/**
 * JOGLWindow.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class JOGLWindow extends GLCanvas implements GLEventListener {

    GLU glu;
    FPSAnimator animator;
    int fps = 60;
    Camera camera;
    int lastCameraX;
    int lastCameraY;
    double lastCameraHeight;
    
    boolean smooth = true;
    
    Tile[] drawList;
    Vertex lastCamPos;
    Vertex currentCamPos;

    
    public JOGLWindow(){
        addGLEventListener(this);
        
    }
    
    
    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL(drawable.getGL()));
        final GL gl = drawable.getGL();

        // Enable z- (depth) buffer for hidden surface removal. 
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);

        // Enable smooth shading.
        gl.glShadeModel(GL.GL_SMOOTH);
        
        
        
        if(GV.get().lightning){
            gl.glEnable(GL.GL_LIGHTING);
            gl.glEnable(GL.GL_LIGHT0);
            //gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
            gl.glEnable(GL.GL_COLOR_MATERIAL);
        }
        
        // Define "clear" color.
        gl.glClearColor(0f, 0f, 0f, 0f);

        // We want a nice perspective.
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

        // Create GLU.
        glu = new GLU();

        camera = new Camera(0,0,400,this);
        lastCamPos = new Vertex((float)camera.getX()-100,(float)camera.getY(),(float)camera.getHeight());
        currentCamPos = new Vertex((float)camera.getX(),(float)camera.getY(),(float)camera.getHeight());
        
        drawList = GV.get().getTileMap().getDrawList((int)camera.getX(), (int)camera.getY() , (int)camera.getHeight());
        
        animator = new FPSAnimator(this, fps);
        animator.start();
        this.requestFocus();
        
        
    }
    
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

   public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }
        final GL gl = drawable.getGL();

        
        
        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        camera.replaceCamera();
        setCamera(gl,glu);
        /*
        // Drawing Using Triangles
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glVertex3f(0.0f, 1.0f, 0.0f);   // Top
            gl.glVertex3f(-1.0f, -1.0f, 0.0f); // Bottom Left
            gl.glVertex3f(1.0f, -1.0f, 0.0f);  // Bottom Right
        // Finished Drawing The Triangle
        gl.glEnd();
        */
        drawTileMap(gl);
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
   
   
   private void setCamera(GL gl, GLU glu) {
        // Change to projection matrix.
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 100000);
        glu.gluLookAt(camera.getX(),camera.getY(),camera.getHeight(),   camera.getFocusX(),camera.getFocusY(),camera.getFocusZ(),     0, 0, 1);
        

        // Change back to model view matrix.
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
   
   
   private void drawTileMap(GL gl){
       
       //Vermeidet unnötige neuberechnungen
       boolean calcFrontOfCam;
       currentCamPos.move((float)camera.getX(),(float)camera.getY(),(float)camera.getHeight());
       if(Vertex.calcDistance(lastCamPos,currentCamPos)>10){
           calcFrontOfCam = true;
           lastCamPos = currentCamPos;
       }else{
           calcFrontOfCam = false;
       }

       

       double start = System.currentTimeMillis();
       for (int k = drawList.length - 1; k >= 0; k--) {
           
           if(calcFrontOfCam){
               drawList[k].calcInFrontOfCam();
           }
           if (drawList[k].isInFrontOfCam()) {
               drawList[k].draw(gl);
           }
       }
       double end = System.currentTimeMillis();
       System.out.println("" + (end - start));
       for (int k = 0; k < drawList.length; k++) {
           drawList[k].drawEntities(gl);
       }

   }
   


   

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

