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
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class VisualizerFrame extends JFrame {

    private JComboBox<String> placeableOptions;
    private JSlider speedSlider;
    private JPanel nestedPanel;
    private JLabel speedLabel;
    private StartButton start;
    private JButton clear;
    private JButton save;

    public VisualizerFrame() {
        super("Pathfinding Visualizer");
        
        nestedPanel = new JPanel();

        speedSlider = new JSlider(JSlider.HORIZONTAL, 100, 500, 100);
        placeableOptions = new JComboBox<String>(new String[] { "Obstacles", "Start", "End", "Clear"});
        speedLabel = new JLabel("speed: 100");
        clear = new JButton("Clear");
        save = new JButton("Save");

        speedSlider.setPreferredSize(new Dimension(100, 20));

        Grid.getInstance().init(new Point(20, 20), new Point(45, 20), 20);

        speedSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                speedLabel.setText(String.format("speed: %d", speedSlider.getValue()));
                start.setSpeed(speedSlider.getValue());
            }
        });

        placeableOptions.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                Grid.getInstance().setSelection((String) placeableOptions.getSelectedItem());
            }
        });

        save.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics graphics = image.getGraphics();
                    paint(graphics);
                    ImageIO.write(image, "png", new File("graphics.png"));
                    JOptionPane.showMessageDialog(null, "Saved to " + System.getProperty("user.dir") + "/graphics.png");
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                if(!Grid.getInstance().isSearching()) {
                    Grid.getInstance().init(new Point(20, 20), new Point(45, 20), 20);
                    placeableOptions.setSelectedItem("Obstacles");
                }
            }
        });

        start = new StartButton(speedSlider.getValue());

        nestedPanel.add(save);
        nestedPanel.add(clear);
        nestedPanel.add(speedLabel);
        nestedPanel.add(speedSlider);
        nestedPanel.add(placeableOptions);
        nestedPanel.add(start);
        
        add(nestedPanel, BorderLayout.SOUTH); 
        add(Grid.getInstance(), BorderLayout.CENTER);
    }
}
