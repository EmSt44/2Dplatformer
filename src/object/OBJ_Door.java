package object;

import entity.Entity;
import main.GamePanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Door extends Entity {

    public static boolean Door_left;

    public OBJ_Door(GamePanel gp) {
        super(gp);

        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 0;
        solidArea.width = 28;
        solidArea.height = 48;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        name = "Door";
        if(Door_left){
            try{
                right1 = ImageIO.read(new File("res/objects/door_l.png"));
                right2 = ImageIO.read(new File("res/objects/door_l.png"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try{
                right1 = ImageIO.read(new File("res/objects/door_r.png"));
                right2 = ImageIO.read(new File("res/objects/door_r.png"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        collision = true;
    }
}