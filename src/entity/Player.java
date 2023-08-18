package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultValues();

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;

        getPlayerImage();

    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 40;
        worldY = gp.tileSize * 40;
        speed = 5;
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
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }

        //     boolean check = false;
        //     worldY += ((-3) * jumpAnimation) + (1 * animationTime);
        //     animationTime++;
        //     if(jumpAnimation <= 7 && !check) {
        //         jumpAnimation++;
        //     }
        //     else if(check) {
        //         jumpAnimation--;
        //     }
        //     else if(jumpAnimation == 7) {
        //         check = true;
        //     }
            
        //     if(worldY > gp.tileSize * 48) {
        //         keyH.upPressed = false;
        //         worldY = gp.tileSize * 48;
        //         animationTime = 0;
        //         jumpAnimation = 0;
        //     }

        //     //y -= speed;
        //     /*if(isJumping){
        //         verticalVelocity = initialVelocity*sin(angle) - g*animationTime;
        //         animationTime += TIME_STEP;
        //     }*/
        // }
        // if(keyH.leftPressed == true) {
        //     worldX -= speed;
        //     direction = "left";
        // }
        // if(keyH.rightPressed == true) {
        //     worldX += speed;
        //     direction = "right";
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

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}