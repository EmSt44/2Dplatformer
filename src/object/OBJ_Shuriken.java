package object;

import entity.Projectile;
import main.GamePanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Shuriken extends Projectile{

    GamePanel gp;

    public OBJ_Shuriken(GamePanel gp){
        super(gp);
        this.gp = gp;

        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 12;
        solidArea.width = 24;
        solidArea.height = 24;
        name = "Shuriken";
        speed = 6;
        maxLife = 500;
        life = maxLife;
        alive = false;
        damage = 1;
        getImage();
        
    }

    public void getImage() {
        try{
            left1 = ImageIO.read(new File("res/projectiles/shuriken.png"));
            left2 = ImageIO.read(new File("res/projectiles/shuriken1.png"));
            right1 = ImageIO.read(new File("res/projectiles/shuriken.png"));
            right2 = ImageIO.read(new File("res/projectiles/shuriken1.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}