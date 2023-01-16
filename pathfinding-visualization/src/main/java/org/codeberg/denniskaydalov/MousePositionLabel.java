// SPDX-License-Identifier: GPL-2.0
/*
 * Label to show mouse position file.
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MousePositionLabel extends MouseAdapter {
    private JPanel panel;
    private JLabel label;

    public MousePositionLabel() {
        panel = new JPanel();
        label = new JLabel("Unknown");

        panel.add(label);
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void mouseMoved( MouseEvent e ) {
        label.setText("Mouse Position: (" + e.getX() + ", " + e.getY() + ")"); 
    } 
}

