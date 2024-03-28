package objects;

import main.UnitSquareGrid;
import main.location;
import objects.moving.Bird;
import objects.moving.Bee;
import objects.stationary.Wall;
import objects.stationary.mountain;
import objects.stationary.rock;
import objects.stationary.tree;
import treasures.Chest;

import javax.swing.*;
import java.awt.*;

public class Obstacle extends JFrame {
    private int gridSize;
    public Obstacle(int gridSize)
    {
        this.gridSize = gridSize;
    }
    public void createObstacles(int gridSize)
    {
        mountain mountain = new mountain(gridSize);
        mountain.createMountains();

        rock rock = new rock(gridSize);
        rock.createRocks();

        tree tree = new tree(gridSize);
        tree.createTrees();

        Wall Wall = new Wall(gridSize);
        Wall.createWalls();

        Chest chest = new Chest();
        chest.initiateChests(gridSize);

        Bird bird = new Bird(gridSize);
        bird.createBirds();

        Bee bee = new Bee(gridSize);
        bee.createBees();
    }

    public void checkBGColor() {
        UnitSquareGrid usg = new UnitSquareGrid();
        for (Point p : location.objectCoordinates) {
            JPanel panel = usg.getSquarePanels()[p.x][p.y];
            JPanel fogPanel = new JPanel();
            fogPanel.setLocation(p);
            if (!(panel.getBackground().equals(new Color(128, 128, 128)))) {
                panel.setVisible(true);
            }
            else
            {
                panel.setVisible(false);
                fogPanel.setBackground(new Color(128,128,128));
            }
        }
        for(Point p : Chest.chestCoordinates)
        {
            JPanel panel = usg.getSquarePanels()[p.x][p.y];
            JPanel fogPanel = new JPanel();
            fogPanel.setLocation(p);
            if (!(panel.getBackground().equals(new Color(128, 128, 128)))) {
                panel.setVisible(true);
            }
            else
            {
                panel.setVisible(false);
                fogPanel.setBackground(new Color(128,128,128));
            }
        }
    }
}
