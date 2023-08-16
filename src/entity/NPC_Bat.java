package entity;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPC_Bat extends Entity{
    
    public NPC_Bat(GamePanel gp) {
        super(gp);

        direction = "right";
        speed = 3;
        //när collision finns lägg till hitbox här
    }

    public void getImage() {
        try {
            left = ImageIO.read(new File("res/player/ninja_l.png")); //använder player texturen för tillfället
            right = ImageIO.read(new File("res/player/ninja_r.png"));

        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
