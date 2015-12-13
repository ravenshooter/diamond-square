/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

   
    
public class Vertex{
        
        public float x,y,z;
        public double length;

        public Vertex(float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
            length = calcDistance(this, new Vertex());
        }
        
        /*
         * Creates a new Null Vertex (x,y and z are all zero)
         */
        public Vertex(){
            x = 0;
            y = 0;
            z = 0;
        }
        
        public float[] getBuffer(){
            float[] ret = {x,y,z};
            return  ret;
        }
        

        
        /**
         * Returns the length of the Vertex
         * @return 
         */
        public double getLength(){
            return length;
        }
        
        /**
         * Returns a new Vertex, pointing in the same direction but with length 1.
         * @return new Vertex
         */
        public Vertex getUnitVertex(){
            return new Vertex(x/(float)length,y/(float)length,z/(float)length);
        }
        
        /**
         * Returns a new Vertex connecting from start to endPos
         * @param startPos
         * @param endPos
         * @return return the Vertex connecting startPos and endPos
         */
        public static Vertex calcDirVertex(Vertex startPos, Vertex endPos){
            return new Vertex(endPos.x-startPos.x,endPos.y-startPos.y,endPos.z-startPos.z);
        }
        
        /**
         * Adds a vertex to this one
         * @param v vertex which will be added
         */
        public void add(Vertex v){
            x = x+v.x;
            y = y+v.y;
            z = z+v.z;
        }
        
        /**
         * Substracts another Vertex from this one
         * @param v 
         */
        public void substract(Vertex v){
            x =- v.x;
            y =- v.y;
            z =- v.z;
        }
        
        /**
         * Returns a new Vector representing the sum of a and b
         * @param a
         * @param b
         * @return a NEW vertex
         */
        public static Vertex addVertices(Vertex a, Vertex b){
            return new Vertex(a.x+b.x,a.y+b.y,a.z+b.z);
        } 
        
        /**
         * Returns a new Vertex pointing from a to b
         * @param a Start of the new Vector
         * @param b End of the new Vector
         * @return a NEW Vector
         */
        public static Vertex subVertices(Vertex a, Vertex b){
            return new Vertex(b.x-a.x,b.y-a.y,b.z-a.z);
        }
        
        /**
         * Returns the difference between to Vertices a and b
         * @param a
         * @param b
         * @return 
         */
        public static double calcDistance(Vertex a, Vertex b){
            return Math.sqrt(Math.pow(a.x-b.x, 2)+Math.pow(a.y-b.y, 2)+Math.pow(a.z-b.z, 2));
        }
        
        /**
         * Returns the cartesian product of vector a and b
         * @param a
         * @param b
         * @return 
         */
        public static Vertex calcNormal(Vertex a, Vertex b){
            float newX = a.y*b.z-a.z*b.y;
            float newY = a.z*b.x-a.x*b.z;
            float newZ = a.x*b.y-a.y*b.x;
            return new Vertex(newX,newY,newZ);
        }
        
        /**
         * Returns a new Vector, pointing normal from a and b with length 1.
         * @param a
         * @param b
         * @return 
         */
        public static Vertex calcUnitNormal(Vertex a, Vertex b){
            return Vertex.calcNormal(a, b).getUnitVertex();
        }
        
        /**
         * Returns a new vertex pointing in the same direction as v but with length 1.
         * @param v
         * @return 
         */
        public static Vertex calcUnitVertex(Vertex v){
            return v.getUnitVertex();
        }
        
        public static double getScalar(Vertex a, Vertex b){
            return a.x*b.x+a.y*b.y+a.z*b.z;
        }
        
        /**
         * Moves the vertex to the new position
         * @param xdist
         * @param ydist
         * @param zdist 
         */
        public void move(float xdist, float ydist, float zdist){
            x = xdist;
            y = ydist;
            z = zdist;
        }
        
        public void scale(float scale){
            x = x*scale;
            y = y*scale;
            z = z*scale;
            this.length = calcDistance(this,new Vertex());
        }
        

        
    
}
