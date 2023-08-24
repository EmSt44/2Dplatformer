package object;

import entity.Projectile;
import main.GamePanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Arrow extends Projectile{

    GamePanel gp;

    public OBJ_Arrow(GamePanel gp){
        super(gp);
        this.gp = gp;

        solidArea = new Rectangle();
        solidArea.x = 11;
        solidArea.y = 4;
        solidArea.width = 26;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        name = "Arrow";
        speed = 5;
        maxLife = 500;
        life = maxLife;
        alive = false;
        damage = 1;
        getImage();
        
    }

    public void getImage() {
        try{
            left1 = ImageIO.read(new File("res/projectiles/arrow_l.png"));
            left2 = ImageIO.read(new File("res/projectiles/arrow_l.png"));
            right1 = ImageIO.read(new File("res/projectiles/arrow_r.png"));
            right2 = ImageIO.read(new File("res/projectiles/arrow_r.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}