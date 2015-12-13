/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.River;

import TileMap.TileMap;

/**
 *
 * @author Steve
 */
public class RiverTile extends Tile{
    

    RiverTile source;
    int armsNumber;
    RiverTile[] arms;
    


    
    public RiverTile(Tile tile, RiverTile source){
        super(tile.xNr,tile.yNr,tile.height, tile.tileMap);
        neighbours = tile.neighbours;
        arms = new RiverTile[4];
        armsNumber = 0;
        this.source = source;
    }
    

    public RiverTile getSource() {
        return source;
    }
    
    
    /**
     * Adds a new riverarm (beginning from the lowest neighbour) to this tile, the new rivertile is returned
     * @return the new arm (rivertile) leading away from this rivertile.
     */
    public RiverTile addArm(){
        if(armsNumber < neighbours.length){
            arms[armsNumber] = new RiverTile(neighbours[armsNumber],this);
            armsNumber++;
            return arms[armsNumber-1];
        }
        return null;
    }
    
    synchronized boolean contains(Tile tile){
        if (!tile.equals(this)) {
            if (tile.xNr == xNr && tile.yNr == yNr) {
                return true;
            } else {
                for (int i = 0; i < armsNumber; i++) {
                    if (arms[i].contains(tile)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getArmsNumber() {
        return armsNumber;
    }

    public RiverTile[] getArms() {
        return arms;
    }
    
    
    public void setColorAtTileMap(riverable colorable){
        colorable.setPassableAt(xNr,yNr,false);
        colorable.setColorAt(xNr, yNr,0, 0, 1f);
                for (int i = 0; i < armsNumber; i++) {
                    arms[i].setColorAtTileMap(colorable);
                }
    }
    
    
    
    
    
    
  
}
