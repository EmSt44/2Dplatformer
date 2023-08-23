package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Shuriken;


import visual.GenericDeathSmoke;


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

    int immunityDuration = 60; //Antal frames man är immun efter att ha tagit skada

    public final int screenX;
    public final int screenY;

    //Hur mycket skada man gör genom att hoppa på folk
    int damage = 1;
    //Hur många nycklar spelaren har
    int hasKey = 0;

    //Andra variabler/ackumlatorer
    int immunityCounter = 0;
    int damageAnimation = 0;
    boolean jumpPressed = false; 
    boolean jumpThisFrame = false;
    
    BufferedImage damageVisual;


    public Player(GamePanel gp, KeyHandler keyH) {
        
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultValues();

        solidArea = new Rectangle();
        solidArea.x = 15;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 18;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getPlayerImage();
        
    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 40;
        worldY = gp.tileSize * 40;
        speed = 5;

        //Player Status (liv)
        maxLife = 6; //bör vara jämnt
        life = maxLife; //Börja med max liv

        //Player "inventory"
        hasKey = 0;

        //Player projectile
        projectile = new OBJ_Shuriken(gp);
    }

    public void getPlayerImage() {
        try {
            left1 = ImageIO.read(new File("res/player/ninja_l.png"));
            right1 = ImageIO.read(new File("res/player/ninja_r.png"));
            damageVisual = ImageIO.read(new File("res/player/bloodsplatter.png"));

        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        // new GenericDeathSmoke(gp, worldX, worldY);

        //VÄNSTER-HÖGER RÖRELSE
        this.collisionOn = false;

        int objIndex = 999;
        int damagedNpc = 999;

        if(keyH.leftPressed == true) {
            direction = "left";

            gp.cChecker.checkTile(this);
            objIndex = gp.cChecker.checkObject(this, true);
            
            pickUpObject(objIndex);
            worldX = collisionOn ? worldX : worldX - speed;
        }
        if(keyH.rightPressed == true) {
            direction = "right";

            gp.cChecker.checkTile(this);
            objIndex = gp.cChecker.checkObject(this, true);
            
            pickUpObject(objIndex);
            worldX = collisionOn ? worldX : worldX + speed;
        }

        //Så att man inte kan hålla in för att konstant hoppa
        if(keyH.upPressed == true && !jumpPressed) {
            jumpThisFrame = true;
            jumpPressed = true;
        }
        else if (keyH.upPressed == true && jumpPressed) {
            jumpThisFrame = false;
        }
        else if (keyH.upPressed == false && jumpPressed) {
            jumpThisFrame = false;
            jumpPressed = false;
        }
            
        
        //RÖRELSE NEDÅT (FALLA)
        //om man inte står på marken och inte hoppar uppåt så ska man falla
        if (upSpeed <= 0) { 
            collisionOn = false; 
            gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed);
            objIndex = gp.cChecker.checkObject(this, true);
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
                objIndex =  gp.cChecker.checkObject(this, true);
                while (!collisionOn) { //denna så att man landar på marken och inte ovanför
                    this.worldY += accumulatedFallSpeed;
                    gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed);
                    objIndex = gp.cChecker.checkObject(this, true);
                }
            }
        }

        //RÖRELSE UPPÅT (HOPPA)
        //om man trycker upp, står på marken, inte faller, och hoppar inte just nu, hoppa
        if (accumulatedFallSpeed <= 1) {
            collisionOn = false;
            gp.cChecker.checkTileBelow(this, (int) accumulatedFallSpeed);
            objIndex = gp.cChecker.checkObject(this, true);
            if (jumpThisFrame && upSpeed <= 0 && collisionOn) {
                upSpeed = jumpPower;
                accumulatedFallSpeed = 1;
            }
        }
        //medans hopp är aktivt
        if (upSpeed > 0) {
            collisionOn = false;
            gp.cChecker.checkTileAbove(this, (int) upSpeed);
            objIndex = gp.cChecker.checkObject(this, true);
            //npc kollison
            if (!collisionOn) {
                this.worldY -= upSpeed;
                upSpeed -= jumpFalloff;
            } 
            else {
                //nollställ jump speed om man slår i tak
                upSpeed = 1;
                collisionOn = false;
                while (!collisionOn) { //ser till att vi inte slår huvudet i taket. Ouch.
                    worldY--;
                    gp.cChecker.checkTileAbove(this, (int) upSpeed);
                    objIndex = gp.cChecker.checkObject(this, true);
                    //npc kollision?
                }
                upSpeed = 0;
            }
        }

        pickUpObject(objIndex);

        //NPC KOLLISION
        //npc stampningscheck
        collisionOn = false;
        damagedNpc = gp.cChecker.checkEntityBelow(this, gp.npc);
        if (collisionOn && damagedNpc != 999 && accumulatedFallSpeed > 2.0 && gp.npc[damagedNpc].stompable) {
            upSpeed += 15; //kanske ändra till nån slags variabel bounceSpeed
            accumulatedFallSpeed = 1.0;
            immunityCounter += 20;
            gp.npc[damagedNpc].takeDamage(damage);
            // System.out.println("entity " + damagedNpc + " tog skada");
        }
        else {
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        
            if (npcIndex != 999 && immunityCounter == 0 && gp.npc[npcIndex].damage > 0) {
                this.takeDamage(gp.npc[npcIndex].damage);
                // System.out.println("entity " + npcIndex + " gjorde skada");
                immunityCounter = immunityDuration;
                damageAnimation = 15;
            }
        }

        //Nedräkning av i-frames
        if (immunityCounter > 0) {
            immunityCounter--;
        }
        if (damageAnimation > 0) {
            damageAnimation--;
        }

        if(gp.keyH.shootKeyPressed == true && projectile.alive == false) {

            //Set default coordinates, direction and user
            projectile.set(worldX, worldY, direction, true, this, damage);

            //Add to the arraylist
            gp.projectileList.add(projectile);
        }
    }

    public void pickUpObject(int i) {

        if(i != 999) {

            String objName = gp.obj[i].name;

            switch(objName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    System.out.println("Key:" + hasKey);
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        System.out.println("Key:" + hasKey);
                    }
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        if (direction == "left") {
            image = left1;
        } else if (direction == "right") {
            image = right1;
        } else { //höger som standard, om ingen direction
            image = right1;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        if (damageAnimation > 0) { //blodstänk
            g2.drawImage(damageVisual, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void resetPlayer() {
        setDefaultValues();
    }
}