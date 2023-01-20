// SPDX-License-Identifier: GPL-2.0
/*
 * Cell class which defines logic for each cell within a grid.
 *
 * name: Dennis Kaydalov
 *
 * date: January 20, 2023
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

/**
 * This class defines the logic for each cell within a grid.
 * 
 * @author Dennis Kaydalov
 * 
 * @version January 20, 2023
 */
public class Cell {
    private Point position;
    private int size;
    private Color color;

    /**
     * Parameterized constructor to initialize the Cell
     * 
     * @param position position of the cell
     * @param size size of the cell (both width and height)
     */
    public Cell(Point position, int size) {
        this.position = position;
        this.size = size;
        color = Color.WHITE;
    }

    /**
     * Draw the cell on the screen based on color and size
     * @param g graphics
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(position.x, position.y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(position.x, position.y, size, size);
    }

    /**
     * Accessor method for the position of the cell
     * 
     * @return Point - returns the position of the cell
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Accessor method for the size of the cell
     * 
     * @return int - returns the size of the cell
     */
    public int getSize() {
        return size;
    }

    /**
     * Mutator method for the color of the cell
     * 
     * @param color - the color to set the cell to
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Accessor method for the color of the cell
     * 
     * @return Color - returns the color of the cell
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns a string representation of the object
     * 
     * @return String - the representation of the object
     */
    public String toString(){
        return "x: " + Integer.toString(position.x) + " y: " + Integer.toString(position.y) + " color: " + color;
    }
}
