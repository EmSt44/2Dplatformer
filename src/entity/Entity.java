package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import visual.GenericDeathSmoke;

public class Entity{

    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public String direction = "right"; //höger som default direction
    public Rectangle solidArea;

    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;

    public BufferedImage left1, right1, left2, right2, up1, up2, down1, down2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int actionLockCounter = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public boolean alive;

    //Om entity ska påverkas av gravitation. Ifall true, så bör endast "right" och "left" används som direction.
    //Ifall false, så kan entityn exempelvis flyga nedåt eller uppåt och då kan direction "up" och "down" vara relevant.
    public boolean gravity = false;

    //Endast relevanta då gravity = true, bör alltid initialiseras 1 respektive 0
    public double accumulatedFallSpeed = 1.0;
    public double upSpeed = 0.0;

    //Entity status (liv)
    public int maxLife;
    public int life;
    public Projectile projectile;
    public int shooting;

    //Hur mycket skada fienden gör vid kontakt
    public int damage;
    //Om entityn kan stampas på för att skada dem
    public boolean stompable = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        if (direction == "left") {
            if(spriteNum == 1){
                image = left1;
            }
            if(spriteNum == 2) {
                image = left2;
            }
        } else if (direction == "right") {
            if(spriteNum == 1){
                image = right1;
            }
            if(spriteNum == 2) {
                image = right2;
            }
        } else if (direction == "up") {
            if(spriteNum == 1){
                image = up1;
            }
            if(spriteNum == 2) {
                image = up2;
            }
        } else if (direction == "down") {
            if(spriteNum == 1){
                image = down1;
            }
            if(spriteNum == 2) {
                image = down2;
            }
        } else { //höger som standard, om ingen direction
            image = right1;
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

        spriteCounter++;
        if(spriteCounter > 10) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void setAction() {} //denna är endast här för att bli overridad av dess subklasser


    //Bör overridas om man vill ha ex. odödlig fiende eller skadeanimation.
    public void takeDamage(int damageAmount) {
        this.life -= damageAmount;
        if (life <= 0) {
            this.die();
        }
    }

    //Bör overridas om man vill ha ex. odödlig fiende eller annan dödsanimation osv. 
    public void die() {
        for (int i = 0; i < gp.npc.length; i++) {
            if (gp.npc[i] != null) {
                if (gp.npc[i] == this) {
                    new GenericDeathSmoke(gp, worldX, worldY);
                    gp.npc[i] = null;
                }
            } 
        }
    }
}