import kotlin.Pair;
import tools.Vector2Int;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Minimap extends JPanel {

    private GameOfLifeFrame instance;

    public Minimap(GameOfLifeFrame instance){
        this.instance = instance;

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameOfLifeFrame.OPT_WIDTH, GameOfLifeFrame.OPT_WIDTH);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for(int i = 0; i < GameOfLifeFrame.TILEMAP_WIDTH; i ++){
            for(int j = 0; j < GameOfLifeFrame.TILEMAP_HEIGHT; j ++){
                if ((instance.gameoflife.getPlateau()[i][j])) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(i,j,1,1);
            }
        }

        //bordure de la camera
        g.setColor(Color.red);
        Pair values = instance.camera.getBoundCoors();
        Vector2Int min = (Vector2Int) values.getFirst();
        Vector2Int max = (Vector2Int) values.getSecond();

        g.drawRect(min.getX(),min.getY(),max.getX()-min.getX(),max.getY()-min.getY());
    }
}
