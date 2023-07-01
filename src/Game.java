import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JPanel implements CameraShow {
    private GameOfLifeFrame instance;

    public Game(GameOfLifeFrame instance){
        this.instance = instance;

        //pour les clicks
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                instance.camera.click((float)e.getPoint().getX(),(float)e.getPoint().getY(),instance.game);
            }
        });
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
        g.setColor(instance.gameoflife.getPlateau()[x][y] ? Color.WHITE:Color.BLACK);
        g.fillRect(posX,posY,GameOfLifeFrame.CASE_WIDTH,GameOfLifeFrame.CASE_WIDTH);
        if(!instance.gameoflife.getPlateau()[x][y]){
            g.setColor(Color.WHITE);
            //g.drawRect(posX,posY,GameOfLifeFrame.CASE_WIDTH-1,GameOfLifeFrame.CASE_WIDTH-1);
        }
    }

    public void click(int tabX, int tabY){
        instance.gameoflife.getPlateau()[tabX][tabY] = !instance.gameoflife.getPlateau()[tabX][tabY];
        instance.game.repaint();
    }
}
