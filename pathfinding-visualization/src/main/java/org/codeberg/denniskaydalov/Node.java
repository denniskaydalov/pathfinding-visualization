// SPDX-License-Identifier: GPL-2.0
/*
 * Node class which defines the logic for the node in the BFS algorithm.
 *
 * name: Dennis Kaydalov
 *
 * date: January 20, 2023
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import java.util.ArrayList;

/**
 * This class defines the logic for the nodes that will be used in the BFS algorithm.
 * 
 * @author Dennis Kaydalov
 * 
 * @version January 20, 2023
 */
public class Node {
    int id;
    ArrayList<Node> neighbors;
    boolean visited = false;
    Node previous = null;

    /**
     * Parameterized constructor to initialize the Node
     * 
     * @param id unique id for this node
     */
    public Node(int id) {
        this.id = id;
        neighbors = new ArrayList<Node>();
    }

    /**
     * Assign neighbors
     * 
     * @param node - recipient of neighboree
     */
    public void addNeighbor(Node node) {
        //add a neighboring node, and have the neighbor add this node
        neighbors.add(node);
        node.neighbors.add(this);
    }

    /**
     * Returns a string representation of the object
     * 
     * @return String - the representation of the object
     */
    public String toString(){
        return Integer.toString(this.id);
    }
}
