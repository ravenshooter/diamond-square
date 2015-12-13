/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.AStar;

import Tools.AStar.List.Element;
import Tools.GV;

/**
 *
 * @author Steve
 */
public class Graph{

    
    
    Knoten[][] knotenTileMap;
    public Graph(aStarAble[][] aStarList){
        knotenTileMap = new Knoten[aStarList.length][aStarList[0].length];
        for(int i = 0; i < aStarList.length;i++){
            for(int j = 0; j < aStarList[0].length;j++){
                knotenTileMap[j][i] = new Knoten(aStarList[i][j]);
            }
        }
        for(int i = 0; i < aStarList.length;i++){
            for(int j = 0; j < aStarList[0].length;j++){
                knotenTileMap[j][i].setNeighbours(this);
            }
        }
        GV.get().setGraph(this);
        

    }
    
    
    public Knoten getKnoten(int x, int y){
        if(x/GV.get().getXTileSize()>=0&&x/GV.get().getXTileSize()<GV.get().getXTiles()&&y/GV.get().getYTileSize()>=0&&y/GV.get().getYTileSize()<GV.get().getXTiles()){
        return knotenTileMap[x/GV.get().getXTileSize()][y/GV.get().getYTileSize()];
        }else{
            return null;
        }
    }
    
    /**
     * Calculates the shortest path from start to destination including height differences
     * @param x
     * @param y
     * @param xEnd
     * @param yEnd
     * @return last node of the path, get next node by call "getVorgänger"
     */

    
    
    public Knoten calcPath(int x, int y, int xEnd, int yEnd){
        Knoten start = getKnoten(x,y);
        Knoten end = getKnoten(xEnd,yEnd);
        if(start==null){
            System.err.println("Starting point is not inside TileMap");
            return null;
        }
        if(end==null){
            System.err.println("Ending point is not inside TileMap");
            return null;
        }
            for(int i = 0; i < knotenTileMap.length;i++){
                for(int j = 0; j < knotenTileMap[0].length;j++){
                    knotenTileMap[j][i].calcHeuristic(end);
                }
            }
        
        
        List openList = new List();
        List closedList = new List();
        //GV.get().getTileMap().setColorAt(end.getX()/5, end.getY()/5, 0f, 1f, 1f);
        Knoten best = start;
        best.setF(0+0+best.getH()); 
        best.setDistance(0);
        while(best.getX()!= end.getX()||best.getY()!=end.getY()){
            for(int i = 0; i < 8; i ++){
                if(best.getNeighbour(i)!=null){
                    if(!closedList.hasElement(best.getNeighbour(i).getX(), best.getNeighbour(i).getY())){
                        if(best.getNeighbour(i).isPassable()){
                            if(!openList.hasElement(best.getNeighbour(i).getX(), best.getNeighbour(i).getY())){
                                best.getNeighbour(i).setDistance(best.getDistance() + best.getKante(i));
                                best.getNeighbour(i).setF(best.getNeighbour(i).getDistance()+best.getNeighbour(i).getH());
                                best.getNeighbour(i).setVorgänger(best);
                                openList.add(best.getNeighbour(i));
                            }else{
                                if(best.getNeighbour(i).getDistance()>best.distance+best.kanten[i]){
                                    best.getNeighbour(i).setDistance(best.getDistance() + best.getKante(i));
                                    best.getNeighbour(i).setF(best.getNeighbour(i).getDistance()+best.getNeighbour(i).getH());
                                }
                            }
                        }
                        //GV.get().getTileMap().setColorAt(best.getNeighbour(i).getX()/5, best.getNeighbour(i).getY()/5, 0, 0.5f, 0);
                    }

                }
            }
            if(openList.getCounter()!=0){
                openList.removeElement(best);
                GV.get().getTileMap().setColorAt(best.getX()/5, best.getY()/5, 0, 0.5f, 0);
            }else{
                System.err.println("No possible way found");
                return null;
            }
            closedList.add(best);
            //GV.get().getTileMap().setColorAt(best.getX()/5, best.getY()/5, 0f, 1f, 0);
            best = openList.removeBest();
            //GV.get().getTileMap().setColorAt(best.getX()/5, best.getY()/5, 1f, 0.5f, 0);
        }
        Knoten k = end;
        while(k != null){
            GV.get().getTileMap().setColorAt(k.getX()/5, k.getY()/5, 1f, 1f, 0);
            k = k.getVorgänger();
        }
        GV.get().getEditor().update();
        GV.get().addPath(end);
        return end;
        
    }
    
    
    
    
    public Knoten calcPathWithouthHeight(int x, int y, int xEnd, int yEnd){
        Knoten start = getKnoten(x,y);
        Knoten end = getKnoten(xEnd,yEnd);
        if(start==null){
            System.err.println("Starting point is not inside TileMap");
            return null;
        }
        if(end==null){
            System.err.println("Ending point is not inside TileMap");
            return null;
        }
            for(int i = 0; i < knotenTileMap.length;i++){
                for(int j = 0; j < knotenTileMap[0].length;j++){
                    knotenTileMap[j][i].calcHeuristicWithoutHeight(end);
                }
            }
        
        
        List openList = new List();
        List closedList = new List();
        //GV.get().getTileMap().setColorAt(end.getX()/5, end.getY()/5, 0f, 1f, 1f);
        Knoten best = start;
        best.setF(0+0+best.getH()); 
        best.setDistance(0);
        while(best.getX()!= end.getX()||best.getY()!=end.getY()){
            for(int i = 0; i < 8; i ++){
                if(best.getNeighbour(i)!=null){
                    if(!closedList.hasElement(best.getNeighbour(i).getX(), best.getNeighbour(i).getY())){
                        if(best.getNeighbour(i).isPassable()){
                            if(!openList.hasElement(best.getNeighbour(i).getX(), best.getNeighbour(i).getY())){
                                best.getNeighbour(i).setDistance(best.getDistance() + Math.sqrt(Math.pow(best.getX()-best.getNeighbour(i).getX(),2)+Math.pow(best.getY()-best.getNeighbour(i).getY(), 2)));
                                best.getNeighbour(i).setF(best.getNeighbour(i).getDistance()+best.getNeighbour(i).getH());
                                best.getNeighbour(i).setVorgänger(best);
                                openList.add(best.getNeighbour(i));
                            }else{
                                if(best.getNeighbour(i).getDistance()>best.distance+Math.sqrt(Math.pow(best.getX()-best.getNeighbour(i).getX(),2)+Math.pow(best.getY()-best.getNeighbour(i).getY(), 2))){
                                    best.getNeighbour(i).setDistance(best.getDistance() + Math.sqrt(Math.pow(best.getX()-best.getNeighbour(i).getX(),2)+Math.pow(best.getY()-best.getNeighbour(i).getY(), 2)));
                                    best.getNeighbour(i).setF(best.getNeighbour(i).getDistance()+best.getNeighbour(i).getH());
                                }
                            }
                        }
                        //GV.get().getTileMap().setColorAt(best.getNeighbour(i).getX()/5, best.getNeighbour(i).getY()/5, 0, 0.5f, 0);
                    }

                }
            }
            if(openList.getCounter()!=0){
                openList.removeElement(best);
                GV.get().getTileMap().setColorAt(best.getX()/5, best.getY()/5, 0, 0.8f, 0);
            }else{
                System.err.println("No possible way found");
                return null;
            }
            closedList.add(best);
            //GV.get().getTileMap().setColorAt(best.getX()/5, best.getY()/5, 0f, 1f, 0);
            best = openList.removeBest();
            //GV.get().getTileMap().setColorAt(best.getX()/5, best.getY()/5, 1f, 0.5f, 0);
        }
        Knoten k = end;
        while(k != null){
            GV.get().getTileMap().setColorAt(k.getX()/5, k.getY()/5, 0.75f, 0.75f, 0);
            k = k.getVorgänger();
        }
        GV.get().addPath(end);
        GV.get().getEditor().update();
        return end;
        
    }
        
    
    
    public Knoten calcPath(aStarAble startAStar, aStarAble endAStar){
        return calcPath(startAStar.getX(),startAStar.getY(), endAStar.getX(), endAStar.getY());
    }

    
}
