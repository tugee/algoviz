/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.algorithms;

import java.util.PriorityQueue;
import visualizer.datastructures.Node;

/**
 *
 * @author tuukk
 */
public class Astar {
    private Node Start;
    private Node Finish;
    private char[][] map;
    private double[][] distanceFromBeginning;
    private boolean[][] closed;
    private PriorityQueue<Node> pq;
    private int height;
    private int width;
    private int[] directions = {0,-1,1};
    /**
     *
     * Initializing variables needed for A* to run.
     * @param map
     */
    
    public Astar(char[][] map, Node start, Node finish){
        this.height = map.length;
        this.width = map[0].length;
        this.pq = new PriorityQueue<Node>(height*width);
        this.map = map;
        this.distanceFromBeginning = new double[height][width];
        this.closed = new boolean[height][width];
        
        this.Start = start;
        this.Finish = finish;
        
        for(int i = 0; i<height;i++){
            for(int j = 0; j<width;j++){
                closed[i][j] = false;
                distanceFromBeginning[i][j] = Integer.MAX_VALUE;
            }
        }
    }
     /**
     *
     * Using Euclidean distance heuristic.Add more heuristic if time permits(?)
     * @param x
     * @param y
     * @param finish
     * @return 
     */
    public double heuristicDistanceEnd(int x, int y, Node finish){
        double distance = Math.sqrt(Math.pow(finish.getX()-x,2)+Math.pow(finish.getY()-y,2));
        return distance;
    }
    
    public double findPath(){
        distanceFromBeginning[Start.getY()][Start.getX()] = 0;
        Start.setMinDistance(0);
        pq.add(Start);
        while(!pq.isEmpty()){
            Node node = pq.poll();
                // is there an issue here, where the first time you reach the end node, it might not be the shortest?
                if(node.equals(Finish)){
                    markPath(node);
                    return distanceFromBeginning[node.getY()][node.getX()];
                }
                closed[node.getY()][node.getX()] = true;
                // this will not stop calculation at nodes which are wasted?
                for(int xDirection : directions ){
                    for(int yDirection : directions) {
                        int neighbourX = node.getX()-xDirection;
                        int neighbourY = node.getY()+yDirection;
                        if(!this.checkValidNode(neighbourX,neighbourY)){
                            continue;
                        }
                        
                        double currentDistance = distanceFromBeginning[neighbourY][neighbourX];
                        double newDistance = node.getMinDistance() + node.adjacentDistance(neighbourX,neighbourY);
                        
                        if(newDistance < currentDistance){
                            distanceFromBeginning[neighbourY][neighbourX] = newDistance;
                            Node newNode = new Node(neighbourX,neighbourY);
                            newNode.setHeuristicDistanceEnd(heuristicDistanceEnd(neighbourX,neighbourY,Finish));
                            newNode.setMinDistance(newDistance);
                            newNode.setPrevious(node);
                            pq.add(newNode);
                        }
                    }
                }
        }
        return 0;
    }
    
    public boolean checkValidNode(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height || map[y][x]=='@'){
            // add map block checker
            return false;
        }else{
            return true;
        }
    }
    public char[][] finalMap(){
        map[Start.getY()][Start.getX()] = 'S';
        map[Finish.getY()][Finish.getX()] = 'F';
        return map;
    }
    private void markPath(Node previous) {
        if(previous.getPrevious()!=null){
            map[previous.getY()][previous.getX()] = 'A';
            markPath(previous.getPrevious());
        }
    }
    
    public int getCount() {
        int finalcount=0;
        for(int i = 0; i<512;i++){
            for(int j = 0; j<512;j++){
                if(closed[i][j]==true){
                    finalcount++;
                }
            }
        }
        return finalcount;
    }
    
    public boolean[][] getClosed(){
        return closed;
    }
    
}

