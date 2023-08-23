package entity;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPC_Goombi extends Entity{

    public double accumulatedFallSpeed = 1.0;
    public double upSpeed = 0.0;
    public boolean gravity = true;
    public double gravityModifier = 1.0; //ändra denna för att ändra hur snabbt fallet accelererar

    public NPC_Goombi(GamePanel gp) {
        super(gp);
        getImage();
        direction = "right";
        speed = 2;
        //när collision finns lägg till hitbox här
        solidArea = new Rectangle();
        solidArea.x = 3;
        solidArea.y = 6;
        solidArea.width = 42;
        solidArea.height = 42;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        maxLife = 1;
        life = 1;
        damage = 1;
    }

    public void getImage() {
        try {
            left1 = ImageIO.read(new File("res/npc/goombi_l.png"));
            left2 = ImageIO.read(new File("res/npc/goombi_l.png"));
            right1 = ImageIO.read(new File("res/npc/goombi_r.png"));
            right2 = ImageIO.read(new File("res/npc/goombi_r.png"));

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
        
        this.collisionOn = false;
        gp.cChecker.checkTile(this);

        if(this.collisionOn == true && this.direction == "right"){
            this.direction = "left";
        }
        else if(this.collisionOn == true && this.direction == "left") {
            this.direction = "right";
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
