// SPDX-License-Identifier: GPL-2.0
/*
 * Grid file.
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

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
        setGraph();
        addMouseListener(mouseAdapter);
    }

    private void setGraph() {
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                cells[j][i] = new Cell(new Point(position.x + cellSize * j, position.y + cellSize * i), cellSize);
            }
        }
    }
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent( g );

        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                cells[j][i].draw(g);
            }
        }
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){ 
            cells[(e.getX()-position.x)/cellSize][(e.getY()-position.y)/cellSize].setColor(Color.BLUE);
            update();
        }
    };

    private void update(){
		this.repaint();
	}
}
