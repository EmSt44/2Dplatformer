package entity;

import main.GamePanel;
//import main.CollisionChecker;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user, int damage) {

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.life = this.maxLife;
        this.user = user;
        this.gravity = false;
        this.damage = damage;
    }

    public void update() {

        int hitEntity = 999;

        switch(direction) {
            case "right": 
                worldX += speed;
                break;
            case "left":
                worldX -= speed;
                break;
        }

        if(user == gp.player){
            
            collisionOn = false;
            gp.cChecker.checkTile(this);
            hitEntity = gp.cChecker.checkEntity(this, gp.npc);

            if(hitEntity != 999) {
                if(gp.npc[hitEntity].stompable == true){
                    gp.npc[hitEntity].takeDamage(damage);
                }
                life = 0;
                alive = false;
            }
            else if(collisionOn == true){
                life = 0;
                alive = false;
            }
        }
        if(user != gp.player){

            collisionOn = false;
            gp.cChecker.checkTile(this);
            hitEntity = gp.cChecker.checkProjectile(gp.player, this);

            if(hitEntity == 1) {
                gp.player.takeDamage(damage);
                life = 0;
                alive = false;
            }
            else if(collisionOn == true){
                life = 0;
                alive = false;
            }

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