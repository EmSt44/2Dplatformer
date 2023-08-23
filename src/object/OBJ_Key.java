package object;

import entity.Entity;
import main.GamePanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {
        super(gp);

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;

        name = "Key";
        try{
            right1 = ImageIO.read(new File("res/objects/key.png"));
            right2 = ImageIO.read(new File("res/objects/key.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}