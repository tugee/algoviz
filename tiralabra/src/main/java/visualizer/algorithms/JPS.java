/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.algorithms;
import java.util.ArrayList;
import java.util.PriorityQueue;
import visualizer.datastructures.Node;
import java.util.Set;
/**
 *
 * @author tuukk
 */
public class JPS {
    private Node Start;
    private Node Finish;
    private char[][] map;
    private double[][] distanceFromBeginning;
    private boolean[][] closed;
    private PriorityQueue<Node> pq;
    private Set<Node> closedSet;
    private int height;
    private int width;
    private int[] directions = {0, -1, 1};
    
    public JPS(char[][] map, Node start, Node finish) {
        this.height = map.length;
        this.width = map[0].length;
        this.pq = new PriorityQueue<Node>(height * width);
        this.map = map;
        this.distanceFromBeginning = new double[height][width];
        this.closed = new boolean[height][width];

        this.Start = start;
        this.Finish = finish;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                closed[i][j] = false;
                distanceFromBeginning[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    
    public double heuristicDistanceEnd(int x, int y, Node finish) {
        double distance = Math.sqrt(Math.pow(finish.getX() - x, 2) + Math.pow(finish.getY() - y, 2));
        return distance;
    }
    
    public double findPath() {
        distanceFromBeginning[Start.getY()][Start.getX()] = 0;
        Start.setMinDistance(0);
        pq.add(Start);
        while(!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.equals(Finish)) {
                markPath(node);
                return distanceFromBeginning[node.getY()][node.getX()];
            }
            if(closed[node.getY()][node.getX()] == true){
                continue;
            }
            closed[node.getY()][node.getX()] = true;
            for(Node next : identifySuccessors(node)){
                int neighbourY = next.getY();
                int neighbourX = next.getX();
                double currentDistance = distanceFromBeginning[neighbourY][neighbourX];
                double newDistance = node.getMinDistance() + heuristicDistanceEnd(neighbourX,neighbourY,next.getPrevious());

                if (newDistance < currentDistance) {
                    distanceFromBeginning[neighbourY][neighbourX] = newDistance;
                    Node newNode = new Node(neighbourX, neighbourY);
                    newNode.setHeuristicDistanceEnd(heuristicDistanceEnd(neighbourX, neighbourY, Finish));
                    newNode.setMinDistance(newDistance);
                    newNode.setPrevious(node);
                    pq.add(newNode);
                }
            }
        }
        return 0;
    }
    
    public ArrayList<Node> identifySuccessors(Node current){
        ArrayList<Node> successors = new ArrayList<>();
        for(int dx = -1; dx < 2;dx++){
            for(int dy = -1; dy < 2; dy++){
                if(dx==0 && dy == 0) {
                    continue;
                }
                Node jumpPoint = jump(current,dx,dy);
                if(jumpPoint!=null){
                    jumpPoint.setPrevious(current);
                    successors.add(jumpPoint);
                }
            }
        }
        return successors;
    }
    
    public Node jump(Node initial, int dx, int dy){
        int candidateX = initial.getX()+dx;
        int candidateY = initial.getY()+dy;
        
        if(!checkValidNode(candidateX,candidateY)){
            return null;
        }
        
        if(candidateX==Finish.getX()&&candidateY==Finish.getY()){
            return Finish;
        }
        
        Node diagonalForced = new Node(candidateX, candidateY);
        
        if(dx!=0&&dy!=0){
            if(forcedNeighbourCheck(initial,dx,dy)){
                return diagonalForced;
            }
            if(jump(diagonalForced,dx,0)!=null||jump(diagonalForced,0,dy)!=null){
                return diagonalForced;
            }
        } else {
            if(forcedNeighbourCheck(initial,dx,dy)){
                return diagonalForced;
            }
        }
        return jump(new Node(candidateX,candidateY),dx,dy);
    }
    
    public boolean forcedNeighbourCheck(Node initial, int dx, int dy){
        int candidateX = initial.getX() + dx;
        int candidateY = initial.getY() + dy;
        if(dy!=0&& dx!=0){
            if(checkValidNode(candidateX,candidateY)){
                if (map[initial.getY() + 1][initial.getX()+1] == '@') {
                    return true;
                }
                if (map[initial.getY() + 1][initial.getX() - 1] == '@') {
                    return true;
                }
                if (map[initial.getY() - 1][initial.getX() + 1] == '@') {
                    return true;
                }
                if (map[initial.getY() - 1][initial.getX() - 1] == '@') {
                    return true;
                }
            }
        }
        if(dy==0){
            if(checkValidNode(candidateX,candidateY)){
                if(map[initial.getY()+1][initial.getX()]=='@'){
                    return true;
                }
                if (map[initial.getY() - 1][initial.getX()] == '@') {
                    return true;
                }
            }
        }
        if (dx == 0) {
            if (checkValidNode(candidateX, candidateY)) {
                if (map[initial.getY()][initial.getX()+1] == '@') {
                    return true;
                }
                if (map[initial.getY()][initial.getX() - 1] == '@') {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean checkValidNode(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height || map[y][x] == '@') {
            // add map block checker
            return false;
        } else {
            return true;
        }
    }

    public char[][] finalMap() {
        map[Start.getY()][Start.getX()] = 'S';
        map[Finish.getY()][Finish.getX()] = 'F';
        return map;
    }

    private void markPath(Node previous) {
        if (previous.getPrevious() != null) {
            map[previous.getY()][previous.getX()] = 'J';
            markPath(previous.getPrevious());
        }
    }

    public int getCount() {
        int finalcount = 0;
        for (int i = 0; i < 512; i++) {
            for (int j = 0; j < 512; j++) {
                if (closed[i][j] == true) {
                    finalcount++;
                }
            }
        }
        return finalcount;
    }

    public boolean[][] getClosed() {
        return closed;
    }
}
