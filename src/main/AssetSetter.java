package main;

import entity.NPC_Bat;
import object.*;

public class AssetSetter { //används för att sätta in objekt/NPC i världen

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setNPC() {
        
        gp.npc[0] = new NPC_Bat(gp);
        gp.npc[0].worldX = gp.tileSize * 40; 
        gp.npc[0].worldY = gp.tileSize * 45;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = gp.tileSize * 7;
        gp.obj[0].worldY = gp.tileSize * 33;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = gp.tileSize * 39;
        gp.obj[1].worldY = gp.tileSize * 46;

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = gp.tileSize * 4;
        gp.obj[2].worldY = gp.tileSize * 33;

    }

    public void resetAssetSetter() {
        setNPC();
        setObject();
    }
    
}
