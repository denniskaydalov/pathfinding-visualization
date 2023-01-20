// SPDX-License-Identifier: GPL-2.0
/*
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VisualizerFrame extends JFrame {

    private JComboBox<String> placeableOptions;
    private JSlider speedSlider;
    private JPanel nestedPanel;
    private JLabel speedLabel;
    private StartButton start;
    private JButton clear;

    public VisualizerFrame() {
        super("Pathfinding Visualizer");
        
        nestedPanel = new JPanel();

        speedSlider = new JSlider(JSlider.HORIZONTAL, 100, 500, 100);
        placeableOptions = new JComboBox<String>(new String[] { "Obstacles", "Start", "End", "Clear"});
        speedLabel = new JLabel("speed: 100");
        clear = new JButton("Clear");

        speedSlider.setPreferredSize(new Dimension(100, 20));

        Grid.getInstance().init(new Point(20, 20), new Point(45, 20), 20);

        speedSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                speedLabel.setText(String.format("speed: %d", speedSlider.getValue()));
            }
        });

        placeableOptions.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                Grid.getInstance().setSelection((String) placeableOptions.getSelectedItem());
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                Grid.getInstance().init(new Point(20, 20), new Point(45, 20), 20);
                placeableOptions.setSelectedItem("Obstacles");
            }
        });

        start = new StartButton(speedSlider.getValue());

        nestedPanel.add(clear);
        nestedPanel.add(speedLabel);
        nestedPanel.add(speedSlider);
        nestedPanel.add(placeableOptions);
        nestedPanel.add(start);
        
        add(nestedPanel, BorderLayout.SOUTH); 
        add(Grid.getInstance(), BorderLayout.CENTER);
    }
}
