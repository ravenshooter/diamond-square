/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap;
import JOGL.Camera;
import Tools.Vertex;
import TileMap.Entities.Entity;
import TileMap.Entities.House;
import Tools.AStar.aStarAble;
import Tools.GV;
import Tools.Sprite;
import Tools.SpriteStore;


import java.util.ArrayList;
import java.awt.Point;
import java.awt.Rectangle;
import java.nio.FloatBuffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile implements Comparable<Tile>, aStarAble
{
    String type;
    Sprite sprite;
    Point center;
    ArrayList <Entity> entities = new ArrayList<Entity>();
    boolean hasEntities;
    public double height;
    double distanceToCam = 0;
    boolean water;

    
    boolean showHeight = true;
    int xNr;
    int yNr;
    int xPos;
    int yPos;
    Rectangle tileRect;
    boolean markable = true; //true wenn diese Zelle markiert werden kann;
    boolean passable = true;
    boolean buildable = true;
    boolean marked;
    
    
    boolean inFrontOfCam;
    
    public Vertex[]corners;
    Vertex[] normalBuffer;
    Vertex[] normalCornerBuffer;
    
    
    float[] color3f;
    boolean [] drawCorner;
    boolean [] drawNeighbour;
    
    /**
     * Referenzen auf die Umgebung des Tiles, 0 ist oben, dann im Uhrzeigersinn
     */
    Tile neighbours[];
    
    
    /**
     * Constructor for objects of class Tile
     */
    public Tile(TileMap tileMap, String type, int xNr, int yNr)
    {
        neighbours = new Tile[8];
        neighbours[0] = tileMap.getTile(xNr,yNr-1);
        neighbours[1] = tileMap.getTile(xNr+1,yNr-1);
        neighbours[2] = tileMap.getTile(xNr+1,yNr);
        neighbours[3] = tileMap.getTile(xNr+1,yNr+1);
        neighbours[4] = tileMap.getTile(xNr,yNr+1);
        neighbours[5] = tileMap.getTile(xNr-1,yNr+1);
        neighbours[6] = tileMap.getTile(xNr-1,yNr);
        neighbours[7] = tileMap.getTile(xNr-1,yNr-1);
        this.xNr = xNr;
        this.yNr = yNr;
        xPos = xNr*GV.get().getXTileSize();
        yPos = yNr*GV.get().getYTileSize();
        tileRect= new Rectangle(xPos,yPos,GV.get().getXTileSize(),GV.get().getYTileSize());
        center = new Point(xPos+GV.get().getXTileSize()/2,yPos+GV.get().getYTileSize()/2);
        
        
        setType(type);
        color3f = new float[3];
        color3f[0] = 0.5f;color3f[1] = 0.5f;color3f[2] = 0.5f;
        corners = new Vertex[4];
        drawCorner = new boolean[4];
        drawNeighbour = new boolean[8];
        normalBuffer = new Vertex[2];
        normalCornerBuffer = new Vertex[4];


        hasEntities =false;
        if(type.compareTo("water")==0){
            passable = false;
            buildable = false;
        }
    }
    
    /**
     * Konstuktor fuer ein black Tile ohne jeglichen Wert
     */
    public Tile(){
        this.type = "black";
        sprite = SpriteStore.get().getSprite(type+".png");
        height = 0;
        normalBuffer = new Vertex[2];
        normalBuffer[0] = new Vertex(0,0,0);
        normalBuffer[1] = new Vertex(0,0,0);
        normalCornerBuffer = new Vertex[4];
        hasEntities =false;
        markable = false;
        passable = false;
    }
    
    
    
    
    public void setNeighbours(TileMap tileMap, int xNr,int yNr){
        neighbours[0] = tileMap.getTile(xNr,yNr-1);
        neighbours[1] = tileMap.getTile(xNr+1,yNr-1);
        neighbours[2] = tileMap.getTile(xNr+1,yNr);
        neighbours[3] = tileMap.getTile(xNr+1,yNr+1);
        neighbours[4] = tileMap.getTile(xNr,yNr+1);
        neighbours[5] = tileMap.getTile(xNr-1,yNr+1);
        neighbours[6] = tileMap.getTile(xNr-1,yNr);
        neighbours[7] = tileMap.getTile(xNr-1,yNr-1);

        doSmoothCorners();
        calcNormalBuffer();
        
    }
    
    private void setNeighbours(){
        TileMap tileMap = GV.get().getTileMap();
        neighbours[0] = tileMap.getTile(xNr,yNr-1);
        neighbours[1] = tileMap.getTile(xNr+1,yNr-1);
        neighbours[2] = tileMap.getTile(xNr+1,yNr);
        neighbours[3] = tileMap.getTile(xNr+1,yNr+1);
        neighbours[4] = tileMap.getTile(xNr,yNr+1);
        neighbours[5] = tileMap.getTile(xNr-1,yNr+1);
        neighbours[6] = tileMap.getTile(xNr-1,yNr);
        neighbours[7] = tileMap.getTile(xNr-1,yNr-1);
        
        doSmoothCorners();
        calcNormalBuffer();
    }
    
    
    
    public final void setType(String type){
        this.type = type;
        //neighbours = new Tile[8];
        //setNeighbours();
        sprite = SpriteStore.get().getSprite(type+".png");
        if(type.compareTo("water")==0){
                    passable = false;
                    buildable = false;
        }
    }
    
    

    
    

    
    public void addEntity(Entity e){
        entities.add(e);
        this.setUnpassable();
    }
    
    
    public Entity getEntity(){
        if(entities.size() != 0){
            if(entities.get(0).getClass().getName().compareTo("Marker")!=0)
                return entities.get(0);
            else{
                if(entities.size()>0){
                    return entities.get(1);
                }else{
                    return null;
                }
            }
        }else{
            return null;
        }
    }
    
    
    public void remove(Entity e){
        int entityID = entities.indexOf(e);
        if(entityID == -1){}else{
            entities.remove(entityID);
            if(entities.size() == 0){
                hasEntities=false;
            }
        }
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public void updateHeight(double height){
        setHeight(height);
        this.doSmoothCorners();
        for(int j = 0; j < 8; j++){
            if(neighbours[j]!=null&&neighbours[j].getType().compareTo("black") != 0){
                neighbours[j].doSmoothCorners();
                for(int i = 0;i < 8; i ++){
                    if(neighbours[i]!=null&&neighbours[i].getType().compareTo("black") != 0){
                        neighbours[j].neighbours[i].doSmoothCorners();
                    }
                }
            }
        }
        this.calcNormalBuffer();
        for(int j = 0;j < 8; j ++){
            if(neighbours[j]!=null&&neighbours[j].getType().compareTo("black") != 0){
                neighbours[j].calcNormalBuffer();    
                for(int i = 0;i < 8; i ++){
                    if(neighbours[i]!=null&&neighbours[i].getType().compareTo("black") != 0){
                        neighbours[j].neighbours[i].calcNormalBuffer();
                    }
                }
            }
        }
        this.calcNormalCornerBuffer();
        for(int j = 0;j < 8; j ++){
            if(neighbours[j]!=null&&neighbours[j].getType().compareTo("black") != 0){
                neighbours[j].calcNormalCornerBuffer();
                for(int i = 0;i < 8; i ++){
                    if(neighbours[i]!=null&&neighbours[i].getType().compareTo("black") != 0){
                        neighbours[j].neighbours[i].calcNormalCornerBuffer();
                    }
                }
            }
        }
    }
    
    
    public void draw(GL gll) {
        GL2 gl = gll.getGL2();
        //Drawing triangles: geschnitten wird in x = y richtung 
        if (marked) {
            gl.glColor3f(1, 0, 0);
        }else{
            gl.glColor3fv(FloatBuffer.wrap(getColor3f()));
        }
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        //gl.glNormal3fv(FloatBuffer.wrap(normalBuffer[0].getBuffer()));    
        gl.glNormal3fv(FloatBuffer.wrap(normalCornerBuffer[0].getBuffer())); 
        gl.glVertex3fv(FloatBuffer.wrap(getCornerBuffer(0)));
        gl.glNormal3fv(FloatBuffer.wrap(normalCornerBuffer[1].getBuffer())); 
        gl.glVertex3fv(FloatBuffer.wrap(getCornerBuffer(1)));
        gl.glNormal3fv(FloatBuffer.wrap(normalCornerBuffer[2].getBuffer())); 
        gl.glVertex3fv(FloatBuffer.wrap(getCornerBuffer(2)));
        gl.glEnd();
        
        
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        //gl.glNormal3fv(FloatBuffer.wrap(normalBuffer[1].getBuffer()));
        gl.glNormal3fv(FloatBuffer.wrap(normalCornerBuffer[0].getBuffer())); 
        gl.glVertex3fv(FloatBuffer.wrap(getCornerBuffer(0)));
        gl.glNormal3fv(FloatBuffer.wrap(normalCornerBuffer[2].getBuffer())); 
        gl.glVertex3fv(FloatBuffer.wrap(getCornerBuffer(2)));
        gl.glNormal3fv(FloatBuffer.wrap(normalCornerBuffer[3].getBuffer())); 
        gl.glVertex3fv(FloatBuffer.wrap(getCornerBuffer(3)));    
        gl.glEnd();
        
        for(int i = 0; i < drawCorner.length; i++){
            if(drawCorner[i])
                drawCorner(gl,i);
        }


        
    }
    
    public void drawCorner(GL gll, int Nr){
        GL2 gl = gll.getGL2();
        GLU glu = new GLU();
        
        gl.glColor3f(1, 0, 0);
        
        GLUquadric earth = glu.gluNewQuadric();
        gl.glTranslatef(getCorner(Nr).x, getCorner(Nr).y, getCorner(Nr).z);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 1f;
        final int slices = 16;
        final int stacks = 16;
        glu.gluSphere(earth, radius, slices, stacks);
        gl.glTranslatef(-getCorner(Nr).x, -getCorner(Nr).y, -getCorner(Nr).z);
        glu.gluDeleteQuadric(earth);
    }
    
    /**
     * Turns the drawing of specified corner on or off. 
     * @param Nr only 0,1,2 and 3 allowed
     */
    public void drawCorner(int Nr){
        if(Nr >= 0 && Nr < 4){
            drawCorner[Nr] = !drawCorner[Nr];
        }
    }
    
    /**
     * Turns the drawing of specified neighbour on or off. 
     * @param Nr only 0,1,2 to 7 are allowed
     */
    public void drawNeighbour(int Nr){
        if(Nr >= 0 && Nr < 8){
            if(drawNeighbour[Nr]){
                neighbours[Nr].setColor3f(0, 0, 0);
            }else{
                neighbours[Nr].setColor3f(0.2f, 0, 0.8f);
            }
            drawNeighbour[Nr] = !drawNeighbour[Nr];
        }
    }
    
    public void calcDistanceToCam(Vertex v){
        distanceToCam = Math.sqrt(Math.pow(v.x-xPos,2)+Math.pow(v.y-yPos,2)+Math.pow(v.z-height,2));
    }
    
    public void setMarked(boolean marked){
        this.marked = marked;
    }
    
    public void drawEntities(GL gl){
            for(int i = 0; i < entities.size();i++){
            entities.get(i).draw(gl);
        }
    }
    
    
    /*
    public collideable isColliding(Point p){
        Entity e;
        for(int i = 0; i < entities.size();i++){
            e = entities.get(i);
            if(e instanceof collideable){
                collideable c = (collideable) e;
                if(c.isColliding(p))
                    return c; 
            }
        }
        return null;
    }
    * 
    * */

    
    /**
     * Returns the neighbour at the position nr, 0 is above, than clockwise, maximum 7
     * @param nr Position of the neighbour
     * @return returns null if nr > 7
     */
    public Tile getNeighbour(int nr){
        if(nr < 8){
            if(neighbours[nr]!= null)
                return neighbours[nr];
            else{
                //return new Tile();
                return null;
            }
        }else{
            return null;
        }
    }
    
    public void setPassable(boolean passable){
        this.passable=passable;
    }
    public void setUnpassable(){
        passable = false;
    }
    public void setUnbuildable(){
        buildable = false;
    }
    
    public void setShowHeight(boolean set){
        showHeight = set;
    }
    public int getXNr(){
        return xNr;
    }
    public int getYNr(){
        return yNr;
    }
    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
    public Point getCenter(){
        return center;
    }
    public int getXSize(){
        return sprite.getWidth();
    }
    public int getYSize(){
        return sprite.getHeight();
    }
    public String getType(){
        return type;
    }
    public double getHeight(){
        return height;
    }
    public boolean isMarkable(){
        return markable;
    }
    
    /*
    public void setRiver(boolean river) {
        if(river = true){
            this.setColor3f(0, 0, 1);
        }else{
            this.calcColor();
        }
        this.river = river;
    }
    * */
    
    public void setColor3f(float a, float b, float c){
        float[] color = {a,b,c};
        color3f = color;
    }
    
    public void calcNormalBuffer(){
        if(this.xNr==1&&this.yNr==1){
            int h = 0;
        }
        normalBuffer[0] = Vertex.calcUnitNormal(Vertex.calcDirVertex(getCorner(0), getCorner(1)), 
                                             Vertex.calcDirVertex(getCorner(0), getCorner(2))
                );
        normalBuffer[0].z = normalBuffer[0].z*-1;
        normalBuffer[1] = Vertex.calcUnitNormal(Vertex.calcDirVertex(getCorner(0), getCorner(2)), 
                                             Vertex.calcDirVertex(getCorner(0), getCorner(3))
                );
        normalBuffer[1].z = normalBuffer[1].z*-1;
    }
    
    /**
     * Calculates the Vertex at corner 0,  by taking the average of all surrounding normal buffers.
     */
    public void calcNormalCornerBuffer(){
        if(this.xNr==1&&this.yNr==1){
            int h = 0;
        }
        float xSum = normalBuffer[0].x+normalBuffer[1].x+neighbours[0].normalBuffer[0].x+neighbours[7].normalBuffer[0].x+neighbours[7].normalBuffer[1].x+neighbours[6].normalBuffer[1].x;
        float ySum = normalBuffer[0].y+normalBuffer[1].y+neighbours[0].normalBuffer[0].y+neighbours[7].normalBuffer[0].y+neighbours[7].normalBuffer[1].y+neighbours[6].normalBuffer[1].y;
        float zSum = normalBuffer[0].z+normalBuffer[1].z+neighbours[0].normalBuffer[0].z+neighbours[7].normalBuffer[0].z+neighbours[7].normalBuffer[1].z+neighbours[6].normalBuffer[1].z;
        normalCornerBuffer[0] = new Vertex(xSum,
                                           ySum,
                                           zSum);  
        normalCornerBuffer[0] = Vertex.calcUnitVertex(normalCornerBuffer[0]);
        normalCornerBuffer[0].z *= 1; 
    }
    
    /**
     * Collects the buffers for all other corners from neighbours. calcNormalCornerBuffer() must FIRST be called for all tiles!
     */
    public void setNormalCornerBuffers(){
        if(neighbours[4]!=null&&neighbours[4].getType().compareTo("black")!=0){
            normalCornerBuffer[1]=neighbours[4].normalCornerBuffer[0];
        }else{
            normalCornerBuffer[1] = normalBuffer[0];
        }
        
        if(neighbours[3]!=null&&neighbours[3].getType().compareTo("black")!=0){
            normalCornerBuffer[2]=neighbours[3].normalCornerBuffer[0];
        }else{
            normalCornerBuffer[2] = Vertex.calcUnitVertex(Vertex.addVertices(Vertex.addVertices(normalBuffer[0],normalBuffer[1]),neighbours[4].normalBuffer[1]));
        }
        
        if(neighbours[2]!=null&&neighbours[2].getType().compareTo("black")!=0){
            normalCornerBuffer[3]=neighbours[2].normalCornerBuffer[0];
        }else{
            normalCornerBuffer[3] = Vertex.calcUnitVertex(Vertex.addVertices(Vertex.addVertices(normalBuffer[1],neighbours[0].normalBuffer[1]),neighbours[0].normalBuffer[0]));
        }
    }
    
    
       /**
    * Calculates distance to two planes to decide wether a point is in front of the camera or behind it
    * @return 
    */
   public void calcInFrontOfCam(){
       Camera camera = GV.get().getCamera();
       Vertex tileVertex = new Vertex((float)getXPos(),(float)getYPos(),(float)getHeight());
       Vertex cameraPos = new Vertex((float)camera.getX(),(float)camera.getY(),(float)camera.getHeight());
       Vertex focusPos = new Vertex((float)camera.getFocusX(),(float)camera.getFocusY(),(float)camera.getFocusZ());
       Vertex viewDirection = Vertex.subVertices(cameraPos, focusPos);
       double distanceToCamPlane = (viewDirection.x*tileVertex.x+viewDirection.y*tileVertex.y+viewDirection.z*tileVertex.z-Vertex.getScalar(viewDirection, cameraPos))/viewDirection.length;
       if (distanceToCamPlane <= 1 && distanceToCamPlane>=-1) {
           inFrontOfCam= true;
       }
       cameraPos.add(viewDirection.getUnitVertex());
       double distanceToFrontCamPlane = (viewDirection.x*tileVertex.x+viewDirection.y*tileVertex.y+viewDirection.z*tileVertex.z-Vertex.getScalar(viewDirection, cameraPos))/viewDirection.length;
       
        if (Math.abs(distanceToFrontCamPlane) > Math.abs(distanceToCamPlane)) {

            inFrontOfCam= false;
        }
        inFrontOfCam= true;
    }
   
   public boolean isInFrontOfCam(){
       return inFrontOfCam;
   }
    
    public synchronized Entity hasEntity(String entity){
        for(int i = 0; i < entities.size();i++){
            if(entities.get(i).getClass().getName().compareTo(entity) == 0){
                return entities.get(i);
            }
        }
        return null;
    }
    public Point getPos(){
        return new Point(xNr*GV.get().getXTileSize(),yNr*GV.get().getYTileSize());
    }
    
    public Vertex getCorner(int nr){
        if(nr >= 0 && nr < corners.length){
            return corners[nr];
        }else{
            return null;
        }
    }
    
    public int hasNeighbour(String type){
        int amount = 0;
        for(int i = 0;i < 8; i++ ){
            if(neighbours[i].getType().compareTo(type)==0)
                amount++;
        }
        return amount;
    }
        public int hasNeighbourAtBorder(String type){
        int amount = 0;
        for(int i = 0;i < 8; i = i + 2 ){
            if(neighbours[i].getType().compareTo(type)==0)
                amount++;
        }
        return amount;
    }

    public int compareTo(Tile o) {
        if( distanceToCam < o.distanceToCam )
            return -1;
        if( distanceToCam > o.distanceToCam )
            return 1;
            
        return 0;

    }
    
    public void doSmoothCorners(){
        corners[0] = new Vertex((float)xPos,(float)yPos,(float)height);
        corners[1] = new Vertex((float)xPos,(float)yPos+GV.get().getYTileSize(),(float)getNeighbour(4).height);
        corners[2] = new Vertex((float)xPos+GV.get().getXTileSize(),(float)yPos+GV.get().getYTileSize(),(float)getNeighbour(3).height);
        corners[3] = new Vertex((float)xPos+GV.get().getXTileSize(),(float)yPos,(float)getNeighbour(2).height);   
    }
    public void doRoughCorners(){
        corners[0] = new Vertex((float)xPos,(float)yPos,(float)height);
        corners[1] = new Vertex((float)xPos,(float)yPos+GV.get().getYTileSize(),(float)height);
        corners[2] = new Vertex((float)xPos+GV.get().getXTileSize(),(float)yPos+GV.get().getYTileSize(),(float)height);
        corners[3] = new Vertex((float)xPos+GV.get().getXTileSize(),(float)yPos,(float)height);   
    
    }
    
    public float[] getCornerBuffer(int corner){
        if(corners == null){
            System.err.println(Tile.class.getPackage() + "  " + Tile.class.getName() + "    Maybe doCornerMathod not called yet.");
        }
        return corners[corner].getBuffer();
    }
    
    public void calcColor(){
        Triangle t = new Triangle(corners[0],corners[1],corners[2]);
        t.recalcColor();
        color3f = t.color3f;
        
    }
    
    

    public float[] getColor3f() {
        return color3f;
    }

    public void setWater() {
        water = true;
        setColor3f(0f,0f,1f);
    }

    public boolean getWater() {
        return water;
    }



    public int getX() {
        return xPos;
    }



    public int getY() {
        return this.yPos;
    }

    public aStarAble[] getNeighbours() {
        return neighbours;
    }
    
    public boolean isPassAble(){
        return passable;
    }

    
    
    
    
    class Triangle{
        Vertex a;
        Vertex b;
        Vertex c;
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
}

 