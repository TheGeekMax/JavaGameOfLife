import javax.swing.*;
import java.awt.*;

public class Options extends JPanel {
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameOfLifeFrame.OPT_WIDTH, GameOfLifeFrame.GAME_WIDTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);
        g.fillRect(-100,0,GameOfLifeFrame.GAME_WIDTH,GameOfLifeFrame.GAME_WIDTH);
    }
}
