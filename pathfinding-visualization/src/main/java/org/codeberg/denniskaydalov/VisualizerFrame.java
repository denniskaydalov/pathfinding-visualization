// SPDX-License-Identifier: GPL-2.0
/*
 * VisualizerFrame class which defines the logic for the main frame.
 *
 * name: Dennis Kaydalov
 *
 * date: January 20, 2023
 *
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

/**
 * This class defines the logic for main frame, this includes all buttons and the grid itself.
 * 
 * @author Dennis Kaydalov
 * 
 * @version January 20, 2023
 */
public class VisualizerFrame extends JFrame {

    private JComboBox<String> placeableOptions;
    private JSlider speedSlider;
    //nested panel to handle everything but the grid
    private JPanel nestedPanel;
    private JLabel speedLabel;
    private StartButton start;
    private JButton clear;
    private JButton save;

    /**
     * Constructor to initialize the visualizer frame, including all UI elements and the grid details
     */
    public VisualizerFrame() {
        super("Pathfinding Visualizer");
        
        nestedPanel = new JPanel();

        //JSlider for speed, minimum of 100, maximum of 500
        speedSlider = new JSlider(JSlider.HORIZONTAL, 100, 500, 100);
        //JComboBox for all the options that the user can choose to place onto the grid
        placeableOptions = new JComboBox<String>(new String[] { "Obstacles", "Start", "End", "Clear"});
        //displays speed
        speedLabel = new JLabel("speed: 100");
        //clear grid
        clear = new JButton("Clear");
        //save a screenshot of the entire window
        save = new JButton("Save");

        //make the slider smaller
        speedSlider.setPreferredSize(new Dimension(100, 20));

        //initialize the grid :20x20, offset (45, 20) and the cells are all 20 in length
        Grid.getInstance().init(new Point(20, 20), new Point(45, 20), 20);

        speedSlider.addChangeListener(new ChangeListener(){
            /**
             * Method to change the speedLabel and startbutton speed based on what the user sled the slider to
             * 
             * @param e - ChangeEvent
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                speedLabel.setText(String.format("speed: %d", speedSlider.getValue()));
                start.setSpeed(speedSlider.getValue());
            }
        });

        placeableOptions.addActionListener(new ActionListener() {
            /**
             * Method to change the placeable option on the grid
             * 
             * @param e - ActionEvent
             */
            @Override 
            public void actionPerformed(ActionEvent e) {
                Grid.getInstance().setSelection((String) placeableOptions.getSelectedItem());
            }
        });

        save.addActionListener(new ActionListener() {
            /**
             * Method to make a save of the window (a screenshot)
             * 
             * @param e - ActionEvent
             */
            @Override 
            public void actionPerformed(ActionEvent e) {
                //try making an image, if successfull, display the path in a JOptionPaine
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
            /**
             * Method to clear the grid and reset the JComboBox options
             * 
             * @param e - ActionEvent
             */
            @Override 
            public void actionPerformed(ActionEvent e) {
                if(!Grid.getInstance().isSearching()) {
                    Grid.getInstance().init(new Point(20, 20), new Point(45, 20), 20);
                    placeableOptions.setSelectedItem("Obstacles");
                }
            }
        });

        start = new StartButton(speedSlider.getValue());

        //add all the items except for the grid onto the nested label
        nestedPanel.add(save);
        nestedPanel.add(clear);
        nestedPanel.add(speedLabel);
        nestedPanel.add(speedSlider);
        nestedPanel.add(placeableOptions);
        nestedPanel.add(start);
        
        //add both the grid and the nested label onto the screen
        add(nestedPanel, BorderLayout.SOUTH); 
        add(Grid.getInstance(), BorderLayout.CENTER);
    }
}
