package entity;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPC_Bat extends Entity{
    
    int behaviorCycle = 0;

    public NPC_Bat(GamePanel gp) {
        super(gp);
        getImage();
        direction = "right";
        speed = 3;
        //när collision finns lägg till hitbox här
        solidArea = new Rectangle();
        solidArea.x = 3;
        solidArea.y = 12;
        solidArea.width = 42;
        solidArea.height = 24;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void getImage() {
        try {
            left = ImageIO.read(new File("res/npc/bat_l.png"));
            right = ImageIO.read(new File("res/npc/bat_r.png"));

        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        if (this.direction == "left") {
            this.worldX -= speed;
        } else if (this.direction == "right") {
            this.worldX += speed;
        }
        
        behaviorCycle++;
        if (behaviorCycle >= 120) { //byta håll efter 120 frames
            if (this.direction == "right") {
                this.direction = "left";
            } else if (this.direction == "left") {
                this.direction = "right";
            }
            behaviorCycle = 0;
        }
    }
}
