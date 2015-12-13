/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap.Entities.Interfaces;

import TileMap.Entities.Entity;
import java.awt.Point;
import java.awt.Rectangle;

interface collideable
{

    public abstract boolean isColliding(Point point);

    public abstract boolean isColliding(Rectangle rectangle);

    public abstract Rectangle[] getBoundBox();

    public abstract void addAllowance(Entity entity);

    public abstract void removeAllowance(Entity entity);

    public abstract Point getClosestCorner(Point point, double ad[]);
}
