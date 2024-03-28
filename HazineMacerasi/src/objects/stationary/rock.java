package objects.stationary;

import main.UnitSquareGrid;
import main.location;
import objects.Obstacles;
import theme.summer;
import theme.winter;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class rock implements Obstacles {
    private int rockCount;
    private int gridSize;
    private int rockCornerX;
    private int rockCornerY;

    public rock(int gridSize) {
        this.gridSize = gridSize;
    }

    public void createRocks() {
        int rockSize = 2;
        int rockCount = 3;
        Random random = new Random();
        UnitSquareGrid usg = new UnitSquareGrid();
        int centerY = gridSize / 2;
        summer summer = new summer();
        winter winter = new winter();

        for (int a = 0; a < 3; a++) {
            for (int i = 0; i < rockCount; i++) {
                int rockCornerX = Math.abs(random.nextInt(gridSize) - rockSize);
                int rockCornerY = Math.abs(random.nextInt(gridSize) - rockSize);
                if (isSpaceAvailable(rockCornerX, rockCornerY, rockSize)) {
                    for (int j = 0; j < rockSize; j++) {
                        for (int k = 0; k < rockSize; k++) {
                            JPanel panel = usg.getSquarePanels()[rockCornerX + j][rockCornerY + k];
                            panel.setLayout(new BorderLayout());

                            ImageIcon rockIcon;
                            if (rockCornerY + k < centerY) {
                                rockIcon = winter.getWinterRockIcon();
                            } else {
                                rockIcon = summer.getSummerRockIcon();
                            }

                            JLabel label = new JLabel(rockIcon);
                            panel.add(label, BorderLayout.CENTER);
                            location.objectCoordinates.add(new Point(rockCornerX + j, rockCornerY + k));
                            location.rockCoordinates.add(new Point(rockCornerX + j,rockCornerY + k));
                        }
                    }
                } else {
                    i--;
                }
            }
            rockSize++;
        }
    }
    @Override
    public boolean isSpaceAvailable(int x, int y, int size) {
        int gap = 1;
        for (int i = x - gap; i < x + size + gap; i++) {
            for (int j = y - gap; j < y + size + gap; j++) {
                if (location.objectCoordinates.contains(new Point(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
