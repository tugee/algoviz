/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.datastructures;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implementation for a Node data structure, which is needed in our implementation of a 2D grid
 * @author tuukk
 */
public class Node implements Comparable<Node>{
    
    private int x;
    private int y;
    private double MinDistance;
    private double heuristicDistanceEnd;
    private boolean visited = false;
    Node previous;
    /**
     * Node constructor initializes co-ordinates. MinDistance set at -1 for when 
     *
     * @author tuukk
     */
    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.MinDistance = -1;
        this.heuristicDistanceEnd = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getMinDistance() {
        return MinDistance;
    }
    
    public void setMinDistance(double distance){
        MinDistance = distance;
    }
    
    public double getHeuristicDistanceEnd(){
        return heuristicDistanceEnd;
    }
    
    public void setHeuristicDistanceEnd(double distance) {
        heuristicDistanceEnd = distance;
    }
    
    public void setPrevious(Node previous){
        this.previous = previous;
    }
    
    public int[] parentDirection(){
        int[] directions = new int[2];
        
        Node parent = this.getPrevious();
        
        int dx = x-parent.getX();
        
        if(dx!=0){
            dx = dx / Math.abs(dx);   
        }
        int dy = y-parent.getY();
        
        if(dy!=0){
            dy = dy / Math.abs(dy);
        }
        
        
        directions[0]=dx;
        directions[1]=dy;
        
        return directions;
    }

    public Node getPrevious() {
        return previous;
    }

    @Override
    public int compareTo(Node o2) {
        
        double priority1 = MinDistance + heuristicDistanceEnd;
        double priority2 = o2.getMinDistance() + o2.getHeuristicDistanceEnd();
        
        if(priority1<priority2){
            return -1;
        }
        if (priority1>priority2) {
            return 1;
        }
        return 0;
    }
    
    public boolean equals(Node o1){
        if(o1.getX()==x && o1.getY()==y){
            return true;
        }
        return false;
    }
}