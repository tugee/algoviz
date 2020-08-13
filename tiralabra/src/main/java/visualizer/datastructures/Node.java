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
    private int priority;
    private double MinDistance;
    private double heuristicDistanceEnd;
    private boolean visited = false;
    Node previous;
    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.MinDistance = -1;
        this.heuristicDistanceEnd = 0;
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

    public Node getPrevious() {
        return previous;
    }
    
    public void visit(){
        this.visited = true;
    }
    
    public boolean getVisited(){
        return this.visited;
    }
    
    public double adjacentDistance(int x2, int y2){
        if((Math.abs(x2-x)==1) && (Math.abs(y2-y)==1)){
            return 1.4142;
        } else {
            return 1;
        }
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