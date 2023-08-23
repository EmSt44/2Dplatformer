package object;

import java.io.IOException;

import java.io.File;
import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {

    public static boolean Door_left;

    public OBJ_Door() {

        this.solidArea.width = 20;
        this.solidArea.x = 28;

        if(Door_left){
            name = "Door";
            try {
                image = ImageIO.read(new File("res/objects/door_l.png"));
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        else {
            name = "Door";
            try {
                image = ImageIO.read(new File("res/objects/door_r.png"));
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }

        collision = true;
    }
}