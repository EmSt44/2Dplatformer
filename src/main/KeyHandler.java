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

        //Knapptryck i pauseState
        if(gp.gameState == gp.pauseState) {

            //Unpause
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }

            //Quit to menu
            if(code == KeyEvent.VK_Q) {
                gp.gameState = gp.menuState;
                gp.ui.menuScreen = gp.ui.mainScreen;
                gp.ui.commandNum = 0;
            }
        }
        //Knapptryck i playState
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
        //Knapptryck i menuState
        else if(gp.gameState == gp.menuState) {

           //MainScreen
           if(gp.ui.menuScreen == gp.ui.mainScreen) {
             if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.ui.menuScreen = gp.ui.mapSelectScreen;
                }
                else if(gp.ui.commandNum == 1) {
                    System.exit(0);
                }
            }

            if(code == KeyEvent.VK_W) {
                if(gp.ui.commandNum == 0) {
                    //do nothing
                }
                else {
                    gp.ui.commandNum -= 1;
                }
            }

            if(code == KeyEvent.VK_S) {
                if(gp.ui.commandNum == 1) {
                    //do nothing
                }
                else {
                    gp.ui.commandNum += 1;
                }
            }
           }
           //MapSelectScreen
           else if(gp.ui.menuScreen == gp.ui.mapSelectScreen) {

            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == gp.tileM.mapsAmount) {
                    gp.ui.menuScreen = gp.ui.mainScreen;
                    gp.ui.commandNum = 0;
                }
                else { //starta valda mapen
                    gp.tileM.loadMap(gp.ui.commandNum);
                    gp.aSetter.resetAssetSetter(gp.ui.commandNum);
                    gp.player.resetPlayer();
                    gp.gameState = gp.playState;
                }
            }

            if(code == KeyEvent.VK_W) {
                if(gp.ui.commandNum == 0) {
                    //do nothing
                }
                else {
                    gp.ui.commandNum -= 1;
                }
            }

            if(code == KeyEvent.VK_S) {
                if(gp.ui.commandNum == gp.tileM.mapsAmount) {
                    //do nothing
                }
                else {
                    gp.ui.commandNum += 1;
                }
            }
           }

        }
        //Knapptryck i gameOverState
        else if(gp.gameState == gp.gameOverState) {

            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.menuState;
                gp.ui.menuScreen = gp.ui.mainScreen;
                gp.ui.commandNum = 0;
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