package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;   
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    
    //Skärminställningar
    final int originalTileSize = 16; //16x16 texturer som standard
    final int scale = 3; //skala upp dem så det inte ser så litet ut på skärmen

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; //768 pixlar (16 48*48 tiles)
    public final int screenHeight = tileSize * maxScreenRow; //576 pixlar (12 48*48 tiles)

    //Världsinställningar
    public final int maxWorldCol = 50; //Ändra numret till vår egna kartas collumn
    public final int maxWorldRow = 50; //Ändra numret till vår egna kartas row
    public final int worldWidth = tileSize * maxScreenCol; //Change to this variable in mapTileNum, as well as in load map. All maxScreenCol to this variable.
    public final int worldHeight = tileSize * maxScreenRow; //Change to this variable in mapTileNum, as well as in load map. All maxScreenRow to this variable.
    //Video #5 11:32 changes needed to be done with draw to have a camera focused on the character


    //FPS
    int FPS = 60;

    //Instansiera klasser
    TileManager tileM = new TileManager(this); //skapar en tilemanager
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public Player player = new Player(this, keyH);

    //Gamestates
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame() {
        gameState = playState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

   
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        setupGame(); //sätter initella gamestaten

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //Buffering with real time to ensure the "character" doesnt disappear out of nowhere
            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            //FPS counter
            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        if(gameState == playState) {
            player.update();
        }
        else if(gameState == pauseState) {
            //gör inget, spelet är pausat
        }

    }
    public void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync();

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); // ritar upp en tile, viktigt: draw tiles innan player!

        player.draw(g2);

        g2.dispose();
    }
}
