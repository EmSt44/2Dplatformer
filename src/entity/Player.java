package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
    }
    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
    }
    public void update() {
        if(keyH.upPressed == true) {
            direction = "up";
            //y -= speed;
        }
        else if(keyH.downPressed == true) {
            direction = "down";
            //y += speed;
        }
        else if(keyH.leftPressed == true) {
            direction = "left";
            //x -= speed;
        }
        else if(keyH.rightPressed == true) {
            direction = "right";
        }
    

    

    //if collision is false player can move


    //change this to an entity
    this.collisionOn = false;
    gp.checker.checkTile(this);

        if (this.collisionOn == false){

            switch (direction){
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
                case "right":
                    x += speed;
                    break;
            }

        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);

        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}