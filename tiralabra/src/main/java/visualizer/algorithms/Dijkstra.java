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
    private char[][] nodeMatrix;
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    
    public Dijkstra(char[][] matrix){
        
    }
    public void findPath(){
        while(!pq.isEmpty()){
            Node node = pq.poll();
            if(node.getVisited()){
                continue;
            }
            node.visit();
        }
    }
}
