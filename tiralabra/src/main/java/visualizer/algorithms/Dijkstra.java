/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.algorithms;
import java.util.*; 
import visualizer.datastructures.Node;
import visualizer.datastructures.MinHeap;

/**
 *
 * @author tuukk
 */
public class Dijkstra {
     /**
     *
     * Initializing variables needed for Dijkstra to run.
     */
    private Node Start;
    private Node Finish;
    private char[][] map;
    private double[][] distanceFromBeginning;
    private boolean[][] settled;
    private MinHeap pq;
    private int height;
    private int width;
    private int finalcount;
    private int[] directions = {0,-1,1};

    
    public Dijkstra(char[][] map, Node start, Node finish){
        this.height = map.length;
        this.width = map[0].length;
        this.pq = new MinHeap(height*width);
        this.map = map;
        this.distanceFromBeginning = new double[height][width];
        this.settled = new boolean[height][width];
        this.finalcount = 0;
        
        this.Start = start;
        this.Finish = finish;
        
        for(int i = 0; i<height;i++){
            for(int j = 0; j<width;j++){
                settled[i][j] = false;
                distanceFromBeginning[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    
    /**
     *
     * Implementation of the dijkstra's pathfinding algorithm using a priority queue.
     * The method uses the initialized Start and Finish nodes and tries to find the shortest path between them.
     * As soon as Dijkstra's settles the Finish node we adjust the character array that is the map in order to allow for the UI to draw the path.
     * After this we return the distance from the prespecified start node to the finish node.
     * 
     */
    
    // Dijkstra is jus A* with heuristic 0, overhaul this implementation after finishing A*
    // Maybe change so that findpath can be used without initializing Start and Finish nodes? a more robust  method
    public double findPath(){
        distanceFromBeginning[Start.getY()][Start.getX()] = 0;
        Start.setMinDistance(0);
        pq.add(Start);
        while(!pq.isEmpty()){
            Node node = pq.poll();
                if(settled[node.getY()][node.getX()]){
                    continue;
                }
                // is there an issue here, where the first time you reach the end node, it might not be the shortest?
                if(node.equals(Finish)){
                    markPath(node);
                    return distanceFromBeginning[node.getY()][node.getX()];
                }
                
                settled[node.getY()][node.getX()] = true;
                finalcount++;
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
    
    public boolean[][] getClosed(){
        return settled;
    }
    
    public int getCount() {
        return finalcount;
    }
    
    private void markPath(Node previous) {
        if(previous.getPrevious()!=null){
            map[previous.getY()][previous.getX()] = 'D';
            markPath(previous.getPrevious());
        }
    }
}
