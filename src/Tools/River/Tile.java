/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.River;


import java.util.ArrayList;

/**
 *
 * @author Steve
 */
public class Tile implements Comparable<Tile>{
    
    int xNr;
    int yNr;
    double height;
    River tileMap;

    
    /**
     * Referenzen auf die Umgebung des Tiles, 0 ist das niedrigste, 7 das h�chste
     */
    Tile neighbours[];

    public Tile(int x, int y, double height, River tileMap) {
        this.xNr = x;
        this.yNr = y;
        this.height = height;
        this.tileMap = tileMap;
    }

    /*
    public void setNeighbours(){
        ArrayList<Tile> tiles = new ArrayList();
        if(tileMap.getTile(xNr,yNr-1)!=null){
            tiles.add(tileMap.getTile(xNr,yNr-1));
        }
        if(tileMap.getTile(xNr+1,yNr)!=null){
            tiles.add(tileMap.getTile(xNr+1,yNr));
        }
        if(tileMap.getTile(xNr+1,yNr+1)!=null){
            tiles.add(tileMap.getTile(xNr+1,yNr+1));
        }
        if(tileMap.getTile(xNr,yNr+1)!=null){
            tiles.add(tileMap.getTile(xNr,yNr+1));
        }
        if(tileMap.getTile(xNr-1,yNr+1)!=null){
            tiles.add(tileMap.getTile(xNr-1,yNr+1));
        }
        if(tileMap.getTile(xNr-1,yNr)!=null){
            tiles.add(tileMap.getTile(xNr-1,yNr));
        }
        if(tileMap.getTile(xNr-1,yNr-1)!=null){
            tiles.add(tileMap.getTile(xNr-1,yNr-1));
        }
        neighbours = new Tile[tiles.size()];
        for(int i = 0; i < tiles.size();i++){
            neighbours[i] = tiles.get(i);
        }
        
        java.util.Arrays.sort( neighbours );
    }
    * */
        public void setNeighbours(){
        ArrayList<Tile> tiles = new ArrayList();
        if(tileMap.getTile(xNr,yNr-1)!=null){
            tiles.add(tileMap.getTile(xNr,yNr-1));
        }
        if(tileMap.getTile(xNr+1,yNr)!=null){
            tiles.add(tileMap.getTile(xNr+1,yNr));
        }
        if(tileMap.getTile(xNr,yNr+1)!=null){
            tiles.add(tileMap.getTile(xNr,yNr+1));
        }
        
        if(tileMap.getTile(xNr-1,yNr)!=null){
            tiles.add(tileMap.getTile(xNr-1,yNr));
        }

        neighbours = new Tile[tiles.size()];
        for(int i = 0; i < tiles.size();i++){
            neighbours[i] = tiles.get(i);
        }
        
        java.util.Arrays.sort( neighbours );
    }
    
    
    public int getX(){
        return xNr;
    }

    public int getY() {
        return yNr;
    }


    
    public int compareTo(Tile o) {
        if( height < o.height )
            return -1;
        if( height > o.height )
            return 1;
            
        return 0;
    }



    
    
    
}
