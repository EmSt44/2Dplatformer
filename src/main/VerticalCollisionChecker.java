package main;

import entity.Entity;

public class VerticalCollisionChecker { //används för att kolla om entity bör/kan falla eller stiga.
    GamePanel gp;


    public VerticalCollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public boolean checkFeetCollision(Entity entity, int fallSpeed) { //TRUE om man står/faller på en solid tile, FALSE annars
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

    public boolean checkHeadCollision(Entity entity, int upSpeed) { //TRUE om toppen av hitbox slår/ska slå i en collision tile. FALSE annars
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
}
