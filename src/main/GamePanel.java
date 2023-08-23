package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;   
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.*;
import java.lang.Integer;
import javax.swing.JPanel;

import entity.*;
import tile.TileManager;
import visual.*;
import object.*;

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

    //KeyHandler
    public KeyHandler keyH = new KeyHandler(this);

    //Entity, objekt, etc
    public Player player = new Player(this, keyH);
    public Entity npc[] = new Entity[10]; //maximal mängd olika NPC. Öka siffran för att ändra.
    public Entity obj[] = new Entity[10]; //maximal mängd olika Objekt du kan ha på mappen.
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    public VisualEffect vis[] = new VisualEffect[10]; //maximal mängd olika visuella effekter (partiklar etc) som pågår på en gång

    //TileManager, KeyHandler, liknande managers
    TileManager tileM = new TileManager(this);
    AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker cChecker = new CollisionChecker(this);

    //UI
    UI ui = new UI(this);

    //FPS
    int FPS = 60;

    Thread gameThread;
    
    //Set player's default position
    int playerX = 95;
    int playerY = 95;
    int playerSpeed = 4;

    //Gamestates
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    //Väljer initiell gameState
    public void setupGame() {
        gameState = menuState;
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
            }

            //FPS counter
            if(timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                timer = 0;
            }
        }
    }
    public void update() {        
        
        if(gameState == playState) {

            if(player.life <= 0) {
                gameState = gameOverState;
            }
            else {
                player.update();
            
                for(int i = 0; i < npc.length; i++) { //uppdatera alla NPC
                    if (npc[i] != null) {
                        npc[i].update();
                    }
                 }
            }

            // Uppdatera visuella effekter (minska lifetime)
            for(int i = 0; i < vis.length; i++) {
                if (vis[i] != null) {
                vis[i].update();
                }
            }

            for(int i = 0; i < projectileList.size(); i++){
                if(projectileList.get(i) != null) {
                    if(projectileList.get(i).life > 0) {
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).life == 0) {
                        projectileList.remove(i);
                    }
                }
            }
        } else if(gameState == pauseState) { //spelet är pausat
            //gör inget
        }
        else if(gameState == gameOverState) { //spelaren har dött
            //gör inget spelet är över
        }
    }
    
    public void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync();

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //MenuState
        if(gameState == menuState) {
            ui.draw(g2);
        }
        //Andra states
        else {
            // Ritar TILES, viktigt: draw tiles innan player!
            tileM.draw(g2); 

            // Lägg till entities till entitylistan
            entityList.add(player);

            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            // Sortera
            Collections.sort(entityList, new Comparator<Entity>(){

                @Override
                public int compare(Entity e1, Entity e2) {
                    
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //Rita entiteter
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //Tom entity lista
            entityList.clear();

            // Ritar NPC
            for(int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                npc[i].draw(g2);
                }
            }

            // Ritar visuella effekter
            for(int i = 0; i < vis.length; i++) {
                if (vis[i] != null) {
                vis[i].draw(g2);
                }
            }

            //rita UI
            ui.draw(g2);

            g2.dispose();

        } 
    }    
}
