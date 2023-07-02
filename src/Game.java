import camera.CameraShow;
import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Game extends JPanel implements CameraShow {
    private GameOfLifeFrame instance;

    private boolean mousePressed = false;
    private boolean clickType = false;

    public Game(GameOfLifeFrame instance){
        this.instance = instance;

        //pour les clicks
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Pair value =  instance.camera.click((float)e.getPoint().getX(),(float)e.getPoint().getY(),instance.game);
                clickType = instance.gameoflife.getPlateau()[(int)value.getFirst()][(int)value.getSecond()];
                mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mousePressed = false;
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Pair value =  instance.camera.canvasToGlobalcoors((float)e.getPoint().getX(),(float)e.getPoint().getY());
                instance.gameoflife.getPlateau()[(int)value.getFirst()][(int)value.getSecond()] = clickType;
                instance.game.repaint();
                instance.option.minimap.repaint();
            }
        });

        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                instance.camera.zoom(e.getWheelRotation());
                instance.caseWidth += e.getWheelRotation();
                instance.game.repaint();
                instance.option.minimap.repaint();
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
        g.fillRect(posX,posY,instance.caseWidth,instance.caseWidth);
        if(!instance.gameoflife.getPlateau()[x][y]){
            g.setColor(Color.WHITE);
            if(instance.gridShow) g.drawRect(posX,posY,instance.caseWidth -1,instance.caseWidth -1);
        }
    }

    public void click(int tabX, int tabY){
        instance.gameoflife.getPlateau()[tabX][tabY] = !instance.gameoflife.getPlateau()[tabX][tabY];
        instance.game.repaint();
        instance.option.minimap.repaint();
    }
}
