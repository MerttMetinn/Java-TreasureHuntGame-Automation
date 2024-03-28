package objects.moving;

import main.UnitSquareGrid;
import main.location;
import objects.Obstacles;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bee implements Obstacles {
    private int gridSize;
    private int beeSizeX = 2;
    private int beeSizeY = 6;
    private int beeCount = 2;
    private Timer timer;
    private JLabel[][] labels;

    public Bee(int gridSize) {
        this.gridSize = gridSize;
        labels = new JLabel[beeSizeX][beeSizeY];
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBeeImages();
            }
        });
    }

    ImageIcon beeIcon = new ImageIcon("src/img/32bee.gif");
    ImageIcon beeIcon2 = new ImageIcon("");
    ImageIcon beeIcon3 = new ImageIcon("");

    public void createBees() {
        Random random = new Random();
        UnitSquareGrid usg = new UnitSquareGrid();

        for (int i = 0; i < beeCount; i++) {
            int beeCornerX = Math.abs(random.nextInt(gridSize) - beeSizeX);
            int beeCornerY = Math.abs(random.nextInt(gridSize) - beeSizeY);
            if (isSpaceAvailable(beeCornerX, beeCornerY, beeSizeX)) {
                for (int j = 0; j < beeSizeX; j++) {
                    for (int k = 0; k < beeSizeY; k++) {
                        JPanel panel = usg.getSquarePanels()[beeCornerX + j][beeCornerY + k];
                        panel.setLayout(new BorderLayout());

                        if (j < 2 && k < 2) {
                            labels[j][k] = new JLabel(beeIcon);
                        }
                        else if (j > 4 && k > 4) {
                            labels[j][k] = new JLabel(beeIcon3);
                        }
                        else {
                            labels[j][k] = new JLabel(beeIcon2);
                        }

                        panel.add(labels[j][k], BorderLayout.CENTER);

                        location.objectCoordinates.add(new Point(beeCornerX + j, beeCornerY + k));
                        location.beeCoordinates.add(new Point(beeCornerX + j, beeCornerY + k));
                    }
                }
            } else {
                i--;
            }
        }

        timer.start();
    }

    private void updateBeeImages() {
        ImageIcon[][] nextIcons = new ImageIcon[beeSizeX][beeSizeY];

        for (int j = 0; j < beeSizeX; j++) {
            for (int k = 0; k < beeSizeY; k++) {
                JLabel label = labels[j][k];
                ImageIcon currentIcon = (ImageIcon) label.getIcon();
                nextIcons[j][k] = getNextBeeIcon(currentIcon);
            }
        }

        for (int j = 0; j < beeSizeX; j++) {
            for (int k = 0; k < beeSizeY; k++) {
                JLabel label = labels[j][k];
                label.setIcon(nextIcons[j][k]);
            }
        }

        for (int j = 0; j < beeSizeX; j++) {
            int nextX = (j + 1) % beeSizeX;
            JLabel currentLabel = labels[j][0];
            JLabel nextLabel = labels[nextX][0];

            ImageIcon currentIcon = (ImageIcon) currentLabel.getIcon();
            ImageIcon nextIcon = (ImageIcon) nextLabel.getIcon();

            currentLabel.setIcon(nextIcon);
            nextLabel.setIcon(currentIcon);
        }
    }

    private ImageIcon getNextBeeIcon(ImageIcon currentIcon) {
        if (currentIcon.equals(beeIcon)) {
            return beeIcon2;
        } else if (currentIcon.equals(beeIcon2)) {
            return beeIcon3;
        } else {
            return beeIcon;
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