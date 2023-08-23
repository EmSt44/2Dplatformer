package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {


GamePanel gp; //gamepanel
public Tile[] tile;  //lista med olika typer av tiles
public int mapTileNum[][];

//Lista med alla maps, representerade som strängar av sin filepath, ex: "res/maps/map1"
public String[] maps;
public final int mapsAmount = 1; //maximalt antal maps VIKTIGT! detta måste korrespondera med antalet maps exakt


public TileManager(GamePanel gp) {

    this.gp = gp;
    tile = new Tile[11]; //maximal mängd typer av tiles, ändra denna siffra för att lägga till fler
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    maps = new String[mapsAmount]; //lista med maps med maximalantalet

    getMaps();
    getTileImage();
}

public void getMaps() { //lägg till maps här

    maps[0] = "res/maps/testmap4";
}

public void getTileImage() { //läser in png filer för varje tile och lägger dem i ett index i tile listan

try {
     tile[0] = new Tile();
     tile[0].image = ImageIO.read(new File("res/tiles/tegel.png"));
     tile[0].collision = true;

     tile[1] = new Tile();
     tile[1].image = ImageIO.read(new File("res/tiles/tegel_bakgrund.png"));

     tile[2] = new Tile();
     tile[2].image = ImageIO.read(new File("res/tiles/grass.png"));
     tile[2].collision = true;

     tile[3] = new Tile();
     tile[3].image = ImageIO.read(new File("res/tiles/tree_1.png"));

     tile[4] = new Tile();
     tile[4].image = ImageIO.read(new File("res/tiles/forrest.png"));
     
     tile[5] = new Tile();
     tile[5].image = ImageIO.read(new File("res/tiles/tree_2.png"));

     tile[6] = new Tile();
     tile[6].image = ImageIO.read(new File("res/tiles/brickwall_lamp.png"));


     tile[7] = new Tile();
     tile[7].image = ImageIO.read(new File("res/tiles/brickwall_grasswalk.png"));
     tile[7].collision = true;

     tile[8] = new Tile();
     tile[8].image = ImageIO.read(new File("res/tiles/sky.png"));

     tile[9] = new Tile();
     tile[9].image = ImageIO.read(new File("res/tiles/forrest_top.png"));

     tile[10] = new Tile();
     tile[10].image = ImageIO.read(new File("res/tiles/grass_sky.png"));
} catch(IOException e) {
    e.printStackTrace();
  }
 }

public void loadMap(int mapNumber) {

    try {

    //InputStream is = getClass().getResourceAsStream("res/maps/testmap"); //läs map filen här
    BufferedReader br = new BufferedReader(new FileReader(maps[mapNumber]));
    
    //startvärde för läsningen av kartan
    int col = 0;
    int row = 0;

    while(col < gp.maxWorldCol && row < gp.maxWorldRow) { //får inte gå utanför den tillåtna storleken på spelvärlden

        String line = br.readLine();
        
        while(col < gp.maxWorldCol) { //läs kolumnen på nuvarande raden

            String numbers[] = line.split(" ");
            int num = Integer.parseInt(numbers[col]);

            mapTileNum[col][row] = num;
            col++;
        }
        if(col == gp.maxWorldCol) { //när sista kolumnen är nådd byt rad
            col = 0;
            row++;
        }
    }
    br.close();

  } catch(IOException e) {

  }
}

public void draw(Graphics2D g2) {

    int worldCol = 0;
    int worldRow = 0;

    while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

        int tileNum = mapTileNum[worldCol][worldRow];

        int worldX = worldCol * gp.tileSize;
        int worldY = worldRow * gp.tileSize;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                //g2.drawImage(tile[mapTileNum[worldCol][worldRow]].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
           }
        
        worldCol++;

     if(worldCol == gp.maxWorldCol) {
         worldCol = 0;
         worldRow++;

    }
 }
    
}

}