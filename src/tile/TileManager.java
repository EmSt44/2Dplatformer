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
Tile[] tile;  //lista med olika typer av tiles
int mapTileNum[][];

public TileManager(GamePanel gp) {

    this.gp = gp;
    tile = new Tile[10]; //maximal mängd typer av tiles, ändra denna siffra för att lägga till fler
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

    getTileImage();
    loadMap();
}

public void getTileImage() { //läser in png filer för varje tile och lägger dem i ett index i tile listan

try {
     tile[0] = new Tile();
     tile[0].image = ImageIO.read(new File("res/tiles/tegel.png"));

     tile[1] = new Tile();
     tile[1].image = ImageIO.read(new File("res/tiles/tegel_bakgrund.png"));


    


} catch(IOException e) {
    e.printStackTrace();
  }
 }

public void loadMap() {

    try {

    //InputStream is = getClass().getResourceAsStream("res/maps/testmap"); //läs map filen här
    BufferedReader br = new BufferedReader(new FileReader("res/maps/testmap3"));
    
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