package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class UI extends JPanel {
    
    GamePanel gp;
    Font arial_40;
    Font arial_20;

    public UI(GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_20 = new Font("Arial", Font.PLAIN, 20);
    }

    public void draw(Graphics2D g2) {

        

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //MenuState
        if(gp.gameState == gp.menuState) {
            drawMenu(g2);
        }
        //PlayState
        else if(gp.gameState == gp.playState) {
            //gör inget spelet är inte pausat, uppdatera detta med annan UI senare.
        }
        //PauseState
        else if(gp.gameState == gp.pauseState) {
            drawPause(g2);
        }
    }

    //Rita upp pausmenyn
    public void drawPause(Graphics2D g2) {

        String pauseText = "Paused";

        int pauseTextLength = (int)g2.getFontMetrics().getStringBounds(pauseText, g2).getWidth();
        int xPlacement = gp.screenWidth/2-pauseTextLength/2;

        int yPlacement = gp.screenHeight/2;
        
        g2.drawString(pauseText, xPlacement, yPlacement);
    }

    //Rita upp huvudmenyn (TEMPORÄR)
    public void drawMenu(Graphics2D g2) {

        String titleText = "2Dplatformer";

        int titleTextLength = getTextLength(titleText, g2);
        int xPlacement = gp.screenWidth/2-titleTextLength/2;

        int yPlacement = gp.screenHeight/2;
        
        g2.drawString(titleText, xPlacement, yPlacement);

        g2.setFont(arial_20);

        String titleSubText = "Press space to play";

        int titleSubTextLength = getTextLength(titleSubText, g2);
        int subXPlacement = gp.screenWidth/2-titleSubTextLength/2;

        int subYPlacement =  gp.screenHeight/2+40;

        g2.drawString(titleSubText, subXPlacement, subYPlacement);

    }

    //Räknar ut hur lång en text kommer bli på skärmen (i pixlar)
    private int getTextLength(String text, Graphics2D g2) {
        return (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    }
}