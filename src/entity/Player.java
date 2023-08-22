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

    //hopp-associerade ackumlatorer och variabler
    public boolean gravity = true;
    public double accumulatedFallSpeed = 1.0;
    public double upSpeed = 0.0;
    public double jumpPower = 20.0; //ändra denna för att ändra hoppets initiella styrka.
    public double jumpFalloff = 1.0; //ändra denna för att ändra hur snabbt hoppet decelerar
    public double gravityModifier = 1.0; //ändra denna för att ändra hur snabbt fallet accelererar

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultValues();

        //ändra dessa till en mer rimlig hitbox
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getPlayerImage();
        
    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 40;
        worldY = gp.tileSize * 40;
        speed = 5;
    }

    public void getPlayerImage() {
        try {
            left = ImageIO.read(new File("res/player/ninja_l.png"));
            right = ImageIO.read(new File("res/player/ninja_r.png"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        //VÄNSTER-HÖGER RÖRELSE
        this.collisionOn = false;

        if(keyH.leftPressed == true) {
            direction = "left";
            gp.cChecker.checkTile(this);
            worldX = collisionOn ? worldX : worldX - speed;
        }
        if(keyH.rightPressed == true) {
            direction = "right";
            gp.cChecker.checkTile(this);
            worldX = collisionOn ? worldX : worldX + speed;
        }
        
        //RÖRELSE NEDÅT (FALLA)
        //om man inte står på marken och inte hoppar uppåt så ska man falla
        if (upSpeed <= 0) { 
            if (!gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed)) {
                this.worldY += accumulatedFallSpeed;
                accumulatedFallSpeed += gravityModifier;
            }
            else {
                //återställ fall speed när man står på marken
                accumulatedFallSpeed = 1;
                while (!gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed)) { //denna så att man landar på marken och inte ovanför
                    this.worldY += accumulatedFallSpeed;
                }
            }
        }

        //RÖRELSE UPPÅT (HOPPA)
        //om man trycker upp, står på marken, inte faller, och hoppar inte just nu, hoppa
        if (accumulatedFallSpeed <= 1 && gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed)) {
            if (keyH.upPressed == true && upSpeed <= 0) {
                upSpeed = jumpPower;
                accumulatedFallSpeed = 1;
            }
        }
        //medans hopp är aktivt
        if (upSpeed > 0) {
            if (!gp.cChecker.checkTileAbove(this, (int) upSpeed)) {
                this.worldY -= upSpeed;
                upSpeed -= jumpFalloff;
            } 
            else {
                //nollställ jump speed om man slår i tak
                upSpeed = 0;
                while (!gp.cChecker.checkTileAbove(this, 1)) { //ser till att vi inte slår huvudet i taket. Ouch.
                    worldY--;
                }
            }
        }

        //NPC KOLLISION
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        if (npcIndex != 999) System.out.println(npcIndex);


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