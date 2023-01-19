// SPDX-License-Identifier: GPL-2.0
/*
 * Main frame file.
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Point;

public class NestedLayoutFrame extends JFrame {

    public NestedLayoutFrame() {
        super("NestedLayout Test");
        
        JPanel nestedPanel = new JPanel();

        JComboBox<String> algorithmOptions = new JComboBox<String>(new String[] { "BFS", "Dijkstra's", "A*" });
        JComboBox<String> placeableOptions = new JComboBox<String>(new String[] { "Obstacles", "Start", "End"});
        StartButton start = new StartButton();

        nestedPanel.add(algorithmOptions);
        nestedPanel.add(placeableOptions);
        nestedPanel.add(start);

        Grid grid = new Grid(new Point(20, 20), new Point(45, 20), 20);
        
        add(nestedPanel, BorderLayout.SOUTH); 
        add(grid, BorderLayout.CENTER);
    }
}
