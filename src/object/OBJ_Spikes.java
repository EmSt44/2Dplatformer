package object;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import entity.Entity;
import main.GamePanel;

public class OBJ_Spikes extends Entity {

    public static int spike_direction; //0 pekar upp, 1 pekar höger, 2 pekar ner, 3 pekar vänster

    public OBJ_Spikes(GamePanel gp) {
        super(gp);

        solidArea = new Rectangle();
        damage = 2;
        collision = true;

        name = "Spikes";
        try {
            switch (spike_direction) {
                case 0:
                right1 = ImageIO.read(new File("res/objects/spikes_up.png"));
                solidArea.x = 0;
                solidArea.y = 24;
                solidArea.width = 48;
                solidArea.height = 24;
                break;
                case 1:
                right1 = ImageIO.read(new File("res/objects/spikes_right.png"));
                solidArea.x = 0;
                solidArea.y = 0;
                solidArea.width = 24;
                solidArea.height = 48;
                break;
                case 2:
                right1 = ImageIO.read(new File("res/objects/spikes_down.png"));
                solidArea.x = 0;
                solidArea.y = 0;
                solidArea.width = 48;
                solidArea.height = 24;
                break;
                case 3:
                right1 = ImageIO.read(new File("res/objects/spikes_left.png"));
                solidArea.x = 24;
                solidArea.y = 0;
                solidArea.width = 24;
                solidArea.height = 48;
                break;
            }
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
    }
    
}
