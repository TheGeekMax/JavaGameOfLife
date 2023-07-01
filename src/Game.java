import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements CameraShow {
    private GameOfLifeFrame instance;

    public Game(GameOfLifeFrame instance){
        this.instance = instance;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameOfLifeFrame.GAME_WIDTH, GameOfLifeFrame.GAME_WIDTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.fillRect(0,0,GameOfLifeFrame.GAME_WIDTH,GameOfLifeFrame.GAME_WIDTH);
        instance.camera.showView(g,this);
    }

    public void showTile(Graphics g, int x, int y,int posX, int posY){
        g.setColor((x+y)%2 ==0 ? Color.WHITE:Color.BLACK);
        g.fillRect(posX,posY,GameOfLifeFrame.CASE_WIDTH,GameOfLifeFrame.CASE_WIDTH);
    }
}
