package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import object.OBJ_heart;
import object.SuperObject;

public class UI extends JPanel {
    
    GamePanel gp;
    Font arial_40;
    Font arial_20;

    //Images för att visa liv i HUD
    BufferedImage heart_full, heart_half, heart_empty;

    public UI(GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_20 = new Font("Arial", Font.PLAIN, 20);

        //Skapa alla HUD objekt
        SuperObject heart = new OBJ_heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void draw(Graphics2D g2) {

        
        //Default font och färg
        //Kan ändras i andra draw funktioner men blir alltid reset till detta när draw kallas
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //MenuState
        if(gp.gameState == gp.menuState) {
            drawMenu(g2);
        }
        //PlayState
        else if(gp.gameState == gp.playState) {
            drawPlayerLife(g2);
        }
        //PauseState
        else if(gp.gameState == gp.pauseState) {
            drawPlayerLife(g2);
            drawPause(g2);
        }
    }

    //Ritar upp liv på HUD
    public void drawPlayerLife(Graphics2D g2) {

        //koordinater i pixlar där första hjärtat ritas upp på skärmen
        int x = 16;
        int y = 16;

        int hearts = gp.player.maxLife / 2 + gp.player.maxLife % 2; //mängd hjärtan, varje hjärta representerar 2 liv
        int remainder = gp.player.life; //mängd liv som spelaren har kvar
        int spacing = 4; //distans i pixlar mellan varje hjärta

        int i = 0; //accumulator för loopen

        while(i < hearts) {
            if(remainder >= 2) {
                g2.drawImage(heart_full, x, y, gp.tileSize, gp.tileSize, null);
                remainder -= 2;
                i++;
                x += gp.tileSize + spacing;
            }
            else if(remainder == 1) {
                g2.drawImage(heart_half, x, y, gp.tileSize, gp.tileSize, null);
                remainder -= 1;
                i++;
                x += gp.tileSize + spacing;
            }
            else if(remainder == 0) {
                g2.drawImage(heart_empty, x, y, gp.tileSize, gp.tileSize, null);
                //vid 0 ändra inte remainder
                i++;
                x += gp.tileSize + spacing;
            }

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