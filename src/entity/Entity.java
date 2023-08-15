package entity;

import java.awt.image.BufferedImage;

public class Entity{

    public int worldX, worldY;
    
    // Ta bort senare
    public int x, y;
    public int speed;

    public BufferedImage left, right;
    public String direction;

    public int animationTime = 0;
    public int jumpAnimation = 1;
}