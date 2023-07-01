import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameOfLifeFrame extends JFrame {

    public static final int GAME_WIDTH = 600;
    public static final int OPT_WIDTH = 300;

    public static final int CASE_WIDTH = 30;
    public static final int TILEMAP_WIDTH = 100;
    public static final int TILEMAP_HEIGHT = 100;

    public static final int PLAYER_SPEED = 5;

    private boolean upPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;


    private int dx =0;
    private int dy = 0;

    public Camera camera= new Camera(CASE_WIDTH,TILEMAP_WIDTH,TILEMAP_HEIGHT,GAME_WIDTH,GAME_WIDTH);
    public Game game = new Game(this);

    public GameOfLifeFrame(){
        this.setTitle("GameOfLife");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);

        this.add(game, BorderLayout.CENTER);
        this.add(new Options(), BorderLayout.EAST);
        this.pack();
        this.setResizable(false);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            private int clamppm1(int v){
                if(v >=1)
                    return 1;
                if(v <= 1)
                    return -1;
                return 0;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_Z:
                        upPressed = true;
                        break;

                    case KeyEvent.VK_S:
                        downPressed = true;
                        break;

                    case KeyEvent.VK_Q:
                        leftPressed = true;
                        break;

                    case KeyEvent.VK_D:
                        rightPressed = true;
                        break;
                }
                calculateDc();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_Z:
                        upPressed = false;
                        break;

                    case KeyEvent.VK_S:
                        downPressed = false;
                        break;

                    case KeyEvent.VK_Q:
                        leftPressed = false;
                        break;

                    case KeyEvent.VK_D:
                        rightPressed = false;
                        break;
                }
                calculateDc();
            }
        });

        mainLoop();
    }

    private void calculateDc(){
        dx = 0;
        dy = 0;

        dy -= upPressed?1:0;
        dy += downPressed?1:0;
        dx -= leftPressed?1:0;
        dx += rightPressed?1:0;
    }

    private void mainLoop(){
        while(true) {
            if (dx != 0 || dy != 0) {
                camera.updateCoors(PLAYER_SPEED*dx,PLAYER_SPEED*dy);
                game.repaint();
            }

            try{
                Thread.sleep(10);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(dx + ":"+dy);
        }
    }

    public static void main(String[] args) {
        new GameOfLifeFrame();
    }
}