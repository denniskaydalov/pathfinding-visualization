// SPDX-License-Identifier: GPL-2.0
/* 
 * Main file which runs the visualizer window.
 *
 * name: Dennis Kaydalov
 *
 * date: January 20, 2023
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * This class runs the visualizer window.
 * 
 * @author Dennis Kaydalov
 * 
 * @version January 20, 2023
 */
public class App 
{
    public static void main( String[] args )
    {
        // prevent clogging EDT (easier processing)
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
                VisualizerFrame appWindow = new VisualizerFrame(); 
        
                appWindow.setSize( 500, 500 );
                appWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                appWindow.setVisible( true );
            }
        });
    }
}
