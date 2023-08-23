package entity;

import main.GamePanel;
//import main.CollisionChecker;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, int life, Entity user) {

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.life = life;
        this.user = user;
        this.gravity = false;
    }

    public void update() {

        int index;

        switch(direction) {
            case "right": 
                worldX += speed;
                break;
            case "left":
                worldX -= speed;
                break;
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);
        index = gp.cChecker.checkEntity(this, gp.npc);

        if(collisionOn == true){
            life = 0;
        }
        else if(index != 999) {
            life = 0;
        }

        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }
}