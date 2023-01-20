// SPDX-License-Identifier: GPL-2.0
/*
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import java.util.ArrayList;

public class Node {
    int id;
    ArrayList<Node> neighbors;
    boolean visited = false;
    Node previous = null;

    public Node(int id) {
        this.id = id;
        neighbors = new ArrayList<Node>();
    }

    public void addNeighbor(Node node) {
        neighbors.add(node);
        node.neighbors.add(this);
    }

    public String toString(){
        return Integer.toString(this.id);
    }
}
