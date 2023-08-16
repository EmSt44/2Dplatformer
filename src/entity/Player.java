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

        worldX = gp.tileSize * 49;
        worldY = gp.tileSize * 48;
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
            boolean check = false;
            worldY += ((-3) * jumpAnimation) + (1 * animationTime);
            animationTime++;
            if(jumpAnimation <= 7 && !check) {
                jumpAnimation++;
            }
            else if(check) {
                jumpAnimation--;
            }
            else if(jumpAnimation == 7) {
                check = true;
            }
            
            if(worldY > gp.tileSize * 48) {
                keyH.upPressed = false;
                worldY = gp.tileSize * 48;
                animationTime = 0;
                jumpAnimation = 0;
            }

            //y -= speed;
            /*if(isJumping){
                verticalVelocity = initialVelocity*sin(angle) - g*animationTime;
                animationTime += TIME_STEP;
            }*/
        }
        if(keyH.leftPressed == true) {
            worldX -= speed;
            direction = "left";
        }
        if(keyH.rightPressed == true) {
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

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}