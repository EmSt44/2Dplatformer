package visual;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class GenericDeathSmoke extends VisualEffect {
    public GenericDeathSmoke(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.lifetime = 10;
        getImage();
    }

    public void getImage() {
        try {
            image = ImageIO.read(new File("res/npc/generic_death_smoke.png"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
