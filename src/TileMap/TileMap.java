/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap;


import Tools.Vertex;
import Tools.GV;
import Tools.River.riverable;
import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Point;


/**
 * Write a description of class TileMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TileMap implements riverable
{
    private Tile tileMap[][];
    int width;
    int height;
    int x_TileSize;
    int y_TileSize;
    int water_state = 0;
    
    double maxHeight,minHeight;
    
    Vertex cameraPos;
    
    Tile[]drawList;
    Tile[]heightList;
    boolean enable_water;
    Sprite water[];
    /**
     * The most basic element on which all others are layered
     */
    String basic_ground = "grass";
    
    /**
     * Erzeugt eine neue TileMap
     * @param width Anzahl der Tiles in X-Richtung
     * @param height Anzahl der Tiles in Y-Richtung
     * @param x_TileSize Anzahl der Pixel jedes Tiles in X Richtung
     * @param y_TileSize Anzahl der Pixel jedes Tiles in X Richtung
     */
    public TileMap(int width, int height,int x_TileSize,int y_TileSize)
    {
        GV.get().setTileMap(this);
        tileMap = new Tile[height][width];
        this.width = width;
        this.height = height;
        this.x_TileSize = x_TileSize;
        this.y_TileSize = y_TileSize;
        if(enable_water){
            water = new Sprite[10];
            for(int j = 0; j <10;j++){
                water[j] = SpriteStore.get().getSprite("water_background0"+j+".bmp");
            }
        }
        cameraPos = new Vertex(-1f,0f,0f);
        
    }
    
    /**
     * Lädt eine existierende TileMap
     * @param tileMap 
     */
    public TileMap(Tile [][] tileMap)
    {
        GV.get().setTileMap(this);
        this.tileMap = tileMap;
        height = tileMap.length;
        width = tileMap[0].length;
        x_TileSize = tileMap[0][0].getXSize();
        y_TileSize = tileMap[0][0].getYSize();
        if(enable_water){
            water = new Sprite[10];
            for(int j = 0; j <10;j++){
                water[j] = SpriteStore.get().getSprite("water_background0"+j+".bmp");
            }
        }
        cameraPos = new Vertex(-1f,0f,0f);
    }
    
    public void add(int x, int y, String type){
        tileMap[y][x] = new Tile(this,type,x,y);
    }

    public void add(int x, int y, Tile tile){
        tileMap[y][x] = tile;
    }
    
    /**
     * Fuellt leere Felder aus und verfollstaendigt Nachbarn
     */
    public void finishMap(){
        //System.out.println(width + "/" + height);
        for(int i = 0; i < height; i ++){
            for(int j = 0; j < width;j++){
                if(tileMap[i][j]==null){
                    add(j,i,basic_ground);
                }
            }
        }
        
        
        /*
         * Sets neighbours for all tiles.
         */
        for(int i = 0; i < GV.get().getYTiles(); i ++){
            for(int j = 0; j < GV.get().getXTiles();j++){
                tileMap[i][j].setNeighbours(this,j,i);
            }
        }
        
        /*
         * Calculates normal buffers for both triangles
         */
        for(int i = 0; i < GV.get().getYTiles(); i ++){
            for(int j = 0; j < GV.get().getXTiles();j++){
                //
            }
        }
               
        /*
         * Calculates normal buffers for corner [0]
         */
        for(int i = 0; i < GV.get().getYTiles(); i ++){
            for(int j = 0; j < GV.get().getXTiles();j++){
                tileMap[i][j].calcNormalCornerBuffer();
            }
        }
        
        /*
         * Gets cornerbuffers from surrounding tiles
         */
        for(int i = 0; i < GV.get().getYTiles(); i ++){
            for(int j = 0; j < GV.get().getXTiles();j++){
                tileMap[i][j].setNormalCornerBuffers();
            }
        }
    }
    
    /**
     * Called by the MouseHandler when the mouse is clicked in the area of the tilemap
     */
    /*
    public void mouseClicked(int xM, int yM){
        Camera camera = GV.get().getCamera();
        //int xOnTile =                  later 
        GV.get().getMarker().move(tileMap[(camera.getYPos()+yM)/y_TileSize][(camera.getXPos()+xM)/x_TileSize]);
        tileMap[(camera.getYPos()+yM)/y_TileSize][(camera.getXPos()+xM)/x_TileSize].mouseClicked();
        
    }
    * */

    /**
     * Mahlt das HIntergrundwasser, bestehend aus 4 Tiles (800*600), links oben, rechts oben, links unten, rechts unten.
     */
    public void draw(Graphics g, int x, int y) {
        int xOffset = x % (800);
        int yOffset = y % (600);
        //g.drawImage(sprite.getImage(),-xOffset,-yOffset,null);
        //g.drawImage(sprite.getImage(),-xOffset+800,-yOffset,null);
        //g.drawImage(sprite.getImage(),-xOffset,-yOffset+600,null);
        //g.drawImage(sprite.getImage(),-xOffset+800,-yOffset+600,null);
        int speed = 2;
        water_state++;
        int frameNr = (int) (Math.round(water_state / speed - 0.5));
        g.drawImage(water[frameNr].getImage(), -xOffset, -yOffset, null);
        g.drawImage(water[frameNr].getImage(), -xOffset + 800, -yOffset, null);
        g.drawImage(water[frameNr].getImage(), -xOffset, -yOffset + 600, null);
        g.drawImage(water[frameNr].getImage(), -xOffset + 800, -yOffset + 600, null);
        if (water_state == 10 * speed - 1) {
            water_state = 0;
        }


        /*
        g.drawImage(sprite.getImage(),xOffset,yOffset,800-xOffset,600-yOffset,null); //rechts unten wird immer gemahlt
        if(xOffset != 0 || yOffset != 0){
        g.drawImage(sprite.getImage(),-800+xOffset,-600+yOffset,null); //das Wasser links oben
        g.drawImage(sprite.getImage(),xOffset,-600+yOffset,800-xOffset,600,null);
        g.drawImage(sprite.getImage(),-800+xOffset,yOffset,800,600-yOffset,null);
        }
         */

    }

    /**
     * Algorithmus zur Ausbreitung von Zink an Elektroden
     * @param type Bodentyp
     * @param amount Tiles die gestreut werden sollen
     * @param islands Inslen an die die Tiles "andocken" können
     */
    /*
    public void addGroundType(String type, int amount, int islands) {
        if (amount >= islands) {
            Tile[] islandTiles = new Tile[islands];
            for (int i = 0; i < islands; i++) {
                Tile tile = this.getTile((int) (Math.random() * this.width), (int) (Math.random() * this.height));
                if (tile.getType().compareTo(type) != 0) {
                    tile.setType(type);
                    islandTiles[i] = tile;
                    System.out.println("Island set at: " + tile.getXNr() +" "  + tile.getYNr());
                } else {
                    i--;
                }
            }
            System.out.println("islands set");
            int flyers = amount - islands;
            for (int i = 0; i < flyers; i++) {
                Tile actTile = getActTile();
                Tile goToIsland = islandTiles[(int) ((Math.random() ) * islands - 0.5)];
                boolean found = false;

                while (!found) {
//            double dir = Math.atan(goToIsland.yPos-actTile.yPos/goToIsland.xPos-actTile.xPos);
                    double dir = GV.getAngleDeg(actTile.xPos, actTile.yPos, goToIsland.xPos, goToIsland.yPos);
                    try{
                    if (dir >= 22.5 && dir < 67.5) {
                        actTile = actTile.getNeighbour(1);
                    }
                    if (dir >= 67.5 && dir < 112.5) {
                        actTile = actTile.getNeighbour(0);
                    }
                    if (dir >= 112.5 && dir < 157.5) {
                        actTile = actTile.getNeighbour(7); 
                    }
                    if (dir >= 157.5 && dir < 202.5) {
                        actTile = actTile.getNeighbour(6);
                    }
                    if (dir >= 202.5 && dir < 247.5) {
                        actTile = actTile.getNeighbour(5);
                    }
                    if (dir >= 247.5 && dir < 292.5) {
                        actTile = actTile.getNeighbour(4);
                    }
                    if (dir >= 292.5 && dir < 337.5) {
                        actTile = actTile.getNeighbour(3);
                    }
                    if (dir >= 337.5 && dir <= 360) {
                        actTile = actTile.getNeighbour(2);
                    }
                    if (dir >= 0 && dir < 22.5) {
                        actTile = actTile.getNeighbour(2);
                    }
                    if(actTile.getType().compareTo(type)==0){
                        found = true;
                        i--;
                    }
                 
                    if (actTile.hasNeighbourAtBorder(type) > 0) {
                        found = true;
                        actTile.setType(type);
                        //System.out.println("flyer found");
                    }
                    }catch(NullPointerException e){
                        found = true;
                        //System.out.println("fake");
                        i--;
                    }

                }
            }
        }
        
        

    }
    * */


    
    private Tile getActTile() {
        Tile actTile = null;
        while (actTile == null) {
            int startside = (int) Math.round((Math.random()) * 4 -0.5);
            //legt die Startseite fest
            int startvalue; //legt den x bzw y wert an der Startseite fest;
            actTile = null;
            if (startside == 0) {
                startvalue = (int) Math.round(Math.random() * GV.get().XTiles);
                actTile = getTile(startvalue, 0);
            }
            if (startside == 1) {
                startvalue = (int) Math.round(Math.random() * GV.get().YTiles);
                actTile = getTile(GV.get().XTiles, startvalue);
            }
            if (startside == 2) {
                startvalue = (int) Math.round(Math.random() * GV.get().XTiles);
                actTile = getTile(startvalue, GV.get().YTiles);
            }
            if (startside == 3) {
                startvalue = (int) Math.round(Math.random() * GV.get().YTiles);
                actTile = getTile(0, startvalue);
            }
        }
        return actTile;

    }


    
    
    
    
    
    public Tile getTile(int xNr, int yNr){
        if(xNr >= 0 && xNr < width && yNr >= 0 && yNr < height)
            return tileMap[yNr][xNr];
        else
            return new Tile() ;
    }
    
    public Tile getHighestTile(){
        return heightList[heightList.length-1];
    }
    public Tile getLowestTile(){
        return heightList[0];
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }
    
    
    
    public void setHeight(double[][]heightMap, double maxHeight, double minHeight){
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        if(heightMap.length == tileMap.length && heightMap[0].length == tileMap[0].length){
            for(int i = 0; i < heightMap.length;i++){
                for(int j = 0; j < heightMap[0].length;j++){
                    tileMap[i][j].setHeight(heightMap[i][j]);
                    tileMap[i][j].distanceToCam = heightMap[i][j];
                }
            }
        }else{
            System.out.println("heightMap past nicht auf tileMap");
        }
        heightList = new Tile[width*height];
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                heightList[i*tileMap[0].length+j]= getTile(i,j);
            }
        }
        
        java.util.Arrays.sort( heightList );
    }
    
    
    public void doGroundTypes() {
        double range = maxHeight - minHeight;
        System.out.println(range + "  " +  maxHeight + "  " + minHeight);
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                if (tileMap[i][j].getHeight() < maxHeight-range / 2) {
                    tileMap[i][j].setType("desert");
                }
            }
        }
    }
    
    
    public void doDrawList(Vertex cameraPos){
        drawList = new Tile[width*height];
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                drawList[i*tileMap[0].length+j]= getTile(i,j);
                drawList[i*tileMap[0].length+j].calcDistanceToCam(cameraPos);
            }
        }
        
        java.util.Arrays.sort( drawList );
    }
    
    public void doSmoothCorners(){
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                tileMap[i][j].doSmoothCorners();
                tileMap[i][j].calcNormalBuffer();
            }
        }
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                tileMap[i][j].calcNormalCornerBuffer();
            }
        }
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                tileMap[i][j].setNormalCornerBuffers();
            }
        }
    }
    
    public void unDoSmoothCorners(){
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                tileMap[i][j].doRoughCorners();
                tileMap[i][j].calcNormalBuffer();
            }
        }
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                tileMap[i][j].calcNormalCornerBuffer();
            }
        }
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                tileMap[i][j].setNormalCornerBuffers();
            }
        }
    }
    
    public void doCalcShade(){
        
            for (int i = 0; i < tileMap.length; i++) {
                for (int j = 0; j < tileMap[0].length; j++) {
                    if(!GV.get().lightning){
                        tileMap[i][j].calcColor();
                    }else{
                        tileMap[i][j].setColor3f(0.5f, 0.5f, 0.5f);
                }
            }
        }
    }
    
    public void doColorByHeight(){
        double heightDif = maxHeight-minHeight;
        float height;
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                height = (float)((tileMap[i][j].getHeight()-minHeight)/heightDif);
                //tileMap[i][j].setColor3f((float)Math.sin(height*Math.PI*2), (float)Math.sin(height*Math.PI*2-Math.PI), 0);
                tileMap[i][j].setColor3f((float)(-1*Math.pow((height-0)*2,2.0)+1), (float)(-1*Math.pow((height-0.5)*3,2.0)+1), (float)(-1*Math.pow((height-1)*3,2.0)+1));
            }
        }
    }
    
    /*
    public Tile getTileByPos(int xM, int yM){
        Camera camera = GV.get().getCamera();
        return tileMap[(camera.getYPos()+yM)/y_TileSize][(camera.getXPos()+xM)/x_TileSize];
    }
    */
    
    public int getPriority(){
        return 1;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public double getMinHeight() {
        return minHeight;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getXTileSize(){
        return x_TileSize;
    }
    public int getYTileSize(){
        return y_TileSize;
    }

    public Tile[] getDrawList(int xCam, int yCam, int zCam) {
        Vertex newCameraPos = new Vertex(xCam,yCam,zCam);
        if(newCameraPos.x == this.cameraPos.x && newCameraPos.y == this.cameraPos.y && newCameraPos.z == this.cameraPos.z){
            return drawList;
        }else{
            doDrawList(newCameraPos);
            return drawList;
        }
    }
    
    public double[][] getHeightMap(){
        double[][]heightMap = new double[tileMap.length][tileMap[0].length];
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                heightMap[i][j] = tileMap[i][j].getHeight();    
            }
        }
        return heightMap;
    }

    public void setColorAt(int x, int y, float r, float g, float b) {
        getTile(x, y).setColor3f(r, g, b);
    }
    
    public void setPassableAt(int x, int y, boolean passable){
        getTile(x, y).setPassable(passable);
    }
    

}
