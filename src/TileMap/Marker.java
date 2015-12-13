/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap;

import Tools.GV;
/**
 * Write a description of class Marker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Marker
{
    
    TileMap tileMap;
    Tile tile;
    
    /**
     * Constructor for objects of class Marker
     */
    public Marker()
    {
        GV.get().setMarker(this);
        tileMap = GV.get().getTileMap();
        tile = tileMap.getTile(1,1);
        tile.setMarked(true);
    }
    
    
    
    public void move(int dir){
        Tile newTile = tile.getNeighbour(dir);
        if(newTile.getType().compareTo("black")!=0)
        {
            tile.setMarked(false);
            tile = newTile;
            tile.setMarked(true);
        }
        //System.out.println(tile.height);
    }
    
    public int getXPos(){
        return tile.xPos;
    }
    public int getYPos(){
        return tile.yPos;
    }
    
    public Tile getMarkedTile(){
        return this.tile;
    }
    
    public void printHeight(){
        System.out.println("Heigt of tile " + tile.getXNr() + "|"+tile.getY()+":  " + tile.height);
    }
        
}