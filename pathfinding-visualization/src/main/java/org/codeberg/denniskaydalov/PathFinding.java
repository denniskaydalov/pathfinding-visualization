// SPDX-License-Identifier: GPL-2.0
/*
 * Pathfinding class which defines the logic for pathfinding on a grid using the BFS algorithm.
 *
 * name: Dennis Kaydalov
 *
 * date: January 20, 2023
 *
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import javax.swing.JOptionPane;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class defines the logic for the pathfinding using BFS, it reports back to the grid for visualization.
 * 
 * @author Dennis Kaydalov
 * 
 * @version January 20, 2023
 */
public class PathFinding {
    /**
     * Method to start bfs. Accepts the grid, start and end nodes and the speed for the visualization
     * 
     * @param grid - main grid
     * @param start - start node
     * @param start - end node
     * @param start - speed for the visualization
     */
    public static void bfs(Grid grid, Node start, Node end, int speed) {
        Queue<Node> queue = new LinkedList<>();

        //Visit and add start node to the queue
        start.visited = true;
        queue.add(start);

        //current time for delay calculation
		long currentTime = System.currentTimeMillis();

        //BFS until queue is empty and not reached to the end node
        while(!queue.isEmpty()){
            //if the delay has not ended yet, skip
            if(System.currentTimeMillis() - currentTime < speed - 80)
                continue;

            //reset delay
            currentTime = System.currentTimeMillis();

            //sent a request to the grid to update its cell's colors
            grid.setVisited(queue);

            //pop a node from queue for search operation
            Node current_node = queue.poll();
            //Loop through neighbors node to find the 'end'
            for(Node node: current_node.neighbors){

                if(!node.visited){
                    //Visit and add the node to the queue
                    node.visited = true;
                    queue.add(node);
                    //update its precedings nodes
                    node.previous = current_node;
                    //If reached the end node then stop BFS
                    if(node==end){
                        queue.clear();
                        break;
                    }
                }
            }
        }

        trace_route(end);
    }

    /**
     * Get trace route from the end to the start of the grid (shortest path calculation)
     * 
     * @param end - get the end node for reversing
     */
    private static void trace_route(Node end){
        Node node = end;
        ArrayList<Node> route = new ArrayList<>();
        //Loop until node is null to reach start node
        //becasue start.prev == null
        while(node != null){
            route.add(node);
            node = node.previous;
        }
        //Reverse the route - bring start to the front
        Collections.reverse(route);

        //if the route has no real path then send a JOptionPane
        if(route.size() == 1) {
            Grid.getInstance().setSearching(false);
            JOptionPane.showMessageDialog(null, "No possible solution found");
        }
        else 
            //send a request to the grid to change colors of the cells to show the final route
            Grid.getInstance().setFinalPath(route);
    }
}
