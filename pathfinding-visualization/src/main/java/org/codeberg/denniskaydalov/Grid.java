// SPDX-License-Identifier: GPL-2.0
/*
 * Grid class defines the logic for drawing and editing the main grid.
 *
 * name: Dennis Kaydalov
 *
 * date: January 20, 2023
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
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class defines the logic for the main grid that the visualizations will be on.
 * 
 * @author Dennis Kaydalov
 * 
 * @version January 20, 2023
 */
public class Grid extends JPanel {
    //size, in squares of the grid
    private Point gridSize;
    //initial (top left) position of the grid
    private Point position;
    //size for each cell in the grid
    private int cellSize;
    private Cell cells[][];
    //which type of cell the user wants to place
    private String selection;
    private Cell start;
    private Cell end;
    //node representation of the start cell for BFS
    private Node startNode;
    //node representation of the end cell for BFS
    private Node endNode;
    private Node nodes[];
    //boolean to check if a search is happening
    private boolean searching;

    //(singleton pattern) static object
    private static Grid grid = new Grid();

    //(singleton pattern) private constructor
    private Grid(){};

    //(singleton pattern) static getter for the object
    public static Grid getInstance() {
        return grid;
    }
    
    /**
     * Initialize the object with a grid size, position and cell sizes. Also resets the graph.
     * 
     * @param gridSize - the size of the grid in squares
     * @param position - the initial position for the grid 
     * @param cellSize - size of each cell on the grid
     */
    public void init(Point gridSize, Point position, int cellSize) {
        this.gridSize = gridSize;
        this.position = position;
        this.cellSize = cellSize;
        searching = false;
        //make default slection same as default for the combo box
        selection = "Obstacles";
        cells = new Cell[gridSize.y][gridSize.x];
        //set new cell objects for the graph
        setGraph();
        addMouseListener(mouseAdapter);
        constructNodeGraph();
        update();
    }

    /**
     * Construct the node graph for BFS. Also set the startNode and endNode variables to their respective cells.
     */
    private void constructNodeGraph() {
        //initialize nodes
        nodes = new Node[gridSize.x * gridSize.x];

        //set each node to a unique value
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                nodes[gridSize.y * i + j] = new Node(gridSize.y * i + j);
            }
        }

        //loop through all nodes
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                //ignore if the cell is a obstacle cell
                if(cells[i][j].getColor() != Color.GRAY) {
                    //bounds checking and obstacle cell checking
                    if(j < gridSize.x - 1 && cells[i][j + 1].getColor() != Color.GRAY)
                        //add a neighbor to this node
                        nodes[gridSize.y * i + j].addNeighbor(nodes[gridSize.y * i + 1 + j]);
                    //bounds checking and obstacle cell checking
                    if(i < gridSize.y - 1 && cells[i + 1][j].getColor() != Color.GRAY)
                        //add a neighbor to this node
                        nodes[gridSize.y * i + j].addNeighbor(nodes[gridSize.y * (i + 1) + j]);
                }
            }
        }

        //set start node to its cell
        startNode = nodes[(start.getPosition().x-position.x)/cellSize + (start.getPosition().y-position.y)/cellSize * gridSize.y];
        //set end node to its cell
        endNode = nodes[(end.getPosition().x-position.x)/cellSize + (end.getPosition().y-position.y)/cellSize * gridSize.y];
    }

    /**
     * Set all cells that led to the fastest past to yellow. Additionally sets all blue cells to purple 
     * 
     * @param route - route list that holds the position of all cells that led to the fastest path
     */
    public void setFinalPath(ArrayList<Node> route) {
        //make all blue cells purple
        redoVisited();

        for(Node node : route) {
            //get cell based off node id
            Cell changedCell = cells[node.id / cellSize][node.id % cellSize];
            if(changedCell != start)
                changedCell.setColor(Color.YELLOW);
            update();
        }

        //turn off searching to renable clear and modifying grid
        searching = false;
    }

    /**
     * Sets all newly visited cells to blue, and previously visited cells to purple
     * 
     * @param queue - queue of all the newly visited nodes
     */
    public void setVisited(Queue<Node> queue) {
        //set searching to true to prevent user griefing
        searching = true;
        //set all previous cells to purple
        redoVisited();
        for(Node node : queue) {
            //get cell based off node id
            Cell changedCell = cells[node.id / cellSize][node.id % cellSize];
            if(changedCell != start)
                changedCell.setColor(Color.BLUE);
            update();
        }
    }

    /**
     * Accessor method for the start node
     * 
     * @return Node - returns the start node
     */
    public Node getStartNode() {
        return startNode;
    }

    /**
     * Accessor method for the end node
     * 
     * @return Node - returns the end node
     */
    public Node getEndNode() {
        return endNode;
    }

    /**
     * Paints the grid onto the screen.
     * 
     * @param g - graphics
     */
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent( g );

        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                cells[i][j].draw(g);
            }
        }
    }

    /**
     * Mutator method for the selection string
     * 
     * @param selection - set selection 
     */
    public void setSelection(String selection) {
        this.selection = selection;
    }

    /**
     * Accessor method for the searching boolean
     * 
     * @return boolean - returns if the grid is being searched
     */
    public boolean isSearching() {
        return searching;
    }

    /**
     * Mutator method for the searching boolean
     * 
     * @param searching - set searching 
     */
    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    /**
     * Update the screen
     */
    private void update(){
		this.repaint();
	}

    /**
     * Turn all blue cells (previously visited) to purple
     */
    private void redoVisited() {
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                if(cells[i][j].getColor() == Color.BLUE) cells[i][j].setColor(Color.MAGENTA);
            }
        }
    }

    //MouseAdapter object to handle mousePressed inputs
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        /**
         * Handles when the mouse has been pressed
         * 
         * @param e - the mouse event
         */
        @Override
        public void mousePressed(MouseEvent e){ 
            //check bounds
            if(e.getX() > position.x && e.getY() > position.y && e.getX() < position.x + cellSize * gridSize.x && e.getY() < position.y + cellSize * gridSize.y && !searching) {
                //set the appriate square based off the crurent JComboBox selection
                switch(selection) {
                    case "Obstacles": 
                        //temp variable to hold the actual cell, calculated based of mouse coordinates and grid offsets/sizes
                        Cell tempObstacleCell = cells[(e.getY()-position.y)/cellSize][(e.getX()-position.x)/cellSize];
                        //make sure to not click an end nor a start cell
                        if(tempObstacleCell.getColor() != Color.RED && tempObstacleCell.getColor() != Color.GREEN)
                            tempObstacleCell.setColor(Color.GRAY);
                        //reconstruct graph
                        constructNodeGraph();
                        break;
                    case "Start": 
                        //temp variable to hold the actual cell, calculated based of mouse coordinates and grid offsets/sizes
                        Cell tempStartCell = cells[(e.getY()-position.y)/cellSize][(e.getX()-position.x)/cellSize];
                        //make sure not to replace end with start
                        if(tempStartCell.getColor() != Color.RED) {
                            //set previous cell to white
                            start.setColor(Color.WHITE);
                            start = tempStartCell;
                            //set new cell to green
                            start.setColor(Color.GREEN);
                            //reconstruct graph
                            constructNodeGraph();
                        }
                        break;
                    case "End": 
                        //temp variable to hold the actual cell, calculated based of mouse coordinates and grid offsets/sizes
                        Cell tempEndCell = cells[(e.getY()-position.y)/cellSize][(e.getX()-position.x)/cellSize];
                        //make sure not to replace start with end
                        if(tempEndCell.getColor() != Color.GREEN) {
                            //set previous cell to white
                            end.setColor(Color.WHITE);
                            end = tempEndCell;
                            //set new cell to red
                            end.setColor(Color.RED);
                            //reconstruct graph
                            constructNodeGraph();
                        }
                        break;
                    case "Clear":
                        //temp variable to hold the actual cell, calculated based of mouse coordinates and grid offsets/sizes
                        Cell tempClearCell = cells[(e.getY()-position.y)/cellSize][(e.getX()-position.x)/cellSize];
                        //make sure to not click an end nor a start cell
                        if(tempClearCell.getColor() != Color.RED && tempClearCell.getColor() != Color.GREEN)
                            tempClearCell.setColor(Color.WHITE);
                        //reconstruct graph
                        constructNodeGraph();
                        break;
                }
                update();
            }
        }
    };

    /**
     * Give the entire graph new cell objects
     */
    private void setGraph() {
        for(int i = 0; i < gridSize.y; ++i) {
            for(int j = 0; j < gridSize.x; ++j) {
                //assign new cell with appriate coordinates
                cells[i][j] = new Cell(new Point(position.x + cellSize * j, position.y + cellSize * i), cellSize);
            }
        }

        //set the start
        start = cells[0][0];
        start.setColor(Color.GREEN);
        //set the end
        end = cells[gridSize.y - 1][gridSize.x - 1];
        end.setColor(Color.RED);
    }
}
