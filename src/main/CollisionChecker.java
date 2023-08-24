package main;

import entity.Entity;
import entity.Projectile;
import entity.Player;
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


    public void checkTileBelow(Entity entity, int fallSpeed) { //TRUE om man står/faller på en solid tile, FALSE annars

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

        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
        }
    }

    public void checkTileAbove(Entity entity, int upSpeed) { //TRUE om toppen av hitbox slår/ska slå i en collision tile (enl upSpeed) FALSE annars
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

        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
        }
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


    //Kollar om entity kolliderar/kommer kollidera nästa frame med ett objekt i gp.obj,
    //om så är fallet och objektet har collision, collisionOn = true och
    //om entity är en spelare så returneras objektets index i gp.obj.
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for(int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null){

                //Entity's hitbox plats i världen
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //Ett objekt ur gp.obj's hitbox plats i världen
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
                
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

                if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                    if(gp.obj[i].collision == true){
                        entity.collisionOn = true;
                    }
                    if(player == true){
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
    
    //Kollar om entity kolliderar/kommer kollidera nästa frame med någon i target UNDER DEN,
    //om så är fallet, collisionOn = true och target i frågas index i NPC-listan returneras
    //Notera att med gravity = true kollar den endast under om du faller, och gravity = false om direction = "down"
    public int checkEntityBelow(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                //Entity's hitbox plats i världen
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Target's hitbox plats i världen
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                if (entity.gravity == false && entity.direction == "down") {
                    entity.solidArea.y += entity.speed;
                }               
                else if (entity.gravity == true && entity.upSpeed < 0.0) {
                    entity.solidArea.y += entity.accumulatedFallSpeed;
                    // switch (entity.direction) { //osäker om denna switchcase ska vara med.
                    //     case "left": 
                    //             entity.solidArea.x -= entity.speed;
                    //             break;
                    //     case "right":
                    //         entity.solidArea.x += entity.speed;
                    //         break;
                    //}
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

    public int checkProjectile(Entity entity, Entity projectile){
        int index = 0;
        //Entity's hitbox plats i världen
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        //Ett objekt ur gp.obj's hitbox plats i världen
        projectile.solidArea.x = projectile.worldX + projectile.solidArea.x;
        projectile.solidArea.y = projectile.worldY + projectile.solidArea.y;
                
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

        if(entity.solidArea.intersects(projectile.solidArea)){
            index = 1;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        projectile.solidArea.x = projectile.solidAreaDefaultX;
        projectile.solidArea.y = projectile.solidAreaDefaultY;
        return index;
    }
}
