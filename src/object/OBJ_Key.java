package object;

import java.io.IOException;

import java.io.File;
import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {

    public OBJ_Key() {

        name = "Key";
        try {   
            image = ImageIO.read(new File("res/objects/key.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}