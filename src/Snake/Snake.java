
package Snake;

import Tools.GV;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Snake implements Runnable, KeyListener{
    SnakeTile tail;
    SnakeTile head;
    
    boolean up = true;
    boolean down;
    boolean left;
    boolean right;
    
    public Snake(){
        GV.get().getGameWindow().getFrame().addKeyListener(this);
        tail = new SnakeTile(3,1);
        head = new SnakeTile(4,1);
        tail.add(head);
        SnakeTile temp = new SnakeTile(5,1);
        head.add(temp);
        head = temp;
        temp = new SnakeTile(6,1);
        head.add(temp);
        head = temp;
        Thread t = new Thread(this);
        t.start();
        
    }
    
    public void move(){
        tail.remove();
        tail = tail.follower;
        if(up){
            head.add(new SnakeTile(head.getXPos(),head.getYPos()+1));
        }else if(down){
            head.add(new SnakeTile(head.getXPos(),head.getYPos()-1));
        }else if(left){
            head.add(new SnakeTile(head.getXPos()-1,head.getYPos()));
        }else if(right){
            head.add(new SnakeTile(head.getXPos()+1,head.getYPos()));
        }
        head = head.follower;
    }

    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    this.wait(200);
                } catch (Exception e) {
                }
            }
            move();
        }
    }

    
        public void keyTyped(KeyEvent e) {
            setCameraFlags(true,e);
        }

        public void keyPressed(KeyEvent e) {
            keyTyped(e);
        }

        public void keyReleased(KeyEvent e) {
            //setCameraFlags(false,e);
        }

        private void setCameraFlags(boolean flag, KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_UP){
                //camera.moveUp = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                //camera.moveDown = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                //camera.moveLeft = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                //camera.moveRight = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_E){
                //camera.rotRight = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_Q){
                //camera.rotLeft = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_W){
                setAllFlagsFalse();
                up = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_A){
                setAllFlagsFalse();
                left = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_S){
                setAllFlagsFalse();
                down = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_D){
               setAllFlagsFalse();
               right = flag;
            }
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                //camera.resetFocus();
            }
        }
        
        private void setAllFlagsFalse(){
            up = false;
            down = false;
            left = false;
           right = false;    
        }
    
}
