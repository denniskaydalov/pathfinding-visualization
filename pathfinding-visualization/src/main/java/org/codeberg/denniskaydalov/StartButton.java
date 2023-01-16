// SPDX-License-Identifier: GPL-2.0
/*
 * Start button file.
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartButton extends JButton implements ActionListener{
    public StartButton() {
        super("Start");

        addActionListener(this);
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        JOptionPane.showMessageDialog(null, ((JButton) e.getSource()).getText());
    }
}
