// SPDX-License-Identifier: GPL-2.0
/*
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingWorker;;

public class StartButton extends JButton implements ActionListener{
    public StartButton() {
        super("Start");

        addActionListener(this);
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        SwingWorker worker = new SwingWorker<Void,Void>(){
            protected Void doInBackground(){
                PathFinding.bfs(Grid.getInstance(), Grid.getInstance().getStartNode(), Grid.getInstance().getEndNode());
                return null;
            }
        };
        worker.execute();			
    }
}
