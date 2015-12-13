/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.AStar;

/**
 *
 * @author Steve
 */
public interface aStarAble {
    public int getX();
    public int getY();
    public double getHeight();
    public String getType();
    public boolean isPassAble();
    public aStarAble[] getNeighbours();
}
