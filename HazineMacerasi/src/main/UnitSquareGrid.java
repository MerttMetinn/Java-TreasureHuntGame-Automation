package main;

import objects.Obstacle;
import objects.stationary.*;
import treasures.Chest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static treasures.Chest.chestCoordinates;

public class UnitSquareGrid extends JFrame {
    private JTextField sizeField;
    private JButton createButton;
    private JPanel gridPanel;
    private JScrollPane gridScrollPane;
    private static JPanel[][] squarePanels;
    private static int gridSize;
    private int squareSize;
    private JLabel characterLabel;
    private int characterX;
    private int characterY;
    private Timer moveTimer;

    public UnitSquareGrid() {
        setTitle("Unit Square Grid");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);

        JPanel sizePanel = new JPanel();
        JLabel sizeLabel = new JLabel("Boyut: ");
        sizeField = new JTextField(10);
        createButton = new JButton("Oluştur");
        createButton.addActionListener(e -> createUnitSquareGrid());

        JButton fogButton = new JButton("Create Fog");
        fogButton.addActionListener(e -> createFogEffect());
        sizePanel.add(fogButton);

        sizePanel.add(sizeLabel);
        sizePanel.add(sizeField);
        sizePanel.add(createButton);

        gridPanel = new JPanel();
        gridScrollPane = new JScrollPane(gridPanel);
        gridScrollPane.setPreferredSize(new Dimension(800, 800));

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(sizePanel, BorderLayout.NORTH);
        container.add(gridScrollPane, BorderLayout.CENTER);
    }
    private boolean isKeyListenerActive = false;
    private void createUnitSquareGrid() {
        Random random = new Random();
        gridSize = Integer.parseInt(sizeField.getText());
        squareSize = 500 / Math.min(gridSize, 15);

        gridPanel.removeAll();

        gridPanel.setLayout(new GridLayout(gridSize, gridSize));
        squarePanels = new JPanel[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                square.setPreferredSize(new Dimension(squareSize, squareSize));

                if (j < gridSize / 2) {
                    square.setBackground(new Color(144, 175, 196));
                } else {
                    square.setBackground(new Color(56, 182, 75));
                }

                gridPanel.add(square);
                squarePanels[i][j] = square;
            }
        }

        Obstacle obstacle = new Obstacle(gridSize);
        obstacle.createObstacles(gridSize);

        ImageIcon characterIcon = new ImageIcon("src/img/kirby-walk.gif");
        characterLabel = new JLabel();
        characterLabel.setIcon(characterIcon);
        while (true) {
            characterX = random.nextInt(gridSize);
            characterY = random.nextInt(gridSize);
            if (!location.objectCoordinates.contains(new Point(characterX, characterY))) {
                break;
            }
        }
        squarePanels[characterX][characterY].add(characterLabel);

        gridPanel.setFocusable(true);
        gridPanel.requestFocus();
        if (!isKeyListenerActive) {
            gridPanel.addKeyListener(new KeyListener() {
                @Override
                public void keyPressed(KeyEvent e) {
                    moveCharacter(e.getKeyCode());
                }

                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
            isKeyListenerActive = true;
        }

        JScrollBar verticalScrollBar = gridScrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = gridScrollPane.getHorizontalScrollBar();

        gridScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gridScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        verticalScrollBar.setUnitIncrement(10);
        horizontalScrollBar.setUnitIncrement(10);

        pack();
        gridPanel.revalidate();
        gridPanel.repaint();
    }
    private void moveCharacter(int arrowKeyCode) {
        int newCharacterX = characterX;
        int newCharacterY = characterY;
        Point p = new Point(newCharacterX, newCharacterY);
        Chest chest = new Chest();
        FogEffect fogEffect = new FogEffect(newCharacterX, newCharacterY);
        Obstacle obstacle = new Obstacle(gridSize);
        obstacle.checkBGColor();

        switch (arrowKeyCode) {
            case KeyEvent.VK_W:
                if(!(location.objectCoordinates.contains(new Point(newCharacterX - 1,newCharacterY))))
                {
                    if(chest.chestControl(new Point(newCharacterX - 1,newCharacterY)))
                    {
                        chest.removeChest(newCharacterX - 1,newCharacterY);
                    }
                    newCharacterX--;
                }
                break;
            case KeyEvent.VK_S:
                if(!(location.objectCoordinates.contains(new Point(newCharacterX + 1,newCharacterY))))
                {
                    if(chest.chestControl(new Point(newCharacterX + 1,newCharacterY)))
                    {
                        chest.removeChest(newCharacterX + 1,newCharacterY);
                    }
                    newCharacterX++;
                }
                break;
            case KeyEvent.VK_A:
                if(!(location.objectCoordinates.contains(new Point(newCharacterX,newCharacterY - 1))))
                {
                    if(chest.chestControl(new Point(newCharacterX,newCharacterY - 1)))
                    {
                        chest.removeChest(newCharacterX,newCharacterY - 1);
                    }
                    newCharacterY--;
                }
                break;
            case KeyEvent.VK_D:
                if(!(location.objectCoordinates.contains(new Point(newCharacterX,newCharacterY + 1))))
                {
                    if(chest.chestControl(new Point(newCharacterX,newCharacterY + 1)))
                    {
                        chest.removeChest(newCharacterX,newCharacterY + 1);
                    }
                    newCharacterY++;
                }
                break;
            default:
                return;
        }

        fogEffect.updateFog (characterX, characterY, newCharacterX, newCharacterY);

        if (newCharacterX < 0 || newCharacterX >= gridSize || newCharacterY < 0 || newCharacterY >= gridSize) {
            return;
        }

        squarePanels[characterX][characterY].remove(characterLabel);
        squarePanels[characterX][characterY].repaint();

        squarePanels[newCharacterX][newCharacterY].add(characterLabel);
        squarePanels[newCharacterX][newCharacterY].repaint();

        characterX = newCharacterX;
        characterY = newCharacterY;

        int offsetX = squarePanels[characterX][characterY].getLocation().x - gridScrollPane.getWidth() / 2;
        int offsetY = squarePanels[characterX][characterY].getLocation().y - gridScrollPane.getHeight() / 2;

        offsetX = Math.min(Math.max(offsetX, 0), gridPanel.getWidth() - gridScrollPane.getWidth());
        offsetY = Math.min(Math.max(offsetY, 0), gridPanel.getHeight() - gridScrollPane.getHeight());

        gridScrollPane.getViewport().setViewPosition(new Point(offsetX, offsetY));
    }
    private void moveCharacterRandomly() {
        Random random = new Random();
        int direction = -1;
        boolean chestFound = false;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int checkX = characterX + i;
                int checkY = characterY + j;
                if (checkX >= 0 && checkX < gridSize && checkY >= 0 && checkY < gridSize) {
                    if (chestCoordinates.contains(new Point(checkX, checkY))) {
                        direction = getDirection(i, j);
                        chestFound = true;
                    }

                    if (location.objectCoordinates.contains(new Point(checkX, checkY))) {
                        if (i == -1) direction = 2;
                        else if (i == 1) direction = 3;
                        else if (j == -1) direction = 1;
                        else if (j == 1) direction = 0;
                    }
                }
                if (chestFound) break;
            }
            if (chestFound) break;
        }

        if (!chestFound) {
            direction = random.nextInt(4);
        }

        switch (direction) {
            case 0:
                moveCharacter(KeyEvent.VK_W);
                break;
            case 1:
                moveCharacter(KeyEvent.VK_S);
                break;
            case 2:
                moveCharacter(KeyEvent.VK_A);
                break;
            case 3:
                moveCharacter(KeyEvent.VK_D);
                break;
        }
    }
    private int getDirection(int i, int j) {
        if (i == -1) return 2;
        else if (i == 1) return 3;
        else if (j == -1) return 1;
        else if (j == 1) return 0;
        return -1;
    }
    private void createFogEffect() {
        FogEffect fogEffect = new FogEffect(characterX, characterY, true);
        fogEffect.applyFogEffect(squarePanels,gridSize,squareSize);
        gridPanel.requestFocusInWindow();

        moveTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveCharacterRandomly();
            }
        });
        moveTimer.start();
    }

    public static JPanel[][] getSquarePanels() {
        return squarePanels;
    }

}
