// SPDX-License-Identifier: GPL-2.0
/*
 * StartButton class is a button that starts the path finding algorithm on the grid.
 *
 * name: Dennis Kaydalov
 *
 * date: January 20, 2023
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingWorker;;

/**
 * This class defines the logic for the start button, which starts the visualization.
 * 
 * @author Dennis Kaydalov
 * 
 * @version January 20, 2023
 */
public class StartButton extends JButton implements ActionListener{
    private int speed;

    /**
     * Parameterized constructor to initialize the start button, accepts the speed for the visualization
     * 
     * @param speed speed for the visualization
     */
    public StartButton(int speed) {
        super("Start");

        this.speed = speed;

        addActionListener(this);
    }

    /**
     * Mutator method for the speed of the visualization
     * 
     * @param speed - speed for the visualization
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Method to start the visualization
     * 
     * @param e - ActionEvent
     */
    @Override
    public void actionPerformed( ActionEvent e ) {
        //multi-threading, put BFS on a different SwingWorker 
        SwingWorker worker = new SwingWorker<Void,Void>(){
            protected Void doInBackground(){
                PathFinding.bfs(Grid.getInstance(), Grid.getInstance().getStartNode(), Grid.getInstance().getEndNode(), speed);
                return null;
            }
        };
        //start the worker
        worker.execute();			
    }
}
