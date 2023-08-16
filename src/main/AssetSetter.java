package main;

import entity.NPC_Bat;

public class AssetSetter { //används för att sätta in objekt/NPC i världen

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
        this.setNPC();
    }

    public void setNPC() {
        
        gp.npc[0] = new NPC_Bat(gp);
        gp.npc[0].worldX = gp.tileSize * 3; 
        gp.npc[0].worldY = gp.tileSize * 5;
    }
    
}
