package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class UI extends JPanel {
    
    GamePanel gp;
    Font arial;

    public UI(GamePanel gp) {

        this.gp = gp;

        arial = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {

        

        g2.setFont(arial);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            //gör inget spelet är inte pausat, uppdatera detta med annan UI senare.
        }
        else if(gp.gameState == gp.pauseState) {
            System.out.println("pause is drawn");
            drawPause(g2);
        }
    }

    public void drawPause(Graphics2D g2) {
        g2.drawString("Paused", 0, 0);
    }
}