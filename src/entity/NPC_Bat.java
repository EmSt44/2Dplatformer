package entity;

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
