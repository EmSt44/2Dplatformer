package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }
    public void setDefaultValues() {

        x = 100;
        y = 300;    
        speed = 4;
    }
    public void update() {
        if(keyH.upPressed == true) {
            boolean check = false;
            y += ((-3) * jumpAnimation) + (1 * animationTime);
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
            
            if(y > 500) {
                //keyH.upPressed = false;
                y = 100;
                //animationTime = 0;
                //jumpAnimation = 0;
            }

            //y -= speed;
            /*if(isJumping){
                verticalVelocity = initialVelocity*sin(angle) - g*animationTime;
                animationTime += TIME_STEP;
            }*/
        }
        if(keyH.downPressed == true) {
            y += speed;
        }
        if(keyH.leftPressed == true) {
            x -= speed;
        }
        if(keyH.rightPressed == true) {
            x += speed;
        }
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);

        g2.fillRect((int)x, (int)y, gp.tileSize, gp.tileSize);
    }
}