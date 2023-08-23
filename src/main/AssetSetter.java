package main;

import entity.NPC_Bat;
import entity.NPC_Goombi;
import entity.NPC_FireSlime;
import object.*;

public class AssetSetter { //används för att sätta in objekt/NPC i världen

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setNPC() {
        
        gp.npc[0] = new NPC_Bat(gp);
        gp.npc[0].worldX = gp.tileSize * 45; 
        gp.npc[0].worldY = gp.tileSize * 45;

        gp.npc[1] = new NPC_Goombi(gp);
        gp.npc[1].worldX = gp.tileSize * 10;
        gp.npc[1].worldY = gp.tileSize * 47;
        
        gp.npc[2] = new NPC_FireSlime(gp);
        gp.npc[2].worldX = gp.tileSize * 14;
        gp.npc[2].worldY = gp.tileSize * 45;

        gp.npc[3] = new NPC_Bat(gp);
        gp.npc[3].worldX = gp.tileSize * 18;
        gp.npc[3].worldY = gp.tileSize * 13;

        gp.npc[4] = new NPC_FireSlime(gp);
        gp.npc[4].worldX = gp.tileSize * (49 - 14);
        gp.npc[4].worldY = gp.tileSize * 8;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = gp.tileSize * 7;
        gp.obj[0].worldY = gp.tileSize * 47;

        //Set door direction
        OBJ_Door.Door_left = true;
        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = gp.tileSize * 4;
        gp.obj[1].worldY = gp.tileSize * 33;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = gp.tileSize * 48;
        gp.obj[2].worldY = gp.tileSize * 10;

        //Set door direction
        OBJ_Door.Door_left = false;
        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = gp.tileSize * (49 - 14);
        gp.obj[3].worldY = gp.tileSize * 3;
    }

    public void resetAssetSetter() {
        setNPC();
        setObject();
    }
    
}
