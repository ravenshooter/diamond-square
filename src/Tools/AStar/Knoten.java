/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.AStar;

import TileMap.TileMap;
import Tools.GV;



/**
 *
 * @author Steve
 */
public class Knoten {
    int x,y;
    double height;
    double h;//heuristic
    double f;//
    double distance;
    double[] kanten;
    Knoten[] neighbours;
    aStarAble[] aStarNeighbours;
    Knoten vorgänger;
    boolean passable;
    aStarAble a;
    
    public Knoten(aStarAble a){
        this.x = a.getX();
        this.y = a.getY();
        this.height = a.getHeight();
        this.passable = a.isPassAble();
        this.a = a;
        aStarNeighbours = a.getNeighbours();
        neighbours = new Knoten[8];
        kanten = new double[8];
        f = -1;
        
    }
    
    public void setNeighbours(Graph graph){
        for(int i = 0; i < 8;i++){
            if(aStarNeighbours[i] != null && aStarNeighbours[i].getType().compareTo("black")!=0){
                Knoten k = graph.getKnoten(aStarNeighbours[i].getX(),aStarNeighbours[i].getY());
                kanten[i] = Math.sqrt(Math.pow(this.x-k.getX(),2)+Math.pow(this.y-k.getY(), 2)+Math.pow(this.height-k.getHeight(),2));
                neighbours[i] = k;
            }else{
                neighbours[i] = null;
            }
        }
    }
    
    public void calcHeuristic(Knoten goal){
        h = Math.sqrt(Math.pow(this.x-goal.getX(),2)+Math.pow(this.y-goal.getY(), 2)+Math.pow(this.height-goal.getHeight(),2));
    }
    
    /**
     * Calculates heuristic value (estimated distance to goal) without height
     * @param goal 
     */
    public void calcHeuristicWithoutHeight(Knoten goal){
        h = Math.sqrt(Math.pow(this.x-goal.getX(),2)+Math.pow(this.y-goal.getY(), 2));
    }
    
    public void setF(double f){
        this.f=f;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getH() {
        return h;
    }

    public double getF() {
        return f;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    
    public Knoten getNeighbour(int i){
        if(i < neighbours.length && i >=0){
            return neighbours[i];
        }
        return null;
    }
    
    public double getKante(int i){
        if(i < neighbours.length && i >=0){
            return kanten[i];
        }
        return -1;
    }

    public Knoten getVorgänger() {
        return vorgänger;
    }

    public void setVorgänger(Knoten vorgänger) {
        this.vorgänger = vorgänger;
    }
    
    public boolean isPassable(){
        return a.isPassAble();
    }
    
    public void setColorAtTileMap(TileMap tilemap){
        GV.get().getTileMap().setColorAt(this.getX()/GV.get().getXTileSize(), this.getY()/GV.get().getYTileSize(), 1f, 1f, 0);
        if( vorgänger != null){
            vorgänger.setColorAtTileMap(tilemap);
        }
    }
    

    
    
}
