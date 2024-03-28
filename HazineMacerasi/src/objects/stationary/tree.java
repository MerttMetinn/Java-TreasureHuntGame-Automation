package objects.stationary;

import main.UnitSquareGrid;
import main.location;
import objects.Obstacles;
import theme.summer;
import theme.winter;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class tree implements Obstacles {
    private int gridSize;
    public tree(int gridSize)
    {
        this.gridSize = gridSize;
    }

    public void createTrees() {
        int treeSize = 2;
        int treeCount = 3;
        Random random = new Random();
        UnitSquareGrid usg = new UnitSquareGrid();
        int centerY = gridSize / 2;
        summer summer = new summer();
        winter winter = new winter();

        for (int a = 0; a < 4; a++) {
            for (int i = 0; i < treeCount; i++) {
                int treeCornerX = Math.abs(random.nextInt(gridSize) - treeSize);
                int treeCornerY = Math.abs(random.nextInt(gridSize) - treeSize);
                if (isSpaceAvailable(treeCornerX, treeCornerY, treeSize)) {
                    for (int j = 0; j < treeSize; j++) {
                        for (int k = 0; k < treeSize; k++) {
                            JPanel panel = usg.getSquarePanels()[treeCornerX + j][treeCornerY + k];
                            panel.setLayout(new BorderLayout());

                            ImageIcon treeIcon;
                            if (treeCornerY + k < centerY) {
                                treeIcon = winter.getWinterTreeIcon();
                            } else {
                                treeIcon = summer.getSummerTreeIcon();
                            }

                            JLabel label = new JLabel(treeIcon);
                            panel.add(label, BorderLayout.CENTER);
                            location.objectCoordinates.add(new Point(treeCornerX + j, treeCornerY + k));
                            location.treeCoordinates.add(new Point(treeCornerX + j,treeCornerY + k));
                        }
                    }
                } else {
                    i--;
                }
            }
            treeSize++;
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
