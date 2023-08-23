package visual;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;

//nån slags visuell effekt, typ partikel eller icke-interagerbar animation
public abstract class VisualEffect {
    public BufferedImage image; //man kan vilja ha fler än en image för animationer
    public GamePanel gp;

    int visIndex;

    //effektens varaktighet i frames
    int lifetime;

    //koordinater i pixlar
    int worldX;
    int worldY;

    //till skillnad från entity skapas visualeffect bara i runtime och därför 
    //sätts in i första null plats i vis arrayen direkt vid skapelse
    public VisualEffect(GamePanel gp, int worldX, int worldY) {
        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;

        for (int i = 0; i < gp.vis.length; i++) { 
            if (gp.vis[i] == null) {
                gp.vis[i] = this;
                this.visIndex = i;
                break;
            }
        }
    }

    public void update() {
        lifetime--;
        if (lifetime <= 0) {
            gp.vis[visIndex] = null;
        }
    }    

    public void draw(Graphics2D g2) { //bör overridas för animationer med flera bilder
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
