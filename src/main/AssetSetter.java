package main;

import entity.NPC_Bat;
import entity.NPC_Goombi;
import object.*;

public class AssetSetter { //används för att sätta in objekt/NPC i världen

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
        this.setNPC();
    }

    public void setNPC() {
        
        gp.npc[0] = new NPC_Bat(gp);
        gp.npc[0].worldX = gp.tileSize * 40; 
        gp.npc[0].worldY = gp.tileSize * 45;

        // gp.npc[1] = new NPC_Goombi(gp);
        // gp.npc[1].worldX = gp.tileSize * 11;
        // gp.npc[1].worldY = gp.tileSize * 47;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = gp.tileSize * 7;
        gp.obj[0].worldY = gp.tileSize * 33;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = gp.tileSize * 7;
        gp.obj[1].worldY = gp.tileSize * 48;

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = gp.tileSize * 4;
        gp.obj[2].worldY = gp.tileSize * 33;

    }
    
}
