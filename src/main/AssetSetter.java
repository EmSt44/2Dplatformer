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
    //Lista med mapAssets Objekt
    String mapAssetObject[];
    //Lista med mapAssets Player
    String mapAssetPlayer[];

    //För att spara info från lästa filer NPC
    NPCproperties npcPropertiesList[];
    int npcsamount;

    //För att spara info från lästa filer OBJEKT
    OBJproperties objPropertiesList[];
    int objsamount;

    //För att spara spelarens koordinater
    int playerCoords[];

    //npc korrespondering
    private final int npc_bat = 0;
    private final int npc_goombi = 1;
    private final int npc_fireslime = 2;

    //objekt korrespondering
    private final int obj_door = 0;
    private final int obj_key = 1;
    private final int obj_shuriken = 2;
    private final int obj_trophy = 4;


    public AssetSetter(GamePanel gp) {
        this.gp = gp;

        //Sätt antalet mapAsset till mapsAmount
        mapAssetNPC = new String[gp.tileM.mapsAmount];
        mapAssetObject = new String[gp.tileM.mapsAmount];
        mapAssetPlayer = new String[gp.tileM.mapsAmount];

        getAssets();
    }

    private void getAssets() {

        //VIKTIGT: Se till att dessa korresponderar med samma map nummer som i TileManager getMaps

        //NPC
        mapAssetNPC[0] = "res/mapAssets/testmap4_NPC";
        mapAssetNPC[1] = "res/mapAssets/testmap5_NPC";  

        //OBJECT
        mapAssetObject[0] = "res/mapAssets/testmap4_OBJECT";
        mapAssetObject[1] = "res/mapAssets/testmap5_OBJECT";

        //PLAYER
        mapAssetPlayer[0] = "res/mapAssets/testmap4_PLAYER";
        mapAssetPlayer[1] = "res/mapAssets/testmap5_PLAYER";
    }

    private void loadNPC(int mapNumber) {
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapAssetNPC[mapNumber]));

            int rows = countRows(mapNumber, mapAssetNPC); //MYCKET ineffektivt, kan göras bättre genom att göra om npcPropertiesList till en länkad lista

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

    private int countRows(int mapNumber, String list[]) { //Räknar hur många rader ett dokument har

        int lines = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(list[mapNumber]));

            while (br.readLine() != null) lines++;

            br.close();

        } catch(IOException e) {

        }
        return lines;

    }

    private void setNPC(int mapNumber) {

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

    private void loadObject(int mapNumber) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapAssetObject[mapNumber]));

            int rows = countRows(mapNumber, mapAssetObject); //MYCKET ineffektivt, kan göras bättre genom att göra om objPropertiesList till en länkad lista

            int row = 0;
            objsamount = 0; //mängd objekt


            objPropertiesList = new OBJproperties[rows];

            while(row < rows) {

                String line = br.readLine();

                String numbers[] = line.split(",");
                objPropertiesList[row] = new OBJproperties();
                objPropertiesList[row].type = Integer.parseInt(numbers[0]);
                objPropertiesList[row].x = Integer.parseInt(numbers[1]);
                objPropertiesList[row].y = Integer.parseInt(numbers[2]);
                objPropertiesList[row].side = Integer.parseInt(numbers[3]);

                objsamount++;
                row++;

            }
            br.close();

        }catch(IOException e) {

        }
    }

    private void setObject(int mapNumber) {

        loadObject(mapNumber);

        int i = 0;

        while(i < objsamount) {

            if(objPropertiesList[i].type == obj_door) {
                if(objPropertiesList[i].side == 0) { //0 = false
                    OBJ_Door.Door_left = false;
                }
                else if(objPropertiesList[i].side == 1) { //1 = true
                    OBJ_Door.Door_left = true;
                }
                gp.obj[i] = new OBJ_Door(gp);
                gp.obj[i].worldX = gp.tileSize * objPropertiesList[i].x;
                gp.obj[i].worldY = gp.tileSize * objPropertiesList[i].y;
            }
            else if(objPropertiesList[i].type == obj_key) {
                gp.obj[i] = new OBJ_Key(gp);
                gp.obj[i].worldX = gp.tileSize * objPropertiesList[i].x;
                gp.obj[i].worldY = gp.tileSize * objPropertiesList[i].y;
            }
            else if(objPropertiesList[i].type == obj_shuriken) {
                gp.obj[i] = new OBJ_Shuriken(gp);
                gp.obj[i].worldX = gp.tileSize * objPropertiesList[i].x;
                gp.obj[i].worldY = gp.tileSize * objPropertiesList[i].y;
            }
            else if(objPropertiesList[i].type == obj_trophy) {
                gp.obj[i] = new OBJ_Trophy(gp);
                gp.obj[i].worldX = gp.tileSize * objPropertiesList[i].x;
                gp.obj[i].worldY = gp.tileSize * objPropertiesList[i].y;
            }
            i++;
        }
    }

    private void loadPlayer(int mapNumber) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapAssetPlayer[mapNumber]));


                String line = br.readLine();

                String numbers[] = line.split(",");
                
                playerCoords = new int[2];
                playerCoords[0] = Integer.parseInt(numbers[0]);
                playerCoords[1] = Integer.parseInt(numbers[1]);

            br.close();

        }catch(IOException e) {

        }
    }

    private void setPlayer(int mapNumber) {

        loadPlayer(mapNumber);

        gp.player.setDefaultValues(playerCoords[0], playerCoords[1]);
    }

    public void resetAssetSetter(int mapNumber) {
        setNPC(mapNumber);
        setObject(mapNumber);
        setPlayer(mapNumber);
    }
    
}
