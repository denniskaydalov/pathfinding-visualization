// SPDX-License-Identifier: GPL-2.0
/*
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class Cell {
    private Point position;
    private int size;
    private Color color;

    public Cell(Point position, int size) {
        this.position = position;
        this.size = size;
        color = Color.WHITE;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(position.x, position.y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(position.x, position.y, size, size);
    }

    public Point getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
