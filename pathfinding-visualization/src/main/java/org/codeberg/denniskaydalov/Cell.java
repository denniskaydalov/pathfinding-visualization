// SPDX-License-Identifier: GPL-2.0
/*
 * Grid cell file.
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import java.awt.Graphics;
import java.awt.Color;

public class Cell {
    private Point position;
    private int size;

    public Cell(Point position, int size) {
        this.position = position;
        this.size = size;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(position.x, position.y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(position.x, position.y, size, size);
    }
}
