package main;
import objects.stationary.*;
import treasures.*;
import theme.*;

import javax.swing.*;

public class StartGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UnitSquareGrid grid = new UnitSquareGrid();
            grid.setVisible(true);
        });
    }
}