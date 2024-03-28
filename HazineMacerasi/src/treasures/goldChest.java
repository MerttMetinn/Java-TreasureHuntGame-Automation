package treasures;

import main.UnitSquareGrid;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class goldChest extends Chest implements IntChest{
    private int gridSize;
    public goldChest(int gridSize) {
        this.gridSize = gridSize;
    }
    ImageIcon goldChestIcon = new ImageIcon("src/img/35goldenchest.png");
    @Override
    public void createChests() {
        UnitSquareGrid usg = new UnitSquareGrid();
        Random random = new Random();
        int chestCount = 0;

        while(chestCount != 5)
        {
            int chestX = Math.abs(random.nextInt(gridSize) - 1);
            int chestY = Math.abs(random.nextInt(gridSize) - 1);

            if(isSpaceAvailable(chestX,chestY))
            {
                JPanel panel = usg.getSquarePanels()[chestX][chestY];
                panel.setLayout(new BorderLayout());
                JLabel label = new JLabel(goldChestIcon);
                panel.add(label,BorderLayout.CENTER);

                chestCoordinates.add(new Point(chestX,chestY));
                goldChestCoordinates.add(new Point(chestX,chestY));
                chestCount++;
            }
        }
    }
}