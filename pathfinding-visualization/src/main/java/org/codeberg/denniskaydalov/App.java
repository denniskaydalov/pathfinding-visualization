// SPDX-License-Identifier: GPL-2.0
/*
 * Main file.
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        NestedLayoutFrame appWindow = new NestedLayoutFrame(); 
        
        appWindow.setSize( 960, 540 );
        appWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        appWindow.setVisible( true );
    }
}
