package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {

        /*Edit out later
        worldX = g
        x = 100;
        y = 100;
        speed = 4;p.tileSize * //starting tile for X
        worldY = gp.tileSize * //starting tile for Y
        */
        worldX = 100;
        worldY = 100;
        speed = 4;
    }

    public void getPlayerImage() {
        try {
            // left = ImageIO.read(getClass().getResourceAsStream("../../res/player/ninja_l.png"));
            // right = ImageIO.read(getClass().getResourceAsStream("../../res/player/ninja_r.png"));

            left = ImageIO.read(new File("res/player/ninja_l.png"));
            right = ImageIO.read(new File("res/player/ninja_r.png"));

        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed == true) {
            worldY -= speed;
        }
        else if(keyH.downPressed == true) {
            worldY += speed;
        }
        else if(keyH.leftPressed == true) {
            worldX -= speed;
            direction = "left";
        }
        else if(keyH.rightPressed == true) {
            worldX += speed;
            direction = "right";
        }
    }
    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);

        // g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        if (direction == "left") {
            image = left;
        } else if (direction == "right") {
            image = right;
        } else { //höger som standard, om ingen direction
            image = right;
        }

        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}