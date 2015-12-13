
package Snake;

import TileMap.Tile;
import TileMap.TileMap;
import Tools.GV;

public class SnakeTile 
{
    
    Tile tile;
    SnakeTile follower;
    
    /**
     * Constructor for objects of class Marker
     */
    public SnakeTile(int x,int y)
    {
        tile = GV.get().getTileMap().getTile(x,y);
        tile.setMarked(true);
    }
    
    
    

    public void add(SnakeTile tile){
        follower = tile;
    }
    public void remove(){
        tile.setMarked(false);
    }
    
    public int getXPos(){
        return tile.getXNr();
    }
    public int getYPos(){
        return tile.getYNr();
    }
    
    public Tile getMarkedTile(){
        return this.tile;
    }
        
}

