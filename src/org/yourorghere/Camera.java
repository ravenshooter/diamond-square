
package org.yourorghere;

public class Camera {
    int x,y,height;
    int focusX, focusY;
    double rotDeg = 0;
    boolean moveUp, moveDown, moveLeft, moveRight, rotRight, rotLeft, focRight, focLeft,focUp,focDown;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }
    
    public int getFocusX(){
        return focusX;
    }
    
    public int getFocusY(){
        return focusY;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }
    
    public synchronized void setFocusX(int fx) {
        this.focusX = fx;
    }
    
    public synchronized void setFocusY(int fy) {
        this.focusY = fy;
    }

    public synchronized void setHeight(int height) {
        this.height = height;
    }

    public Camera(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.focusX = x+200;
        this.focusY = y;

    }
    
    public void replaceCamera(){
        if(moveUp){
            setX(getX()+5);
            setFocusX(getFocusX()+5);
        }
        if(moveDown){
            setX(getX()-5);
            setFocusX(getFocusX()-5);
        }
        if(moveLeft){
            setY(getY()+5);
            setFocusY(getFocusY()+5);
        }
        if(moveRight){
            setY(getY()-5);
            setFocusY(getFocusY()-5);
        }
        if(rotRight){
            throw new UnsupportedOperationException("Not supported yet");
        }
        if(rotLeft){
            throw new UnsupportedOperationException("Not supported yet");
        }
        if(focUp){
            setFocusX(getFocusX()+5); 
        }
        if(focDown){
            setFocusX(getFocusX()-5);
        }
        if(focLeft){
            setFocusY(getFocusY()+5);
        }
        if(focRight){
            setFocusY(getFocusY()-5);
        }
    }
    
    public void resetFocus(){
        this.focusX = x+200;
        this.focusY = y;
    }
    
}
