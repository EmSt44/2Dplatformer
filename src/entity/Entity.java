package entity;

import java.awt.image.BufferedImage;

public class Entity{

    public int worldX, worldY;
    public int speed;

    public BufferedImage left, right;
    public String direction;

    public int animationTime = 0;
    public int jumpAnimation = 1;
}