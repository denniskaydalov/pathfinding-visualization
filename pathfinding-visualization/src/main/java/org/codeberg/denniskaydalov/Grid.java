// SPDX-License-Identifier: GPL-2.0
/*
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
    private String selection;
    private Cell start;
    private Cell end;

    private static Grid grid = new Grid();

    private Grid(){};

    public static Grid getInstance() {
        return grid;
    }
    
    public void init(Point gridSize, Point position, int cellSize) {
        this.gridSize = gridSize;
        this.position = position;
        this.cellSize = cellSize;
        selection = "Obstacles";
        cells = new Cell[gridSize.y][gridSize.x];
        setGraph();
        addMouseListener(mouseAdapter);
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){ 
            switch(selection) {
                case "Obstacles": 
                    cells[(e.getX()-position.x)/cellSize][(e.getY()-position.y)/cellSize].setColor(Color.GRAY);
                    break;
                case "Start": 
                    start.setColor(Color.WHITE);
                    start = cells[(e.getX()-position.x)/cellSize][(e.getY()-position.y)/cellSize];
                    start.setColor(Color.GREEN);
                    break;
                case "End": 
                    end.setColor(Color.WHITE);
                    end = cells[(e.getX()-position.x)/cellSize][(e.getY()-position.y)/cellSize];
                    end.setColor(Color.RED);
                    break;
            }
            update();
        }
    };

    private void setGraph() {
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                cells[j][i] = new Cell(new Point(position.x + cellSize * j, position.y + cellSize * i), cellSize);
            }
        }
        start = cells[0][0];
        start.setColor(Color.GREEN);
        end = cells[gridSize.x - 1][gridSize.y - 1];
        end.setColor(Color.RED);
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

    public void setSelection(String selection) {
        this.selection = selection;
    }

    private void update(){
		this.repaint();
	}
}
