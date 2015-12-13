/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap.Entities;
/**
 * Write a description of class Farm here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


import TileMap.Tile;
import java.awt.Point;
import java.awt.Rectangle;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL;

public class House extends Entity
    //implements missionMaster, collideable
{
    

    
    private int stock;
    public int expectedStock;
    int hungercounter;
    int missionTimer;
    int manInHouse;
    int discoverTileX;
    int discoverTileY;
    int distance;
    String missionBlog[];
    //LittleMan littleMen[];
    //Farm farm;
    //Mill mill;
    ArrayList missionlist;
    Rectangle collisionBox[];
    Point collisionBoxCorners[];

    
    public House(Tile tile) 
    {
        super(tile);
        discoverTileX = 0;
        discoverTileY = 0;
        distance = 0;
        this.tile = tile;
        tile.setUnpassable();
        collisionBox = new Rectangle[1];
        collisionBox[0] = new Rectangle(tile.getXPos() + 3, tile.getYPos() + 3, 28, 28);
        collisionBoxCorners = new Point[4];
        collisionBoxCorners[0] = new Point(collisionBox[0].getLocation());
        collisionBoxCorners[1] = new Point((int)(collisionBox[0].getX() + collisionBox[0].getWidth()), (int)collisionBox[0].getY());
        collisionBoxCorners[2] = new Point((int)(collisionBox[0].getX() + collisionBox[0].getWidth()), (int)(collisionBox[0].getY() + collisionBox[0].getHeight()));
        collisionBoxCorners[3] = new Point((int)collisionBox[0].getX(), (int)(collisionBox[0].getY() + collisionBox[0].getHeight()));


        manInHouse = 4;
        missionlist = new ArrayList();
        discoverTileX = tile.getXNr();
        discoverTileY = tile.getYNr();
        //littleMen = new LittleMan[manInHouse];
        //for(int i = 0; i < littleMen.length; i++)
        //    littleMen[i] = new LittleMan(i, new Point(tile.getPos()), this);

    }

    
    public void draw(GL gl)
    {

        
        float[] Corner0 = tile.getCornerBuffer(0);
        float[] Corner1 = tile.getCornerBuffer(1);
        float[] Corner2 = tile.getCornerBuffer(2);
        float[] Corner3 = tile.getCornerBuffer(3);
        
        //wand1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1f, 1f, 1f);
        gl.glVertex3f(Corner0[0],Corner0[1],Corner0[2]);
        gl.glVertex3f(Corner0[0],Corner0[1],Corner0[2]+10);
        gl.glVertex3f(Corner1[0],Corner1[1],Corner1[2]+10);
        gl.glVertex3f(Corner1[0],Corner1[1],Corner1[2]);
        
        gl.glEnd();
        
        //wand2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1f, 1f, 1f);
        gl.glVertex3f(Corner1[0],Corner1[1],Corner1[2]);
        gl.glVertex3f(Corner1[0],Corner1[1],Corner1[2]+10);
        gl.glVertex3f(Corner2[0],Corner2[1],Corner2[2]+10);
        gl.glVertex3f(Corner2[0],Corner2[1],Corner2[2]);
        
        gl.glEnd();
        
        //wand3
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1f, 1f, 1f);
        gl.glVertex3f(Corner2[0],Corner2[1],Corner2[2]);
        gl.glVertex3f(Corner2[0],Corner2[1],Corner2[2]+10);
        gl.glVertex3f(Corner3[0],Corner3[1],Corner3[2]+10);
        gl.glVertex3f(Corner3[0],Corner3[1],Corner3[2]);
        
        gl.glEnd();
        
        //wand4
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1f, 1f, 1f);
        gl.glVertex3f(Corner3[0],Corner3[1],Corner3[2]);
        gl.glVertex3f(Corner3[0],Corner3[1],Corner3[2]+10);
        gl.glVertex3f(Corner0[0],Corner0[1],Corner0[2]+10);
        gl.glVertex3f(Corner0[0],Corner0[1],Corner0[2]);
        
        gl.glEnd();
        
        //decke
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1f, 0f, 0f);
        gl.glVertex3f(Corner0[0],Corner0[1],Corner0[2]+10);
        gl.glVertex3f(Corner1[0],Corner1[1],Corner1[2]+10);
        gl.glVertex3f(Corner2[0],Corner2[1],Corner2[2]+10);
        gl.glVertex3f(Corner3[0],Corner3[1],Corner3[2]+10);
        
        gl.glEnd();

    }

   
    public void mouseClicked()
    {
//       if(isClickable)
//            GV.get().getUI().mouseClicked(this);
    }

/*
    public void addMission(Mission mission)
    {
        missionlist.add(mission);
    }

    @Override
    public void missionCompleted(Mission mission)
    {
        if(mission.getType().compareTo("DdiscoverFarm") == 0)
            handleDiscoverFarm(mission);
        else
        if(mission.getType().compareTo("PickUp") == 0)
            handleDeliverFoodMission((Mission_DeliverFood)mission);
        else
        if(mission.getType().compareTo("DdiscoverMill") == 0)
            handleDiscoverMill(mission);
    }

    void handleDiscoverFarm(Mission mission)
    {
        Mission_DdiscoverFarm m = (Mission_DdiscoverFarm)mission;
        farm = m.getFarm();
        if(farm == null)
        {
            if(discoverTileY - distance == tile.getYNr() || discoverTileY + distance == tile.getYNr())
                discoverTileX++;
            else
                discoverTileX = tile.getXNr() + distance;
            if(discoverTileX > tile.getXNr() + distance)
            {
                discoverTileY++;
                discoverTileX = tile.getXNr() - distance;
            }
            if(discoverTileY > tile.getYNr() + distance)
            {
                distance++;
                discoverTileX = tile.getXNr() - distance;
                discoverTileY = tile.getYNr() - distance;
            }
        } else
        {
            System.out.println("House has Farm");
        }
    }
    
    void handleDiscoverMill(Mission mission){
        Mission_DdiscoverMill m = (Mission_DdiscoverMill)mission;
        mill = m.getMill();
        if(mill == null)
        {
            if(discoverTileY - distance == tile.getYNr() || discoverTileY + distance == tile.getYNr())
                discoverTileX++;
            else
                discoverTileX = tile.getXNr() + distance;
            if(discoverTileX > tile.getXNr() + distance)
            {
                discoverTileY++;
                discoverTileX = tile.getXNr() - distance;
            }
            if(discoverTileY > tile.getYNr() + distance)
            {
                distance++;
                discoverTileX = tile.getXNr() - distance;
                discoverTileY = tile.getYNr() - distance;
            }
        } else
        {
            System.out.println("House has Mill");
        }
    }

    private void handleDeliverFoodMission(Mission_DeliverFood mission)
    {
        for(int i = 0; i < mission.getPickUp(); i++)
            incStock();

    }

    public void setLittleMan(int ID, LittleMan littleMan)
    {
        littleMen[ID] = littleMan;
    }

    public synchronized Mission[] getMissionList()
    {
        Mission missionList[] = new Mission[missionlist.size()];
        for(int i = 0; i < missionList.length; i++)
            missionList[i] = (Mission)missionlist.get(i);

        return missionList;
    }

    public void reportHome()
    {
        incInhabits();
    }

    public void incInhabits()
    {
        manInHouse++;
        entityContainer.editAttribute("Man in house", String.valueOf(manInHouse));
    }

    public void decInhabits()
    {
        manInHouse--;
        entityContainer.editAttribute("Man in house", String.valueOf(manInHouse));
    }

    public void incStock()
    {
        stock++;
        entityContainer.editAttribute("Food", String.valueOf(stock));
    }

    public void decStock()
    {
        stock--;
        entityContainer.editAttribute("Food", String.valueOf(stock));
        expectedStock--;
    }

    @Override
    public boolean isColliding(Point p)
    {
        for(int i = 0; i < collisionBox.length; i++)
            if(collisionBox[i].contains(p))
                return true;

        return false;
    }

    @Override
    public boolean isColliding(Rectangle r)
    {
        for(int i = 0; i < collisionBox.length; i++)
            if(collisionBox[i].contains(r))
                return true;

        return false;
    }

    @Override
    public Rectangle[] getBoundBox()
    {
        return collisionBox;
    }

    @Override
    public Point getClosestCorner(Point location, double direction[])
    {
        if(collisionBoxCorners[2].getY() - 1.0D == location.getY())
        {
            System.out.println("wenn er an der unteren wand steht");
            if(direction[0] > 0.0D)
                return collisionBoxCorners[2];
            else
                return collisionBoxCorners[3];
        }
        if(collisionBoxCorners[0].getY() == location.getY())
        {
            System.out.println("wenn er an der oberen wand steht");
            if(direction[0] > 0.0D)
                return collisionBoxCorners[1];
            else
                return collisionBoxCorners[0];
        }
        if(collisionBoxCorners[0].getX() == location.getX())
        {
            System.out.println("wenn er an der linken wand steht");
            if(direction[1] < 0.0D)
                return collisionBoxCorners[0];
            else
                return collisionBoxCorners[3];
        }
        if(collisionBoxCorners[1].getX() - 1.0D == location.getX())
        {
            System.out.println("wenn er an der rechten wand steht");
            if(direction[1] < 0.0D)
                return collisionBoxCorners[1];
            else
                return collisionBoxCorners[2];
        } else
        {
            return new Point(0, 0);
        }
    }

    @Override
    public void addAllowance(Entity entity)
    {
    }

    @Override
    public void removeAllowance(Entity entity)
    {
    }
*/

}
