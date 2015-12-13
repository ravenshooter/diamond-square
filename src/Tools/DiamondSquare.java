/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.util.ArrayList;

/**
 *
 * @author Paul
 */
public class DiamondSquare {
    
    private Point [] [] points;
    private static final float INITIAL_RANGE = 700;
    private static final double DIVISOR = 1.9;
    
    private static final boolean SMOOTH_ON = true;
    private static final int NORMAL_SMOOTH = 1;
    private static final int VERY_SMOOTH = 2;
    
    float range = INITIAL_RANGE;
    
    public double maxHeight;
    public double minHeight;
    
    public double[][] diamondSquare(double[][] m, double seed){
        //check validty
        int size = m.length-1;
        if(m.length != m[0].length){
            return null;
        }
        points = new Point[m.length][m.length];
        for(int i = 0; i < m.length;i++){
            for(int j = 0; j < m.length; j++){
                points[i][j] = new Point(i, j);
            }
        }
        
        //set corners
        points[0][0].setHeight(Math.random()*seed);
        points[size][0].setHeight(Math.random()*seed);
        points[0][size].setHeight(Math.random()*seed);
        points[size][size].setHeight(Math.random()*seed);
        

        int step = m.length - 1;
        while(step > 1){
            for(int x = 0; x < m.length - 1;x = x+step) {
                for(int y = 0; y < m.length - 1;y=y+step){
                    int sx = x + (step>>1);
                    int sy = y + (step>>1);
                    
                    points[sx][sy].calcHeight(points[x][y],
                            points[x+step][y],
                            points[x][y+step],
                            points[x+step][y+step]);
                }
            }
            for(int x = 0; x < m.length - 1; x += step){
                for(int y = 0; y < m.length - 1; y += step){
                    int halfstep = step>>1;
                    int x1 = x + halfstep;
                    int y1 = y;
                    int x2 = x;
                    int y2 = y + halfstep;
                    
                    ArrayList<Point> list1 = new ArrayList<Point>();
                    if(x1 - halfstep > 0){
                        list1.add(points[x1-halfstep][y1]);
                    }
                    if(y1 - halfstep > 0){
                        list1.add(points[x1][y1-halfstep]);
                    }
                    if(x1 + halfstep < m.length){
                        list1.add(points[x1 + halfstep][y1]);
                    }
                    if(y1 + halfstep < m.length){
                        list1.add(points[x1][y1 + halfstep]);
                    }
                    
                    ArrayList<Point> list2 = new ArrayList<Point>();
                    if(x2 - halfstep > 0){
                        list2.add(points[x2-halfstep][y2]);
                    }
                    if(y2 - halfstep > 0){
                        list2.add(points[x2][y2-halfstep]);
                    }
                    if(x2 + halfstep < m.length){
                        list2.add(points[x2 + halfstep][y2]);
                    }
                    if(y2 + halfstep < m.length){
                        list2.add(points[x2][y2 + halfstep]);
                    }
                    points[x1][y1].calcHeight(list1);
                    points[x2][y2].calcHeight(list2);
                }
            }
            range /= DIVISOR;
            step >>= 1;
        }
        if(SMOOTH_ON){
            smooth(VERY_SMOOTH);
        }
        
        for(int i = 0; i < m.length;i++){
            for(int j = 0; j < m.length; j++){
                m[i][j] = points[i][j].height;
            }
        }
        maxHeight = -99999;
        minHeight =  99999;
        for(int i = 0; i < m.length; i++){
            for(int j = 0;j < m[0].length;j++){
                if(m[i][j] > maxHeight)
                    maxHeight = m[i][j];
                if(m[i][j] < minHeight)
                    minHeight = m[i][j];
            }
        }
        return m;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public double getMinHeight() {
        return minHeight;
    }
    
    
    
   
    private void smooth(int smoothLevel){
        System.out.println("Smoothing..");
        for(int i = smoothLevel; i < points.length-smoothLevel; i++){
            for(int j = smoothLevel; j < (points.length-smoothLevel); j++){
                double v = getNeighboursAVG(i, j, smoothLevel);
                double hb = points[i][j].height;
                points[i][j].height = (v);
            }
        }
    }
    
    private double getNeighboursAVG(int x, int y, int level){
        
        Point[] n;
        switch(level){
            case NORMAL_SMOOTH: 
                return (points[x-1][y].height + points[x][y-1].height + points[x+1][y].height + points[x][y+1].height)/4;
            case VERY_SMOOTH: n = new Point[8];
                return (points[x-1][y].height + points[x][y-1].height + points[x+1][y].height + points[x][y+1].height + 
                        points[x-1][y-1].height + points[x-1][y-1].height + points[x+1][y+1].height + points[x+1][y+1].height)/8;
        }
        System.out.println("err");
        return 0;
    }
    
    class Point{
        int x,y;
        double height;
        boolean square;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.square = square;
        }
        
        public void calcHeight(Point ...  p){
            double value = 0;
            for(int i = 0; i < p.length; i++){
                value += p[i].height;
            }
            value /= p.length;
            value +=  (Math.random()*range - range/2);
            height = value;
        }
        
        public void calcHeight(ArrayList<Point> p){
            double value = 0;
            for(int i = 0; i < p.size(); i++){
                value += p.get(i).height;
            }
            value /= p.size();
            value +=  (Math.random()*range - range/2);
            height = value;
            value +=  (Math.random()*range - range/2);
            height = value;
        }
        
        public void setHeight(double h){
            height = h;
        }
    }
}
