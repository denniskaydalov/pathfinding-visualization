// SPDX-License-Identifier: GPL-2.0
/*
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App 
{
    public static void main( String[] args )
    {
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
