package main;

import javax.swing.*;
import java.awt.*;

public class FogEffect {
    private int characterX;
    private int characterY;
    private UnitSquareGrid usg = new UnitSquareGrid();
    private static boolean isFogActive = false;

    public FogEffect(int characterX, int characterY, boolean isFogActive) {
        this.characterX = characterX;
        this.characterY = characterY;
        this.isFogActive = isFogActive;
    }

    public FogEffect(int characterX, int characterY) {
        this.characterX = characterX;
        this.characterY = characterY;
    }

    public void applyFogEffect(JPanel[][] squarePanels,int gridSize,int squareSize) {

        JPanel[][] fogPanel = new JPanel[gridSize][gridSize];

        for (int i = 0; i < squarePanels.length; i++) {
            for (int j = 0; j < squarePanels[i].length; j++) {
                if (Math.abs(i - characterX) > 3 || Math.abs(j - characterY) > 3) {
                    squarePanels[i][j].setBackground(new Color(128, 128, 128));
                }
                else {
                    if (j < squarePanels[i].length / 2) {
                        squarePanels[i][j].setBackground(new Color(144, 175, 196)); // Blue
                    } else {
                        squarePanels[i][j].setBackground(new Color(56, 182, 75)); // Green
                    }
                }
            }
        }
    }
    public void updateFog(int oldCharacterX, int oldCharacterY, int newCharacterX, int newCharacterY) {
        if (isFogActive) {
            boolean[][] isOpen = new boolean[usg.getSquarePanels().length][usg.getSquarePanels()[0].length];

            for (int i = Math.max(oldCharacterX - 3, 0); i <= Math.min(oldCharacterX + 3, usg.getSquarePanels().length - 1); i++) {
                for (int j = Math.max(oldCharacterY - 3, 0); j <= Math.min(oldCharacterY + 3, usg.getSquarePanels()[0].length - 1); j++) {
                    isOpen[i][j] = true;
                }
            }

            for (int i = Math.max(newCharacterX - 3, 0); i <= Math.min(newCharacterX + 3, usg.getSquarePanels().length - 1); i++) {
                for (int j = Math.max(newCharacterY - 3, 0); j <= Math.min(newCharacterY + 3, usg.getSquarePanels()[0].length - 1); j++) {
                    if (!isOpen[i][j]) {
                        if (j < usg.getSquarePanels()[i].length / 2) {
                            usg.getSquarePanels()[i][j].setBackground(new Color(144, 175, 196));
                        } else {
                            usg.getSquarePanels()[i][j].setBackground(new Color(56, 182, 75));
                        }
                    }
                }
            }
        }
    }
}
