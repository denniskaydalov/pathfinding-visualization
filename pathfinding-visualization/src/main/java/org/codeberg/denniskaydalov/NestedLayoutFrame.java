// SPDX-License-Identifier: GPL-2.0
/*
 * Main file.
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;

public class NestedLayoutFrame extends JFrame {
    private JButton button;
    
    public NestedLayoutFrame() {
        super("NestedLayout Test");
        
        JPanel nestedPanel = new JPanel();

        JComboBox<String> touchDownMenu = new JComboBox<String>(new String[] { "a", "b", "c" });

        nestedPanel.add(touchDownMenu);

        Grid grid = new Grid(new Pair(34, 14), new Pair(53, 100), 25);
        
        add(nestedPanel, BorderLayout.NORTH); 
        add (grid, BorderLayout.CENTER);
    }
}
