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

    //Fonts
    Font arial_40;
    Font arial_20;

    //Images för att visa liv i HUD
    BufferedImage heart_full, heart_half, heart_empty;

    //Hjälpmedel för menyn
    int commandNum;

    public int menuScreen;
    public final int mainScreen = 0;
    public final int mapSelectScreen = 1;


    public UI(GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_20 = new Font("Arial", Font.PLAIN, 20);

        //Skapa alla HUD objekt
        SuperObject heart = new OBJ_heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;

        //Sätt menyns commandNum till 0
        commandNum = 0;
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
    //Rita upp menyer
    public void drawMenu(Graphics2D g2) {

        if(menuScreen == mainScreen) {
            drawMenuMain(g2);
        }
        else if(menuScreen == mapSelectScreen) {
            drawMapSelectMenu(g2);
        }
    }

    //rita upp map select menyn
    private void drawMapSelectMenu(Graphics2D g2) {

        //TITLE
        String titleText = "Map Selection";

        int titleTextLength = getTextLength(titleText, g2);
        int xPlacement = gp.screenWidth/2-titleTextLength/2;

        int yPlacement = 70;
        
        g2.drawString(titleText, xPlacement, yPlacement);

        //MAPS
        g2.setFont(arial_20);
        int i = 0;
        int y = gp.screenHeight/2-80;
        int x;

        while(i < gp.tileM.mapsAmount) {
            String mapNameFull = gp.tileM.maps[i];
            String mapName = mapNameFull.substring(9);

            int mapNameLength = getTextLength(mapName, g2);
            x = gp.screenWidth/2-mapNameLength/2;

            g2.drawString(mapName, x, y);
            y += 40;
            i++;
        }

        //BACK
        String backText = "back";

        int backTextLength = getTextLength(backText, g2);
        int backXPlacement = gp.screenWidth/2-backTextLength/2;
        
        g2.drawString(backText, backXPlacement, y);

        //CURSOR
        if(commandNum < gp.tileM.mapsAmount) {
            g2.drawString(">", gp.screenWidth/2-getTextLength(gp.tileM.maps[commandNum].substring(9), g2)/2 - 20, gp.screenHeight/2 - 80 + commandNum*40);
        }
        else if(commandNum == gp.tileM.mapsAmount) {
            g2.drawString(">", backXPlacement - 20, gp.screenHeight/2 - 80 + commandNum*40);
        }

    }

    //Rita upp huvudmenyn 
    private void drawMenuMain(Graphics2D g2) {

        //TITLE
        String titleText = "2Dplatformer";

        int titleTextLength = getTextLength(titleText, g2);
        int xPlacement = gp.screenWidth/2-titleTextLength/2;

        int yPlacement = 70;
        
        g2.drawString(titleText, xPlacement, yPlacement);

         //MENU OPTIONS
        g2.setFont(arial_20);

        //Play
        String playText = "Play";

        int playTextLength = getTextLength(playText, g2);
        int playXPlacement = gp.screenWidth/2-playTextLength/2;

        int playYPlacement =  gp.screenHeight/2-40;

        g2.drawString(playText, playXPlacement, playYPlacement);

        //Quit
        String quitText = "Quit";

        int quitTextLength = getTextLength(quitText, g2);
        int quitXPlacement = gp.screenWidth/2-quitTextLength/2;

        int quitYPlacement =  gp.screenHeight/2;

        g2.drawString(quitText, quitXPlacement, quitYPlacement);

        //CURSOR
        if(commandNum == 0) {
            g2.drawString(">", playXPlacement- 20, playYPlacement);
        }
        else if(commandNum == 1) {
            g2.drawString(">", quitXPlacement - 20, quitYPlacement);
        }

    }

    //Räknar ut hur lång en text kommer bli på skärmen (i pixlar)
    private int getTextLength(String text, Graphics2D g2) {
        return (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    }
}