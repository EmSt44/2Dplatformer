package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entity.NPC_Bat;
import entity.NPC_Goombi;
import entity.NPC_FireSlime;
import object.*;

public class AssetSetter { //används för att sätta in objekt/NPC i världen

    GamePanel gp;

    //Lista med mapAssets NPCs
    String mapAssetNPC[];

    //För att spara info från lästa filer NPC
    NPCproperties npcPropertiesList[];
    int npcsamount;

    //npc korrespondering
    private final int npc_bat = 0;
    private final int npc_goombi = 1;
    private final int npc_fireslime = 2;


    public AssetSetter(GamePanel gp) {
        this.gp = gp;

        //Sätt antalet mapAsset till mapsAmount
        mapAssetNPC = new String[gp.tileM.mapsAmount];

        getAssets();
    }

    private void getAssets() {

        //VIKTIGT: Se till att dessa korresponderar med samma map nummer som i TileManager getMaps
        mapAssetNPC[0] = "res/mapAssets/testmap4_NPC"; 
    }

    private void loadNPC(int mapNumber) {
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapAssetNPC[mapNumber]));

            int rows = countRows(mapNumber); //MYCKET ineffektivt, kan göras bättre genom att göra om npcPropertiesList till en länkad lista

            int row = 0;
            npcsamount = 0; //mängdnpcs


            npcPropertiesList = new NPCproperties[rows];

            while(row < rows) {

                String line = br.readLine();

                String numbers[] = line.split(",");
                npcPropertiesList[row] = new NPCproperties();
                npcPropertiesList[row].type = Integer.parseInt(numbers[0]);
                npcPropertiesList[row].x = Integer.parseInt(numbers[1]);
                npcPropertiesList[row].y = Integer.parseInt(numbers[2]);

                npcsamount++;
                row++;

                
            }
            br.close();

        }catch(IOException e) {

        }
    }

    private int countRows(int mapNumber) { //Räknar hur många rader ett dokument har

        int lines = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(mapAssetNPC[mapNumber]));

            while (br.readLine() != null) lines++;

            br.close();

        } catch(IOException e) {

        }
        return lines;

    }

    public void setNPC(int mapNumber) {

        loadNPC(mapNumber);

        int i = 0;

        while(i < npcsamount) {

            if(npcPropertiesList[i].type == npc_bat) {
                gp.npc[i] = new NPC_Bat(gp);
                gp.npc[i].worldX = gp.tileSize * npcPropertiesList[i].x; 
                gp.npc[i].worldY = gp.tileSize * npcPropertiesList[i].y;
            }
            else if(npcPropertiesList[i].type == npc_goombi) {
                gp.npc[i] = new NPC_Goombi(gp);
                gp.npc[i].worldX = gp.tileSize * npcPropertiesList[i].x; 
                gp.npc[i].worldY = gp.tileSize * npcPropertiesList[i].y;
            }
            else if(npcPropertiesList[i].type == npc_fireslime) {
                gp.npc[i] = new NPC_FireSlime(gp);
                gp.npc[i].worldX = gp.tileSize * npcPropertiesList[i].x; 
                gp.npc[i].worldY = gp.tileSize * npcPropertiesList[i].y;
            }

            i++;
        }
        
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = gp.tileSize * 7;
        gp.obj[0].worldY = gp.tileSize * 47;

        //Set door direction
        OBJ_Door.Door_left = true;
        gp.obj[1] = new OBJ_Door();
        gp.obj[1].worldX = gp.tileSize * 4;
        gp.obj[1].worldY = gp.tileSize * 33;

        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = gp.tileSize * 48;
        gp.obj[2].worldY = gp.tileSize * 10;

        //Set door direction
        OBJ_Door.Door_left = false;
        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = gp.tileSize * (49 - 14);
        gp.obj[3].worldY = gp.tileSize * 3;
    }

    public void resetAssetSetter(int mapNumber) {
        setNPC(mapNumber);
        setObject();
    }
    
}
