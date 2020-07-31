/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.datastructures;

import java.util.Comparator;

/**
 * Implementation for a Node data structure, which is needed in our implementation of a 2D grid
 * @author tuukk
 */
public class Node implements Comparator<Node>{
    
    private int x;
    private int y;
    private int priority;
    private int MinDistance;
    private boolean visited = false;
    Node previous;
    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.MinDistance = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPriority() {
        return priority;
    }
    
    public int getMinDistance() {
        return MinDistance;
    }

    public Node getPrevious() {
        return previous;
    }
    
    public void visit(){
        this.visited = true;
    }
    
    public boolean getVisited(){
        return this.visited;
    }

    @Override
    public int compare(Node o1, Node o2) {
        if(o1.getMinDistance()<o2.getMinDistance()){
            return -1;
        }
        if (o1.getMinDistance() > o2.getMinDistance()) {
            return 1;
        }
        return 0;
    }
}