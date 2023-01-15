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
    private Pair gridSize;
    private Pair position;
    private int cellSize;
    private Cell cells[][];
    
    public Grid(Pair gridSize, Pair position, int cellSize) {
        this.gridSize = gridSize;
        this.position = position;
        this.cellSize = cellSize;
        cells = new Cell[gridSize.y][gridSize.x];
    }

    @Override
	public void paintComponent(Graphics g) {
        //System.out.println(g.getClipBounds()); // return rectangle (use .getWidth and .getHeight)
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                cells[i][j] = new Cell(new Pair(position.x + cellSize * j, position.y + cellSize * i), cellSize);
                cells[i][j].draw(g);
            }
        }
    }
}
