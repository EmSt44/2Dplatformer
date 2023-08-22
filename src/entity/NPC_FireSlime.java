package entity;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import entity.Player;

public class NPC_FireSlime extends Entity{

    public double accumulatedFallSpeed = 1.0;
    public double upSpeed = 0.0;
    public boolean gravity = true;
    public double gravityModifier = 1.0; //ändra denna för att ändra hur snabbt fallet accelererar
    public int collisionFix = 0;

    public NPC_FireSlime(GamePanel gp) {
        super(gp);
        getImage();
        direction = "right";
        speed = 15;
        //när collision finns lägg till hitbox här
        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void getImage() {
        try {
            left1 = ImageIO.read(new File("res/npc/fire_slime_l.png"));
            left2 = ImageIO.read(new File("res/npc/fire_slime1_l.png"));
            right1 = ImageIO.read(new File("res/npc/fire_slime_r.png"));
            right2 = ImageIO.read(new File("res/npc/fire_slime1_r.png"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 10){
            if (this.direction == "left") {
                this.worldX -= speed;
            } 
            else if (this.direction == "right") {
                this.worldX += speed;
            }

            actionLockCounter = 0;
        }
        
        this.collisionOn = false;
        gp.cChecker.checkTile(this);

        if(gp.player.worldX < this.worldX){
            if(this.collisionOn == true) {
                collisionFix++;
                this.direction = "right";
            }
            else if(collisionFix < 1){
                this.direction = "left";
            }
            else if(collisionFix == 1){
                collisionFix = 0;
            }
            
        }
        else if(gp.player.worldX > this.worldX) {
            if(this.collisionOn == true) {
                collisionFix++;
                this.direction = "left";
            }
            else if(collisionFix <= 0){
                this.direction = "right";
            }
            else if(collisionFix == 1){
                collisionFix = 0;
            }
        }

        //RÖRELSE NEDÅT (FALLA)
        //om man inte står på marken och inte hoppar uppåt så ska man falla
        if (upSpeed <= 0) { 
            collisionOn = false; 
            gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed);
            //check npc osv
            if (!collisionOn) {
                this.worldY += accumulatedFallSpeed;
                accumulatedFallSpeed += gravityModifier;
            }
            else {
                //återställ fall speed när man står på marken
                accumulatedFallSpeed = 1;
                collisionOn = false;
                gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed);
                while (!collisionOn) { //denna så att man landar på marken och inte ovanför
                    this.worldY += accumulatedFallSpeed;
                    gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed);
                }
            }
        }
    }
}
