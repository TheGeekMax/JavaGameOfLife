import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLifeFrame extends JFrame {

    public static final int GAME_WIDTH = 600;
    public static final int OPT_WIDTH = 300;

    public static final int CASE_WIDTH = 5;
    public static final int TILEMAP_WIDTH = 400;
    public static final int TILEMAP_HEIGHT = 400;

    public static final int PLAYER_SPEED = 5;

    public KeyMovement camMovement= new KeyMovement(KeyEvent.VK_Z,KeyEvent.VK_Q,KeyEvent.VK_S,KeyEvent.VK_D);

    public GameOfLife gameoflife;

    public Camera camera= new Camera(CASE_WIDTH,TILEMAP_WIDTH,TILEMAP_HEIGHT,GAME_WIDTH,GAME_WIDTH);
    public Game game = new Game(this);

    private boolean playing = false;
    private int frameCount = 0;

    public GameOfLifeFrame(){
        boolean[][] plateau = new boolean[TILEMAP_WIDTH][TILEMAP_HEIGHT];
        //on init le tableau
        for(int i = 0; i < TILEMAP_WIDTH; i ++){
            for(int j = 0; j < TILEMAP_WIDTH; j ++){
                plateau[i][j] = false;
            }
        }
        gameoflife = new GameOfLife(plateau);



        //init de la fenetre
        this.setTitle("GameOfLife");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);

        this.add(game, BorderLayout.CENTER);
        this.add(new Options(), BorderLayout.EAST);
        this.pack();
        this.setResizable(false);

        //pour le mouvement
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                camMovement.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    playing = !playing;
                }else if(e.getKeyCode() == KeyEvent.VK_R){
                    for(int i = 0 ; i < TILEMAP_WIDTH; i ++){
                        for(int j = 0 ; j < TILEMAP_HEIGHT; j ++){
                            plateau[i][j] = Math.random()>.5f;
                        }
                    }
                }else if(e.getKeyCode() == KeyEvent.VK_B){
                    for(int i = 0 ; i < TILEMAP_WIDTH; i ++){
                        for(int j = 0 ; j < TILEMAP_HEIGHT; j ++){
                            plateau[i][j] = false;
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                camMovement.keyReleased(e);
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
            }
            frameCount++;
            //System.out.println(dx + ":"+dy);
        }
    }

    public static void main(String[] args) {
        new GameOfLifeFrame();
    }
}