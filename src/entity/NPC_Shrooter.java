package entity;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import object.OBJ_Arrow;

public class NPC_Shrooter extends Entity{

    public NPC_Shrooter(GamePanel gp) {
        super(gp);
        this.gp = gp;
        getImage();
        direction = "right";
        speed = 0;
        //när collision finns lägg till hitbox här
        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 2;
        solidArea.width = 40;
        solidArea.height = 46;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        shooting = 0;

        maxLife = 1;
        life = 1;
        damage = 1;
        stompable = true;

        //NPC projectile
        projectile = new OBJ_Arrow(gp);
    }

    public void getImage() {
        try {
            left1 = ImageIO.read(new File("res/npc/shrooter_l.png"));
            left2 = ImageIO.read(new File("res/npc/shrooter_l.png"));
            right1 = ImageIO.read(new File("res/npc/shrooter_r.png"));
            right2 = ImageIO.read(new File("res/npc/shrooter_r.png"));

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

        if(gp.player.worldX < this.worldX) {
            direction = "left";
        }
        else if(gp.player.worldX > this.worldX){
            direction = "right";
        }

        shooting++;
        if(shooting > 70 && projectile.alive == false) {

            //Set default coordinates, direction and user
            projectile.set(worldX, worldY, direction, true, this, damage);

            //Add to the arraylist
            gp.projectileList.add(projectile);

            shooting = 0;
        }
    }
}