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
    mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

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
    BufferedReader br = new BufferedReader(new FileReader("res/maps/testmap2"));
    
    //startvärde för läsningen av kartan
    int col = 0;
    int row = 0;

    while(col < gp.maxScreenCol && row < gp.maxScreenRow) { //får inte gå utanför den tillåtna storleken på spelvärlden

        String line = br.readLine();
        
        while(col < gp.maxScreenCol) { //läs kolumnen på nuvarande raden

            String numbers[] = line.split(" ");
            int num = Integer.parseInt(numbers[col]);

            mapTileNum[col][row] = num;
            col++;
        }
        if(col == gp.maxScreenCol) { //när sista kolumnen är nådd byt rad
            col = 0;
            row++;
        }
    }
    br.close();

  } catch(IOException e) {

  }
}

public void draw(Graphics2D g2) {

    int col = 0;
    int row = 0;
    int x = 0;
    int y = 0;

    while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

        g2.drawImage(tile[mapTileNum[col][row]].image, x, y, gp.tileSize, gp.tileSize, null);
        col++;
        x += gp.tileSize;

     if(col == gp.maxScreenCol) {
         col = 0;
         x = 0;
         row++;
         y += gp.tileSize;

    }
 }
    
}

}