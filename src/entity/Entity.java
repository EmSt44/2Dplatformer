package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Entity{

    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public String direction = "right"; //höger som default direction
    public Rectangle solidArea;
    public boolean collisionOn = false;

    public BufferedImage left, right;

    public int animationTime = 0;
    public int jumpAnimation = 1;

    public Entity(GamePanel gp) {
        this.gp = gp;
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

        // g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
           }
    }

    public void update() {
        
        setAction();
        //collison detection bör också hända här.
    }

    public void setAction() {} //denna är endast här för att bli overridad av dess subklasser
}