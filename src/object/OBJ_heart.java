package object;

import entity.Entity;
import main.GamePanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_heart extends Entity {

    GamePanel gp;

    public OBJ_heart(GamePanel gp) {
        super(gp);

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;

        this.gp = gp;

        name = "heart";
        try{
            image = ImageIO.read(new File("res/objects/heart_full.png"));
            image2 = ImageIO.read(new File("res/objects/heart_half.png"));
            image3 = ImageIO.read(new File("res/objects/heart_empty.png"));            
            //Scale them to tilesize?
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}