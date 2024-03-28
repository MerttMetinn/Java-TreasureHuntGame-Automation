package objects.stationary;

import main.UnitSquareGrid;
import main.location;
import objects.Obstacles;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Wall implements Obstacles {
    private int gridSize;

    public Wall(int gridSize)
    {
        this.gridSize = gridSize;
    }
    ImageIcon wallIcon = new ImageIcon("src/img/40wall.png");
    JLabel wallLabel = new JLabel(wallIcon);
    public void createWalls() {
        Random random = new Random();
        UnitSquareGrid usg = new UnitSquareGrid();

        int verticalWallX = Math.abs(random.nextInt(gridSize - 10));
        int verticalWallY = Math.abs(random.nextInt(gridSize - 10));

        while (true) {
            if (isSpaceAvailable(verticalWallX, verticalWallY, 0)) {
                for (int i = 0; i < 10; i++) {
                    JPanel panel = usg.getSquarePanels()[verticalWallX][verticalWallY + i];
                    panel.setLayout(new BorderLayout());
                    JLabel label = new JLabel(wallIcon);
                    panel.add(label, BorderLayout.CENTER);
                    location.objectCoordinates.add(new Point(verticalWallX, verticalWallY + i));
                    location.wallCoordinates.add(new Point(verticalWallX,verticalWallY));
                }
                break;
            } else {
                verticalWallX = Math.abs(random.nextInt(gridSize - 10));
                verticalWallY = Math.abs(random.nextInt(gridSize - 10));
            }
        }

        int horizontalWallX = Math.abs(random.nextInt(gridSize - 10));
        int horizontalWallY = Math.abs(random.nextInt(gridSize - 10));

        while (true) {
            if (isSpaceAvailable(horizontalWallX, horizontalWallY, 1)) {
                for (int i = 0; i < 10; i++) {

                    JPanel panel = usg.getSquarePanels()[horizontalWallX + i][horizontalWallY];
                    panel.setLayout(new BorderLayout());
                    JLabel label = new JLabel(wallIcon);
                    panel.add(label, BorderLayout.CENTER);
                    location.objectCoordinates.add(new Point(horizontalWallX + i, horizontalWallY));
                    location.wallCoordinates.add(new Point(horizontalWallX + i,horizontalWallY));
                }
                break;
            } else {
                horizontalWallX = Math.abs(random.nextInt(gridSize - 10));
                horizontalWallY = Math.abs(random.nextInt(gridSize - 10));
            }
        }
    }
    @Override
    public boolean isSpaceAvailable(int x, int y, int size) {
        int gap = 1;
        boolean kontrol = true;
        switch (size) {
            case 0:
            {
                for (int i = y - gap; i < y + 10 + gap; i++) {
                    if (location.objectCoordinates.contains(new Point(x, i))) {
                        kontrol = false;
                        break;
                    }
                }
            }
            break;
            case 1:
            {
                for (int i = x - gap; i < x + 10 + gap; i++) {
                    if (location.objectCoordinates.contains(new Point(i, y))) {
                        kontrol = false;
                        break;
                    }
                }
            }
            break;
        }
        return kontrol;
    }
}