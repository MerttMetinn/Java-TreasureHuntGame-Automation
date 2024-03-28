package objects.stationary;

import main.UnitSquareGrid;
import main.location;
import objects.Obstacles;
import theme.summer;
import theme.winter;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class mountain implements Obstacles {
    private int mountainCount;
    private int gridSize;
    private int mountainCornerX;
    private int mountainCornerY;
    private int mountainSize;
    public mountain(int gridSize) {
        this.gridSize = gridSize;
    }

    public void createMountains() {
        location.objectCoordinates.clear();
        Random random = new Random();
        UnitSquareGrid usg = new UnitSquareGrid();
        mountainCount = 2;
        mountainSize = 15;
        mountainCornerX = Math.abs(random.nextInt(gridSize) - mountainSize);
        mountainCornerY = Math.abs(random.nextInt(gridSize) - mountainSize);
        int centerY = gridSize / 2;
        summer summer = new summer();
        winter winter = new winter();

        for (int j = 0; j < mountainSize; j++) {
            for (int k = 0; k < mountainSize; k++) {
                JPanel panel = usg.getSquarePanels()[mountainCornerX + j][mountainCornerY + k];
                JPanel fogPanel = usg.getSquarePanels()[mountainCornerX + j][mountainCornerY + k];
                panel.setLayout(new BorderLayout());

                ImageIcon mountainIcon;
                if (mountainCornerY + k < centerY) {
                    mountainIcon = winter.getWinterMountainIcon();
                } else {
                    mountainIcon = summer.getSummerMountainIcon();
                }
                JLabel label = new JLabel(mountainIcon);
                panel.add(label, BorderLayout.CENTER);
                location.objectCoordinates.add(new Point(mountainCornerX + j, mountainCornerY + k));
                location.mountainCoordinates.add(new Point(mountainCornerX + j,mountainCornerY + k));
            }
        }

        for (int i = 0; i < mountainCount - 1; i++) {
            mountainCornerX = Math.abs(random.nextInt(gridSize) - mountainSize);
            mountainCornerY = Math.abs(random.nextInt(gridSize) - mountainSize);
            if (isSpaceAvailable(mountainCornerX, mountainCornerY, mountainSize)) {
                for (int j = 0; j < mountainSize; j++) {
                    for (int k = 0; k < mountainSize; k++) {
                        JPanel panel = usg.getSquarePanels()[mountainCornerX + j][mountainCornerY + k];
                        panel.setLayout(new BorderLayout());
                        ImageIcon mountainIcon;
                        if (mountainCornerY + k < centerY) {
                            mountainIcon = winter.getWinterMountainIcon();
                        } else {
                            mountainIcon = summer.getSummerMountainIcon();
                        }
                        JLabel label = new JLabel(mountainIcon);
                        panel.add(label, BorderLayout.CENTER);
                        location.objectCoordinates.add(new Point(mountainCornerX + j, mountainCornerY + k));
                        location.mountainCoordinates.add(new Point(mountainCornerX + j,mountainCornerY + k));
                    }
                }
            } else {
                i--;
            }

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
