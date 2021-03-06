package org.yourorghere;







import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.texture.Texture;
import java.awt.BorderLayout;
import javax.media.opengl.DebugGL;
import javax.media.opengl.glu.GLUquadric;



/**
 * A minimal JOGL demo.
 * 
 * @author <a href="mailto:kain@land-of-kain.de">Kai Ruhl</a>
 * @since 26 Feb 2009
 */
public class GodAlgorythm extends GLCanvas implements GLEventListener {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The GL unit (helper class). */
    private GLU glu;

    /** The frames per second setting. */
    private int fps = 60;

    /** The OpenGL animator. */
    private FPSAnimator animator;

    /** The earth texture. */
    private Texture earthTexture;
    
    /** WaterTest */
    private static final boolean WATER_ON = false;
    
    int dist = 0;
    
    double[][] matrix;
    Triangle[] triangles;
    float length = 5;
    
    Camera camera;
    Water water;
    int watercount;
    
    long [] lvl;

    /**
     * A new mini starter.
     * 
     * @param capabilities The GL capabilities.
     * @param width The window width.
     * @param height The window height.
     */
    public GodAlgorythm(GLCapabilities capabilities, int width, int height, double[][] matrix, Camera camera, long [] lvl) {
        addGLEventListener(this);
        this.matrix = matrix;
        this.camera = camera;
        this.lvl = lvl;
    }

    /**
     * @return Some standard GL capabilities (with alpha).
     */
    private static GLCapabilities createGLCapabilities() {
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);
        return capabilities;
    }

    /**
     * Sets up the screen.
     * 
     * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
     */
    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL(drawable.getGL()));
        final GL gl = drawable.getGL();

        // Enable z- (depth) buffer for hidden surface removal. 
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);

        // Enable smooth shading.
        gl.glShadeModel(GL.GL_SMOOTH);
        

        // Define "clear" color.
        gl.glClearColor(0f, 0f, 0f, 0f);

        // We want a nice perspective.
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

        // Create GLU.
        glu = new GLU();

        recalcTriangles();
        

        for(int i = 0; i < triangles.length;i++){
            if(triangles[i].a.z == lvl[0]){
                triangles[i].setOcean();
            }
            if(triangles[i].a.z > lvl[0] && triangles[i].a.z < lvl[1]){
                triangles[i].setBeach();
            }else if(triangles[i].a.z > lvl[1] && triangles[i].a.z < lvl[2]){
                triangles[i].setForest();
            }else if(triangles[i].a.z > lvl[2] && triangles[i].a.z < lvl[3]){
                triangles[i].setMountain();
            }else if(triangles[i].a.z > lvl[3] && triangles[i].a.z < lvl[4]){
                triangles[i].setGlacier();
            }else{
                //triangles[i].setGlacier();
            }
        }
        
        if(WATER_ON){
            water = new Water();
        }
        
        // Start animator.
        animator = new FPSAnimator(this, fps);
        animator.start();
    }

    
    
    
    
    /**
     * The only method that you should implement by yourself.
     * 
     * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
     */
    public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }
        final GL gl = drawable.getGL();

        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Set camera.
        dist++;
        setCamera(gl, glu);
        
        //replace camera
        camera.replaceCamera();
        
        //setLighting(gl);
        if(WATER_ON){
            if(watercount == 5){
                matrix = water.doWater(matrix);
                recalcTriangles();
                watercount = 0;
            }
        }
        watercount++;
        
        /*
        GLUquadric earth = glu.gluNewQuadric();

        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 6.378f;
        final int slices = 32;
        final int stacks = 32;
        glu.gluSphere(earth, radius, slices, stacks);
        
        glu.gluDeleteQuadric(earth);
        
        */
        // Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
        
        
        gl.glBegin(GL.GL_TRIANGLES);
        
        for(int i = 0; i < triangles.length;i++){
            
            gl.glColor3f(triangles[i].color3f[0], triangles[i].color3f[1], triangles[i].color3f[2]);
            gl.glVertex3f(triangles[i].a.x,triangles[i].a.y,triangles[i].a.z);
            gl.glVertex3f(triangles[i].b.x,triangles[i].b.y,triangles[i].b.z);
            gl.glVertex3f(triangles[i].c.x,triangles[i].c.y,triangles[i].c.z);
        }
        gl.glEnd();
    }


    
    /**
     * @param gl The GL context.
     * @param glu The GL unit.
     * @param distance The distance from the screen.
     */
    private void setCamera(GL gl, GLU glu) {
        // Change to projection matrix.
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(camera.getX(),camera.getY(),camera.getHeight(),   camera.getFocusX(),camera.getFocusY(),0,     0, 0, 1);
        
        // Change back to model view matrix.
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    
    private void setLighting(GL gl){
        
        // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {0, 30, 100, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.3f, 0.3f, 0.3f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_LIGHTING);
    }
    
    private void recalcTriangles(){
        int amount = ((matrix.length - 1) * (matrix[0].length - 1)) * 2;
        triangles = new Triangle[amount];
        int counter = 0;
        
        
        for (int j = 0; j < matrix[0].length - 1; j++) {
            for (int i = 0; i < matrix.length - 1; i++) {

                triangles[counter] = new Triangle(
                        new Vertex((float) i * length, (float) j * length, (float) matrix[j][i]),
                        new Vertex((float) (i + 1) * length, (float) j * length, (float) matrix[j][i+1]),
                        new Vertex((float) (i + 1) * length, (float) (j + 1) * length, (float) matrix[j + 1][i + 1]));
                
                counter++;
            }
            
            for (int i = 0; i < matrix.length - 1; i++) {


                triangles[counter] = new Triangle(
                        new Vertex((float) i * length, (float) j * length, (float) matrix[j][i]),
                        new Vertex((float) (i) * length, (float) (j + 1) * length, (float) matrix[j + 1][i]),
                        new Vertex((float) (i + 1) * length, (float) (j + 1) * length, (float) matrix[j + 1][i + 1]));
                
                counter++;
            }
        }

        for(int i = 0; i < triangles.length; i++){
            triangles[i].recalcColor();
        }
    }

    
    
    
    
    
    


    
    /**
     * Resizes the screen.
     * 
     * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable,
     *      int, int, int, int)
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
    }

    /**
     * Changing devices is not supported.
     * 
     * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable,
     *      boolean, boolean)
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

    
    
    
    
    class Triangle{
        Vertex a,b,c;
        float [] color3f = new float [3];
        public Triangle(Vertex a, Vertex b, Vertex c ){
           this.a = a;
           this.b = b;
           this.c = c;
        }
        
        public void setOcean(){
            color3f[0] = (float) (0.28f + Math.random()*0.1f);
            color3f[1] = (float) (0.48f + Math.random()*0.1f);
            color3f[2] = (float) (0.8f + Math.random()*0.1f);
        }
        
        public void setBeach() {
            color3f[0] = (float) (1f - Math.random()*0.1f);
            color3f[1] = (float) (1f - Math.random()*0.1f);
            color3f[2] = (float) (0f + Math.random()*0.1f);
        }
        
        public void setForest() {
            color3f[0] = (float) (0.05f + Math.random()*0.1f);
            color3f[1] = (float) (0.4f + Math.random()*0.1f);
            color3f[2] = (float) (0f + Math.random()*0.1f);
        }
        
        public void setMountain() {
            color3f[0] = (float) (0.4f + Math.random()*0.1f);
            color3f[1] = (float) (0.4f + Math.random()*0.1f);
            color3f[2] = (float) (0.4f + Math.random()*0.1f);
        }
        
        public void setGlacier() {
            color3f[0] = (float) (1f - Math.random()*0.1f);
            color3f[1] = (float) (1f - Math.random()*0.1f);
            color3f[2] = (float) (1f - Math.random()*0.1f);
        }
        
        public void recalcColor(){
            Vertex v1 = new Vertex(c.x - a.x, c.y - a.y, c.z - a.z);
            Vertex v2 = new Vertex(b.x - a.x, b.y - a.y, b.z - a.z);
            
            Vertex vres = new Vertex(v1.y*v2.z - v1.z*v2.y,
                                     v1.z*v2.x - v1.x*v2.z,
                                     v1.x*v2.y - v1.y*v2.x);
            
            //vektor normieren
            float fac = (float) Math.sqrt(Math.pow(vres.x,2) + Math.pow(vres.y,2) + Math.pow(vres.z,2));
            vres.x /= fac;
            vres.y /= fac;
            vres.z /= fac;
            
            double p =  Math.sqrt(Math.pow(vres.x,2) + Math.pow(vres.y,2) + Math.pow(vres.z,2));
            
            Vertex vcam = new Vertex(-(float)(3/3.1),0f,(float)(1/3.1));
            
            color3f[0] = Math.abs((vcam.x*vres.x + vcam.y*vres.y + vcam.z*vres.z)*0.5f)+0.25f;
            color3f[1] = color3f[0];
            color3f[2] = color3f[0];
        }
        
    }
    
    
    class Vertex{
        
        public float x,y,z;

        Vertex(float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        

        
    }
    
}
