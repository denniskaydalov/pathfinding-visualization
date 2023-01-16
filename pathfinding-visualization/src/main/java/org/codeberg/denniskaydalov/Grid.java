// SPDX-License-Identifier: GPL-2.0
/*
 * Main file.
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JPanel;
import java.awt.Graphics;

public class Grid extends JPanel {
    private Point gridSize;
    private Point position;
    private int cellSize;
    private Cell cells[][];
    
    public Grid(Point gridSize, Point position, int cellSize) {
        this.gridSize = gridSize;
        this.position = position;
        this.cellSize = cellSize;
        cells = new Cell[gridSize.y][gridSize.x];
    }

    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent( g );

        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                cells[i][j] = new Cell(new Point(position.x + cellSize * j, position.y + cellSize * i), cellSize);
                cells[i][j].draw(g);
            }
        }
    }
}
