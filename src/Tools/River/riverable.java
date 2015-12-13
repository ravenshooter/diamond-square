/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.River;

/**
 * Can be implemented by TileMap to call setColor() in RiverTile, 
 * to easily color the river on the map
 */
public interface riverable {
    /**
     * sets the spceified color at the position x,y
     * @param x xPosition of the colored Tile
     * @param y yPosition of the colored Tile
     * @param r 
     * @param g
     * @param b 
     */
    public void setColorAt(int x, int y, float r, float g, float b);
    public void setPassableAt(int x, int y, boolean passable);
}
