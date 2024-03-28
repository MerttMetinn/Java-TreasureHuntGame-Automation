package treasures;

import main.UnitSquareGrid;
import main.location;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;
public class copperChest extends Chest implements IntChest{
    private int gridSize;
    public copperChest(int gridSize) {
        this.gridSize = gridSize;
    }
    ImageIcon copperChestIcon = new ImageIcon("src/img/35copperchest.png");
    @Override
    public void createChests() {
        UnitSquareGrid usg = new UnitSquareGrid();
        Random random = new Random();
        int chestCount = 0;

        while(chestCount != 5)
        {
            int chestX = Math.abs(random.nextInt(gridSize) - 1);
            int chestY = Math.abs(random.nextInt(gridSize) - 1);

            if(isSpaceAvailable(chestX,chestY)) {
                JPanel panel = usg.getSquarePanels()[chestX][chestY];
                panel.setLayout(new BorderLayout());
                JLabel label = new JLabel(copperChestIcon);
                panel.add(label,BorderLayout.CENTER);

                chestCoordinates.add(new Point(chestX,chestY));
                copperChestCoordinates.add(new Point(chestX,chestY));
                chestCount++;
            }
        }
    }
}