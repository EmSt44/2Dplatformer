package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Trophy extends Entity {

    public OBJ_Trophy(GamePanel gp) {
        super(gp);

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;

        name = "Trophy";
        try{
            right1 = ImageIO.read(new File("res/objects/trophy.png"));
            right2 = ImageIO.read(new File("res/objects/trophy.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}