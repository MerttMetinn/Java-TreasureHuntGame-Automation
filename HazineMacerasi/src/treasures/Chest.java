package treasures;

import main.UnitSquareGrid;
import main.location;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Chest {
    public static ArrayList<Point> chestCoordinates = new ArrayList<>();
    public static ArrayList<Point> goldChestCoordinates = new ArrayList<>();
    public static ArrayList<Point> silverChestCoordinates = new ArrayList<>();
    public static ArrayList<Point> copperChestCoordinates = new ArrayList<>();
    public static ArrayList<Point> emeraldChestCoordinates = new ArrayList<>();

    public void initiateChests(int gridSize) {
        copperChest copperChest = new copperChest(gridSize);
        copperChest.createChests();

        silverChest silverChest = new silverChest(gridSize);
        silverChest.createChests();

        goldChest goldChest = new goldChest(gridSize);
        goldChest.createChests();

        emeraldChest emeraldChest = new emeraldChest(gridSize);
        emeraldChest.createChests();
    }

    public boolean isSpaceAvailable(int x, int y) {
        for (int i = 0; i < 3; i++) {
            if (location.objectCoordinates.contains(new Point(x + i - 1, y + i - 1))) {
                return false;
            }
        }
        return true;
    }

    public boolean chestControl(Point p) {
        boolean control = false;
        if (chestCoordinates.contains(p)) {
            control = true;
        }
        return control;
    }

    public void removeChest(int x, int y) {
        Point chestPoint = new Point(x, y);
        if (silverChestCoordinates.contains(chestPoint)) {
            silverChestCoordinates.remove(chestPoint);
            JOptionPane.showMessageDialog(null, "Silver Chest is removed at " + x + "," + y);
            writeToTXT(chestPoint, "Silver");
        } else if (goldChestCoordinates.contains(chestPoint)) {
            goldChestCoordinates.remove(chestPoint);
            JOptionPane.showMessageDialog(null, "Gold Chest is removed at " + x + "," + y);
            writeToTXT(chestPoint, "Gold");
        } else if (copperChestCoordinates.contains(chestPoint)) {
            copperChestCoordinates.remove(chestPoint);
            JOptionPane.showMessageDialog(null, "Copper Chest is removed at " + x + "," + y);
            writeToTXT(chestPoint, "Copper");
        } else if (emeraldChestCoordinates.contains(chestPoint)) {
            emeraldChestCoordinates.remove(chestPoint);
            JOptionPane.showMessageDialog(null, "Emerald Chest is removed at " + x + "," + y);
            writeToTXT(chestPoint, "Emerald");
        }
        chestCoordinates.remove(chestPoint);

        UnitSquareGrid usg = new UnitSquareGrid();
        JPanel panel = usg.getSquarePanels()[x][y];
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    public void writeToTXT(Point p, String chestType) {
        try {
            FileWriter writer = new FileWriter("bilgi.txt", true);
            writer.write(chestType + "Chest bulundu! Koordinatları : (" + p.x + "," + p.y + ")\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }
}
