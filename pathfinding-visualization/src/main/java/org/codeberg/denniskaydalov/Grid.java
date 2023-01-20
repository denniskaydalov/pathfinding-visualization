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
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid extends JPanel {
    private Point gridSize;
    private Point position;
    private int cellSize;
    private Cell cells[][];
    private String selection;
    private Cell start;
    private Cell end;
    private Node startNode;
    private Node endNode;
    private Node nodes[];

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
        constructNodeGraph();
        update();
    }

    private void constructNodeGraph() {
        nodes = new Node[gridSize.x * gridSize.x];

        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                nodes[gridSize.y * i + j] = new Node(gridSize.y * i + j);
            }
        }

        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                if(cells[i][j].getColor() != Color.GRAY) {
                    if(j < gridSize.x - 1 && cells[i][j + 1].getColor() != Color.GRAY)
                        nodes[gridSize.y * i + j].addNeighbor(nodes[gridSize.y * i + 1 + j]);
                    if(i < gridSize.y - 1 && cells[i + 1][j].getColor() != Color.GRAY)
                        nodes[gridSize.y * i + j].addNeighbor(nodes[gridSize.y * (i + 1) + j]);
                }
            }
        }

        startNode = nodes[(start.getPosition().x-position.x)/cellSize + (start.getPosition().y-position.y)/cellSize * gridSize.y];
        endNode = nodes[(end.getPosition().x-position.x)/cellSize + (end.getPosition().y-position.y)/cellSize * gridSize.y];
    }

    public void printGridNodes() {
        for(Node node : nodes) {
            System.out.print(String.format("id: %d, neighbors: ", node.id));
            for(Node neighborNode : node.neighbors) {
                System.out.print(String.format("%d, ", neighborNode.id));
            }
            System.out.println();
        }
    }

    public void setFinalPath(ArrayList<Node> route) {
        redoVisited();

        for(Node node : route) {
            Cell changedCell = cells[node.id / cellSize][node.id % cellSize];
            if(changedCell != start)
                changedCell.setColor(Color.YELLOW);
            update();
        }
    }

    public void setVisited(Queue<Node> queue) {
        redoVisited();
        for(Node node : queue) {
            Cell changedCell = cells[node.id / cellSize][node.id % cellSize];
            if(changedCell != start)
                changedCell.setColor(Color.BLUE);
            update();
        }
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent( g );

        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                cells[i][j].draw(g);
            }
        }
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    private void update(){
		this.repaint();
	}

    private void redoVisited() {
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                if(cells[i][j].getColor() == Color.BLUE) cells[i][j].setColor(Color.MAGENTA);
            }
        }
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e){ 
            if(e.getX() > position.x && e.getY() > position.y && e.getX() < position.x + cellSize * gridSize.x && e.getY() < position.y + cellSize * gridSize.y) {
                switch(selection) {
                    case "Obstacles": 
                        Cell tempObstacleCell = cells[(e.getY()-position.y)/cellSize][(e.getX()-position.x)/cellSize];
                        if(tempObstacleCell.getColor() != Color.RED && tempObstacleCell.getColor() != Color.GREEN)
                            tempObstacleCell.setColor(Color.GRAY);
                        constructNodeGraph();
                        break;
                    case "Start": 
                        Cell tempStartCell = cells[(e.getY()-position.y)/cellSize][(e.getX()-position.x)/cellSize];
                        if(tempStartCell.getColor() != Color.RED) {
                            start.setColor(Color.WHITE);
                            start = tempStartCell;
                            start.setColor(Color.GREEN);
                            constructNodeGraph();
                        }
                        break;
                    case "End": 
                        Cell tempEndCell = cells[(e.getY()-position.y)/cellSize][(e.getX()-position.x)/cellSize];
                        if(tempEndCell.getColor() != Color.GREEN) {
                            end.setColor(Color.WHITE);
                            end = tempEndCell;
                            end.setColor(Color.RED);
                            constructNodeGraph();
                        }
                        break;
                    case "Clear":
                        cells[(e.getY()-position.y)/cellSize][(e.getX()-position.x)/cellSize].setColor(Color.WHITE);
                        break;
                }
                update();
            }
        }
    };

    private void setGraph() {
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                cells[i][j] = new Cell(new Point(position.x + cellSize * j, position.y + cellSize * i), cellSize);
            }
        }
        start = cells[0][0];
        start.setColor(Color.GREEN);
        end = cells[gridSize.y - 1][gridSize.x - 1];
        end.setColor(Color.RED);
    }
}
