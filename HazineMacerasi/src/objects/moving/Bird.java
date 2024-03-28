package objects.moving;

import main.UnitSquareGrid;
import main.location;
import objects.Obstacles;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Bird implements Obstacles {
    private int gridSize;
    public Bird(int gridSize) {
        this.gridSize = gridSize;
    }
    ImageIcon birdIcon = new ImageIcon("src/img/25bird.gif");
    public void createBirds() {
        int birdSizeX = 9;
        int birdSizeY = 2;
        int birdCount = 2;
        Random random = new Random();
        UnitSquareGrid usg = new UnitSquareGrid();
        int centerY = gridSize / 2;

        for (int i = 0; i < birdCount; i++) {
            int birdCornerX = Math.abs(random.nextInt(gridSize) - birdSizeX);
            int birdCornerY = Math.abs(random.nextInt(gridSize) - birdSizeY);
            if (isSpaceAvailable(birdCornerX, birdCornerY, birdSizeX)) {
                for (int j = 0; j < birdSizeX; j++) {
                    for (int k = 0; k < birdSizeY; k++) {
                        JPanel panel = usg.getSquarePanels()[birdCornerX + j][birdCornerY + k];
                        panel.setLayout(new BorderLayout());
                        JLabel label = new JLabel(birdIcon);
                        panel.add(label, BorderLayout.CENTER);
                        location.objectCoordinates.add(new Point(birdCornerX + j, birdCornerY + k));
                        location.birdCoordinates.add(new Point(birdCornerX + j, birdCornerY + k));
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
