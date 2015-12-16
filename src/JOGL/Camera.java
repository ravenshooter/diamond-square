
package JOGL;

import TileMap.Entities.House;
import Tools.GV;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.jogamp.opengl.awt.GLCanvas;



public class Camera {
    double x,y,height;
    double focusX, focusY,focusZ;
    /**
     * Rotation of camera around Z Axis
     */
    double rotDeg = 0;
    /**
     * Rotation between camera and X-Y Plane
     */
    double rotZDeg = 0;
    boolean moveUp, moveDown, moveLeft, moveRight, rotRight, rotLeft, focRight, focLeft,focUp,focDown,zoomIn,zoomOut;

    cameraMover cameraMover;
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }
    
    public double getFocusX(){
        return focusX;
    }
    
    public double getFocusY(){
        return focusY;
    }

    public synchronized void setX(double x) {
        this.x = x;
    }

    public synchronized void setY(double y) {
        this.y = y;
    }
    
    public synchronized void setFocusX(double fx) {
        this.focusX = fx;
    }
    
    public synchronized void setFocusY(double fy) {
        this.focusY = fy;
    }

    public synchronized void setHeight(double height) {
        this.height = height;
    }

    public double getFocusZ() {
        return focusZ;
    }

    public void setFocusZ(double focusZ) {
        this.focusZ = focusZ;
    }

    public double getRotZDeg() {
        return rotZDeg;
    }

    /**
     * Sets the rotation of camera to X-Y Plane, in Rad not Degree!
     * Only values from -PI/2 to +Pi/2 accepted! 
     * @param rotZDeg in RAD!
     */
    public void setRotZDeg(double rotZDeg) {
        if(rotZDeg >= -Math.PI/2 && rotZDeg <= Math.PI/2){
            this.rotZDeg = rotZDeg;
        }
    }
    
    
    


    public Camera(int x, int y, int height, GLCanvas canvas) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.focusX = GV.get().getMarker().getXPos();
        this.focusY = GV.get().getMarker().getXPos();
        canvas.addKeyListener(new KeyHandler(this));
        GV.get().addCamera(this);
    }
    
    public void replaceCamera(){
        if(cameraMover == null){
        if(moveUp){
            setX(getX()+5);
            
        }
        if(moveDown){
            setX(getX()-5);
            
        }
        if(moveLeft){
            setY(getY()+5);
           
        }
        if(moveRight){
            setY(getY()-5);
            
        }
        if(rotRight){
            System.err.println("Not yet supported");
        }
        if(rotLeft){
            System.err.println("Not yet supported");
        }
        if(focUp){
            GV.get().getMarker().move(2);
            setFocusY(GV.get().getMarker().getYPos());
            setFocusX(GV.get().getMarker().getXPos());
        }
        if(focDown){
            GV.get().getMarker().move(6);
            setFocusY(GV.get().getMarker().getYPos());
            setFocusX(GV.get().getMarker().getXPos());
        }
        if(focLeft){
            GV.get().getMarker().move(4);
            setFocusX(GV.get().getMarker().getXPos());
            setFocusY(GV.get().getMarker().getYPos());
        }
        if(focRight){
            GV.get().getMarker().move(0);
            setFocusX(GV.get().getMarker().getXPos());
            setFocusY(GV.get().getMarker().getYPos());
        }
        if(zoomIn){
            setHeight(getHeight()-5);
            
        }
        if(zoomOut){
            setHeight(getHeight()+5);
            
        }
        focusZ = (int)GV.get().getMarker().getMarkedTile().getHeight();
        }else{
            cameraMover.newFlag(this);
        }

    }
    
    public void resetFocus(){
        setX(getFocusX());
        setY(getFocusY());
    }
    
    public void setCameraMover(cameraMover mover){
        this.cameraMover = mover;
        if(mover != null){
            mover.newFlag(this);
        }
    }
    
    public cameraMover getCameraMover(){
        return cameraMover;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public boolean isRotRight() {
        return rotRight;
    }

    public boolean isRotLeft() {
        return rotLeft;
    }

    public boolean isFocRight() {
        return focRight;
    }

    public boolean isFocLeft() {
        return focLeft;
    }

    public boolean isFocUp() {
        return focUp;
    }

    public boolean isFocDown() {
        return focDown;
    }

    public boolean isZoomIn() {
        return zoomIn;
    }

    public boolean isZoomOut() {
        return zoomOut;
    }

    public double getRotDeg() {
        return rotDeg;
    }

    public void setRotDeg(double rotDeg) {
        this.rotDeg = rotDeg;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public class KeyHandler implements KeyListener{




        Camera camera;
        KeyHandler(Camera camera){
            this.camera = camera;
        }

        public void keyTyped(KeyEvent e) {
            setCameraFlags(true,e);
            if(e.getKeyCode() == KeyEvent.VK_H){
                System.out.println("Passable:" + GV.get().getMarker().getMarkedTile().isPassAble());
            }
            if(e.getKeyCode() == KeyEvent.VK_B){
                GV.get().getMarker().getMarkedTile().addEntity(new House(GV.get().getMarker().getMarkedTile()));
            }
        }

        public void keyPressed(KeyEvent e) {
            keyTyped(e);
        }

        public void keyReleased(KeyEvent e) {
            setCameraFlags(false,e);
        }

        private void setCameraFlags(boolean flag, KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_UP){
                camera.moveUp = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                camera.moveDown = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                camera.moveLeft = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                camera.moveRight = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_E){
                camera.rotRight = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_Q){
                camera.rotLeft = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_W){
                camera.focUp = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_A){
                camera.focLeft = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_S){
                camera.focDown = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_D){
                camera.focRight = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_PLUS){
                camera.zoomIn = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_MINUS){
                camera.zoomOut = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                camera.resetFocus();
            }
            if(GV.get().getEditor()!=null){
                GV.get().getEditor().updateCameraLocation((int)camera.x, (int)camera.y, camera.height);
                GV.get().getEditor().updatePointerLocation((int)camera.focusX, (int)camera.focusY, camera.focusZ);
            }

        }
    }


}
