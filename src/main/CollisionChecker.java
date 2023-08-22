package main;

import entity.Entity;
//import tile.TileManager;

public class CollisionChecker {
    GamePanel gp;


    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    //checks collision for all entities not just the player. does not account for gravity. depends on it's direction
    public void checkTile(Entity entity){

        int entityLeftX = entity.worldX + entity.solidArea.x;
        int entityRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.worldY + entity.solidArea.y;
        int entityBottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftX/gp.tileSize;
        int entityRightCol = entityRightX/gp.tileSize;
        int entityTopRow = entityTopY/gp.tileSize;
        int entityBottomRow = entityBottomY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol= (entityRightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
                }
                break;
        }
    }

    public boolean checkTileBelow(Entity entity, int fallSpeed) { //TRUE om man står/faller på en solid tile (enl fallSpeed), FALSE annars
        int entityLeftX = entity.worldX + entity.solidArea.x;
        int entityRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityBottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftX/gp.tileSize;
        int entityRightCol = entityRightX/gp.tileSize;
        int entityBottomRow = entityBottomY/gp.tileSize;

        int tileNum1, tileNum2;

        entityBottomRow = (entityBottomY + fallSpeed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

        return (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true);
    }

    public boolean checkTileAbove(Entity entity, int upSpeed) { //TRUE om toppen av hitbox slår/ska slå i en collision tile (enl upSpeed) FALSE annars
        int entityLeftX = entity.worldX + entity.solidArea.x;
        int entityRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.worldY + entity.solidArea.y;

        int entityLeftCol = entityLeftX/gp.tileSize;
        int entityRightCol = entityRightX/gp.tileSize;
        int entityTopRow = entityTopY/gp.tileSize;

        int tileNum1, tileNum2;

        entityTopRow = (entityTopY - upSpeed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

        return (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true);
    }


    //Kollar om entity kolliderar/kommer kollidera nästa frame med någon i target,
    //om så är fallet, collisionOn = true och target i frågas index i NPC-listan returneras
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                //Entity's hitbox plats i världen
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Target's hitbox plats i världen
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                if (entity.gravity == false) {
                    switch(entity.direction) {
                        case "up":
                            entity.solidArea.y -= entity.speed;
                            break;
                        case "down":
                            entity.solidArea.y += entity.speed;
                            break;
                        case "left": 
                            entity.solidArea.x -= entity.speed;
                            break;
                        case "right":
                            entity.solidArea.x += entity.speed;
                            break;
                    }
                }
                else if (entity.gravity == true) {
                    if (entity.upSpeed > 0.0) {
                        entity.solidArea.y -= entity.upSpeed;
                    }
                    else {
                        entity.solidArea.y += entity.accumulatedFallSpeed;
                    }
                    switch (entity.direction) {
                        case "left": 
                                entity.solidArea.x -= entity.speed;
                                break;
                        case "right":
                            entity.solidArea.x += entity.speed;
                            break;
                    }
                }

                if (entity.solidArea.intersects(target[i].solidArea)) {
                    entity.collisionOn = true;
                    index = i;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
                
            }
        }
        return index;
    }
}
