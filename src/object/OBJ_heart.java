package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_heart extends SuperObject {
    
    GamePanel gp;

    public OBJ_heart(GamePanel gp) {

        this.gp = gp;

        name = "heart";

        try {
            image = ImageIO.read(new File("res/objects/heart_full.png"));
            image2 = ImageIO.read(new File("res/objects/heart_half.png"));
            image3 = ImageIO.read(new File("res/objects/heart_empty.png"));
            //Scale them to tilesize?

} catch(IOException e) {
    e.printStackTrace();
  }
    }
}