
package TileMap;

import JOGL.JOGLScene;
import JOGL.LoadScene;
import JOGL.TestScene;
import Snake.Snake;
import TileMap.Entities.Unit;
import Tools.AStar.Graph;
import Tools.AStar.Knoten;

import Tools.DiamondSquare;
import GUI.Editor;
import Game.Canon;
import Tools.GV;
import Tools.GameWindow;
import Tools.River.River;
import Tools.River.RiverTile;

public class Main {

    public static void main(String[]args){
        GameWindow gw = new GameWindow();
        LoadScene loadScene = new LoadScene();
        gw.loadScene("LoadScene");
        new JOGLScene(gw.getFrame());
        TileMap tilemap = new TileMap(GV.get().getXTiles(),GV.get().getYTiles(),GV.get().getXTileSize(),GV.get().getYTileSize());
        tilemap.finishMap();
        double heightmap[][] = new double[GV.get().getXTiles()][GV.get().getYTiles()];
        DiamondSquare ds = new DiamondSquare();
        heightmap = ds.diamondSquare(heightmap, GV.get().getHeightSeed());
        tilemap.setHeight(heightmap,ds.getMaxHeight(),ds.getMinHeight());
        //tilemap.doGroundTypes();
        tilemap.doSmoothCorners();
        tilemap.doCalcShade();
        //tilemap.doColorByHeight();
        new Marker();
        //new Snake();
        
        new Graph(tilemap.getTileMap());
        
        loadScene.setDone();
        

        //gw.loadScene("JOGLScene");
        

    }
}
