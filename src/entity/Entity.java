package entity;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class Entity{

    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public BufferedImage left, right;
    public String direction;

    public int animationTime = 0;
    public int jumpAnimation = 1;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
}