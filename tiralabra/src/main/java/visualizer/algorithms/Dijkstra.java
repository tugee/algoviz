/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.algorithms;
import java.util.*; 
import visualizer.datastructures.Node;

/**
 *
 * @author tuukk
 */
public class Dijkstra {
    
    private Node Start;
    private Node Finish;
    private char[][] map;
    private double[][] distanceFromBeginning;
    private boolean[][] settled;
    private PriorityQueue<Node> pq;
    private int height;
    private int width;
    private int[] directions = {0,-1,1};
    private int countSettled = 0;
    /**
     *
     * Initializing variables needed for Dijkstra to run.
     */
    
    public Dijkstra(char[][] map){
        this.height = map.length;
        this.width = map[0].length;
        this.pq = new PriorityQueue<Node>(height*width);
        this.map = map;
        this.distanceFromBeginning = new double[height][width];
        this.settled = new boolean[height][width];
        
        this.Start = new Node(0,0);
        this.Finish = new Node(height-1,width-1);
        
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
                countSettled++;
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
                            distanceFromBeginning[node.getY()][node.getX()] = newDistance;
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
        if(x < 0 || y < 0 || x >= width || y >= height){
            // add map block checker
            return false;
        }else{
            return true;
        }
    }


    private void markPath(Node previous) {
        if(previous.getPrevious()!=null){
            map[previous.getY()][previous.getX()] = 'P';
            markPath(previous.getPrevious());
        }
    }
}
