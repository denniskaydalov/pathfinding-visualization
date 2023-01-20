// SPDX-License-Identifier: GPL-2.0
/*
 * Copyright (C) 2022 Dennis Kaydalov
 */ 

package org.codeberg.denniskaydalov;

import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

public class PathFinding {
    public static void bfs(Grid grid, Node start, Node end) {
        Queue<Node> queue = new LinkedList<>();

        //Visit and add start node to the queue
        start.visited = true;
        queue.add(start);

		long currentTime = System.currentTimeMillis();

        //BFS until queue is empty and not reached to the end node
        while(!queue.isEmpty()){
            if(System.currentTimeMillis() - currentTime < 100)
                continue;

            currentTime = System.currentTimeMillis();

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

    //Function to trace the route using preceding nodes
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

        Grid.getInstance().setFinalPath(route);
    }
}
