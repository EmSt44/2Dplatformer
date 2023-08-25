package main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Ninja Trials");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //gör fönstret till rätt storlek för vår panel

        window.setLocationRelativeTo(null); //ospecificerad window location = mitten av skärmen.
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}