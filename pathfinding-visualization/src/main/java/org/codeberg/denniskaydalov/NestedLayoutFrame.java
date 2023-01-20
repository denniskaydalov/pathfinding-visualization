// SPDX-License-Identifier: GPL-2.0
/*
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NestedLayoutFrame extends JFrame {

    private JComboBox<String> placeableOptions;
    private JComboBox<String> algorithmOptions;
    private JPanel nestedPanel;
    private StartButton start;

    public NestedLayoutFrame() {
        super("Pathfinding Visualizer");
        
        nestedPanel = new JPanel();

        algorithmOptions = new JComboBox<String>(new String[] { "BFS", "Dijkstra's", "A*" });
        placeableOptions = new JComboBox<String>(new String[] { "Obstacles", "Start", "End"});

        Grid.getInstance().init(new Point(20, 20), new Point(45, 20), 20);

        placeableOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Grid.getInstance().setSelection((String) placeableOptions.getSelectedItem());
            }
        });

        start = new StartButton();

        nestedPanel.add(algorithmOptions);
        nestedPanel.add(placeableOptions);
        nestedPanel.add(start);
        
        add(nestedPanel, BorderLayout.SOUTH); 
        add(Grid.getInstance(), BorderLayout.CENTER);
    }
}
