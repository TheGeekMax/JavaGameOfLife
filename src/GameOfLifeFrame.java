import camera.Camera;
import tools.KeyManager;
import tools.KeyMovement;
import tools.Vector2Int;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLifeFrame extends JFrame {
    private int frameCount = 0;

    public static final int GAME_WIDTH = 600;
    public static final int OPT_WIDTH = 400;


    public static final int TILEMAP_WIDTH = 400;
    public static final int TILEMAP_HEIGHT = 400;

    public static final int PLAYER_SPEED = 5;

    public int caseWidth = 20;

    public KeyMovement camMovement= new KeyMovement(KeyEvent.VK_Z,KeyEvent.VK_Q,KeyEvent.VK_S,KeyEvent.VK_D);

    public GameOfLife gameoflife;

    public Camera camera= new Camera(caseWidth,TILEMAP_WIDTH,TILEMAP_HEIGHT,GAME_WIDTH,GAME_WIDTH);
    public Game game = new Game(this);
    public Options option = new Options(this);
    public KeyManager keymanager = new KeyManager();

    public boolean playing = false;
    public boolean gridShow= true;


    public GameOfLifeFrame(){
        boolean[][] plateau = new boolean[TILEMAP_WIDTH][TILEMAP_HEIGHT];
        //on init le tableau
        for(int i = 0; i < TILEMAP_WIDTH; i ++){
            for(int j = 0; j < TILEMAP_WIDTH; j ++){
                plateau[i][j] = false;
            }
        }
        gameoflife = new GameOfLife(plateau);

        keymanager.addButton(KeyEvent.VK_SPACE,"play");
        keymanager.addButton(KeyEvent.VK_R,"random");
        keymanager.addButton(KeyEvent.VK_B,"reset");
        keymanager.addButton(KeyEvent.VK_G,"show grid");

        //init de la fenetre
        this.setTitle("GameOfLife");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);

        this.add(game, BorderLayout.CENTER);
        this.add(option, BorderLayout.EAST);
        this.pack();
        this.setResizable(false);

        //pour le mouvement
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                camMovement.keyPressed(e);
                keymanager.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                camMovement.keyReleased(e);
                keymanager.keyReleased(e);
            }
        });

        mainLoop();
    }

    private void mainLoop(){
        while(true) {
            Vector2Int currentCameraMovement = camMovement.getDirrection();
            if (currentCameraMovement.getX() != 0 || currentCameraMovement.getY() != 0) {
                camera.updateCoors(PLAYER_SPEED*currentCameraMovement.getX(),PLAYER_SPEED*currentCameraMovement.getY());
                game.repaint();
                option.minimap.repaint();
            }

            try{
                Thread.sleep(10);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }

            if(playing && frameCount >= 5){
                frameCount = -1;
                gameoflife.iteration();
                game.repaint();
                option.minimap.repaint();
            }
            frameCount++;
            //System.out.println(dx + ":"+dy);
            if(keymanager.activable("play")){
                playing = !playing;
            }
            if(keymanager.activable("random")){
                for(int i = 0 ; i < TILEMAP_WIDTH; i ++){
                    for(int j = 0 ; j < TILEMAP_HEIGHT; j ++){
                        gameoflife.getPlateau()[i][j] = Math.random()>.5f;
                    }
                }
                game.repaint();
                option.minimap.repaint();
            }
            if(keymanager.activable("reset")){
                for(int i = 0 ; i < TILEMAP_WIDTH; i ++){
                    for(int j = 0 ; j < TILEMAP_HEIGHT; j ++){
                        gameoflife.getPlateau()[i][j] = false;
                    }
                }
                game.repaint();
                option.minimap.repaint();
            }
            if(keymanager.activable("show grid")){
                gridShow = !gridShow;
                game.repaint();
                option.minimap.repaint();
            }
        }
    }

    public static void main(String[] args) {
        new GameOfLifeFrame();
    }
}