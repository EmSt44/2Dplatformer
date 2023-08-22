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

    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;

    public BufferedImage left, right;

    //Om entity ska påverkas av gravitation. Ifall true, så bör endast "right" och "left" används som direction.
    //Ifall false, så kan entityn exempelvis flyga nedåt eller uppåt och då kan direction "up" och "down" vara relevant.
    public boolean gravity = false;

    //Endast relevanta då gravity = true, bör alltid initialiseras 1 respektive 0
    public double accumulatedFallSpeed = 1.0;
    public double upSpeed = 0.0;

    //Entity status (liv)
    public int maxLife;
    public int life;

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

        //Kollisionsdetektering
        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntity(this, gp.npc);
        // gp.cChecker.checkPlayer(this);
    }

    public void setAction() {} //denna är endast här för att bli overridad av dess subklasser
}