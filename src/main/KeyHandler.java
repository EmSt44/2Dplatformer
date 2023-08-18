package main;

//import entity.Entity;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp; //för att interagera med gamepanel
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        //Tillåt endast unpause knapptryck när spelet är pausat
        if(gp.gameState == gp.pauseState) {

            //Unpause
            if(code ==KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        //Tillåt alla knapptryck då spelet kör
        else if(gp.gameState == gp.playState) {

            //Pause
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }

            if(code == KeyEvent.VK_W) {
                upPressed = true;
            }

            if(code == KeyEvent.VK_S) {
                downPressed = true;
            }

            if(code == KeyEvent.VK_A) {
                leftPressed = true;
            }

            if(code == KeyEvent.VK_D) {
                rightPressed = true;
            }

        }
        else if(gp.gameState == gp.menuState) {

            if(code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.playState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}