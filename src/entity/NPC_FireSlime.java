package entity;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPC_FireSlime extends Entity{

    public double accumulatedFallSpeed = 1.0;
    public double upSpeed = 0.0;
    public boolean gravity = true;
    public double gravityModifier = 0.1; //ändra denna för att ändra hur snabbt fallet accelererar
    public int collisionFix = 0;
    public boolean active = false;
    public boolean onGround = false;

    public NPC_FireSlime(GamePanel gp) {
        super(gp);
        getImage();
        direction = "up";
        speed = 15;

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        damage = 1;
        stompable = false;
    }

    public void getImage() {
        try {
            left1 = ImageIO.read(new File("res/npc/fire_slime_l.png"));
            left2 = ImageIO.read(new File("res/npc/fire_slime1_l.png"));
            right1 = ImageIO.read(new File("res/npc/fire_slime_r.png"));
            right2 = ImageIO.read(new File("res/npc/fire_slime1_r.png"));
            up1 = ImageIO.read(new File("res/npc/fire_slime_hang.png"));
            up2 = ImageIO.read(new File("res/npc/fire_slime_hang1.png"));
            down1 = ImageIO.read(new File("res/npc/fire_slime_fall.png"));
            down2 = ImageIO.read(new File("res/npc/fire_slime_fall1.png")); 
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        //Check if slime is active
        if(active == false) {
            //Slime will activate if player is close enough
            if(this.worldX - (gp.tileSize * 2) > gp.player.worldX - gp.player.screenX &&
               this.worldX + (gp.tileSize * 2) < gp.player.worldX + gp.player.screenX &&
               this.worldY - (gp.tileSize * 2) > gp.player.worldY - gp.player.screenY &&
               this.worldY + (gp.tileSize * 2) < gp.player.worldY + gp.player.screenY ) {
                
                active = true;
            }
        }
        else if(active == true) {
            //When active check if the slime has fallen to the ground, otherwise fall with animation
            if(onGround == false) {
                if (upSpeed <= 0) { 
                    this.direction = "down";
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
                        onGround = true;
                        this.direction = "right";
                    }
                }
            }
            else if(onGround == true) { 
                //Slime has hit the ground and will follow player but fix position to not walk through walls
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
        
                collisionOn = false;
                gp.cChecker.checkTile(this);

                if(collisionOn == true){
                    if(collisionFix < 1){
                        if(direction == "right"){
                            direction = "left";
                        }
                        else if(direction == "left"){
                            direction = "right";
                        }
                        collisionFix++;
                    }
                }
                else if(collisionFix == 1){
                    collisionFix--;
                }
                else if(gp.player.worldX < this.worldX) {
                    direction = "left";
                }
                else if(gp.player.worldX > this.worldX){
                    direction = "right";
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
    }
}
