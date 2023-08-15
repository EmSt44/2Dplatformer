package tile;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

GamePanel gp;
public Tile[] tile;
public int mapTileNum[][];

public TileManager(GamePanel gp) {

    this.gp = gp;
    tile = new Tile[10]; //maximal mängd typer av tiles, ändra denna siffra för att lägga till fler

    getTileImage();
}

public void getTileImage() { //instansierar alla tiles

try {
     tile[0] = new Tile();
     tile[0].image = ImageIO.read(new File("res/tiles/redtile.png"));

     tile[1] = new Tile();
     tile[1].image = ImageIO.read(new File("res/tiles/blacktile.png"));


    


} catch(IOException e) {
    e.printStackTrace();
  }
 }

public void draw(Graphics2D g2) {
    g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null); //ritar upp tile0 i koordinaterna 0,0
}

}