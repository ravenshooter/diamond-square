/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.River;

/**
 *
 * @author Steve
 */
public class River {
    Tile[][] map;
    int xStart;
    int yStart;
    int length;
    RiverTile river;
    RiverTile start;

    /**
     * DOES NOT CREATE A RIVER! 
     * Just prepares everything, call createRiver() after constructor, to get back the beginning of the river
     * Creates a new river at the tile x and y, with specified amount of tiles.
     * Call create river to actually
     * @param heightMap double heightmap of terrain
     * @param xStart Startposition of river
     * @param yStart Startposition of river
     * @param length Amount of tiles that the river will cover
     */
    public River(double[][] heightMap, int xStart, int yStart, int length) {
        map = new Tile[heightMap.length][heightMap[0].length];
        
        for(int i = 0; i < heightMap.length;i++){
            for(int j = 0; j < heightMap[0].length;j++){
                map[i][j] = new Tile(j,i,heightMap[i][j],this);
            }
        }
        
        for(int i = 0; i < heightMap.length;i++){
            for(int j = 0; j < heightMap[0].length;j++){
                map[i][j].setNeighbours();
            }
        }
        this.xStart = xStart;
        this.yStart = yStart;
        this.length = length;
        
    
    }
    
    /**
     * Creates a new river and stores the beginning into the submitted buffer
     * @param heightMap double heightmap of terrain
     * @param xStart Startposition of river
     * @param yStart Startposition of river
     * @param length Amount of tiles that the river will cover
     * @param buffer Buffer for the first tile of the new river, must be at least size 1;
     */
    public River(double[][] heightMap, int xStart, int yStart, int length, RiverTile[] buffer){
        River river = new River(heightMap, xStart, yStart, length);
        if(buffer.length<1){
            System.err.println("The submitted buffer is to small, size must be at least 1");
        }else{
            buffer[0] = river.createRiver();
        }
    }
    
    
    public RiverTile createRiver() {
        RiverTile newRiver;

        start = new RiverTile(getTile(xStart, yStart), null);
        river = start;
        for (int i = 0; i < length; i++) {
            newRiver = river.addArm();
            if (newRiver == null) {
                //if all arms are tried without success go back to previous tile
                river = river.source;
            } else {
                if (isPartOfThisRiver(newRiver)) {
                    //if the river leads to itself
                    //repeat with same RiverTile 
                    //until all arms are full, then jump backward to source
                } else {
                    river = newRiver;
                }
            }
        }

        return start;
    }

    
    

    
 

    protected void setTile(Tile tile){
        int xNr = tile.xNr;
        int yNr = tile.yNr;
        if(xNr >= 0 && xNr < map[0].length && yNr >= 0 && yNr < map.length)
           map[yNr][xNr] = tile;
    }
    
    protected Tile getTile(int xNr, int yNr){
        if(xNr >= 0 && xNr < map[0].length && yNr >= 0 && yNr < map.length)
            return map[yNr][xNr];
        else
            return null;
    }
    
    
    protected boolean isPartOfThisRiver(RiverTile tile){
        return start.contains(tile);
    }
    
   
    
    
}
