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

public class NestedLayoutFrame extends JFrame {

    final boolean DEBUG = true;
    
    public NestedLayoutFrame() {
        super("NestedLayout Test");
        
        JPanel nestedPanel = new JPanel();

        JComboBox<String> algorithmOptions = new JComboBox<String>(new String[] { "BFS", "Dijkstra's", "A*" });
        JComboBox<String> placeableOptions = new JComboBox<String>(new String[] { "Obstacles", "Start", "End"});
        StartButton start = new StartButton();

        nestedPanel.add(algorithmOptions);
        nestedPanel.add(placeableOptions);
        nestedPanel.add(start);

        if(DEBUG) {
            MousePositionLabel mousePositionLabel = new MousePositionLabel();
            nestedPanel.add(mousePositionLabel.getPanel());
            addMouseMotionListener(mousePositionLabel);
        }

        Grid grid = new Grid(new Point(34, 14), new Point(53, 50), 25);
        
        add(nestedPanel, BorderLayout.NORTH); 
        add(grid, BorderLayout.CENTER);
    }
}
