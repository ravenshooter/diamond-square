/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap.Entities;

import TileMap.Tile;
import Tools.GV;
import com.jogamp.opengl.GL;

/**
 *
 * @author Steve
 */
public abstract class Entity {
    
    int xPos;
    int yPos;
    Tile tile;
    
    public Entity(Tile tile){
        this.tile = tile;
        if(tile != null)
        {
            xPos = tile.getXNr() * GV.get().getXTileSize();
            yPos = tile.getYNr() * GV.get().getYTileSize();
        } else
        {
            xPos = 0;
            yPos = 0;
        }
    }
    
    public abstract void draw(GL gl);
}
