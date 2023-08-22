package object;

import java.io.IOException;

import java.io.File;
import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {

    public OBJ_Door() {

        solidArea.width = 10;
        solidArea.x = 38;

        name = "Door";
        try {
            image = ImageIO.read(new File("res/objects/door.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        collision = true;
    }
}