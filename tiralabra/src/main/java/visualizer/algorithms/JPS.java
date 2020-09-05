/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.algorithms;
import visualizer.datastructures.MinHeap;
import visualizer.datastructures.Node;
import visualizer.datastructures.DynamicList;

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
    private MinHeap pq;
    private int height;
    private int width;
    private int[] directions = {0, -1, 1};
    private boolean[][] considered;
    private int finalcount;
    
    public JPS(char[][] map, Node start, Node finish) {
        this.height = map.length;
        this.width = map[0].length;
        this.pq = new MinHeap(height * width);
        this.map = map;
        this.distanceFromBeginning = new double[height][width];
        this.closed = new boolean[height][width];
        this.considered = new boolean[height][width];
        this.finalcount = 0;

        this.Start = start;
        this.Finish = finish;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                closed[i][j] = false;
                distanceFromBeginning[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    /** 
     * The euclidian heuristic distance to the specified finish node from the current coordinates
     * @param x -coordinate of current node
     * @param y -coordinate of current node
     * @param finish objective node, to which we measure the euclidean distance to
     * @return the distance between current and objective node
     */
    public double heuristicDistanceEnd(int x, int y, Node finish) {
        int xDistance = finish.getX()-x;
        int yDistance = finish.getY()-y;
        double distance = Math.sqrt(xDistance*xDistance + yDistance*yDistance);
        return distance;
    }
    
    
    /**
     * Finds the shortest path from start node to finish node using the Jump Point Search algorithm.
     * @return distance from start to finish, if exists, otherwise return 0
     */
    public double findPath() {
        distanceFromBeginning[Start.getY()][Start.getX()] = 0;
        Start.setMinDistance(0);
        pq.add(Start);
        while(!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.equals(Finish)) {
                Finish.setPrevious(node.getPrevious());
                return distanceFromBeginning[node.getY()][node.getX()];
            }

            closed[node.getY()][node.getX()] = true;
            finalcount++;
            
            DynamicList successors = identifySuccessors(node);
            
            for(int i = 0; i < successors.size(); i++){
                Node next = successors.get(i);
                int neighbourY = next.getY();
                int neighbourX = next.getX();
                
                
                double currentDistance = distanceFromBeginning[neighbourY][neighbourX];
                double newDistance = node.getMinDistance() + heuristicDistanceEnd(neighbourX,neighbourY,node);

                if (newDistance <= currentDistance) {
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
    
    public DynamicList identifySuccessors(Node current){
        DynamicList successors = new DynamicList();
        DynamicList neighbours = identifyNeighbours(current);
            for(int i = 0; i < neighbours.size();i++){
                Node neighbour = neighbours.get(i);
                int dirY = neighbour.getY()-current.getY();
                int dirX = neighbour.getX()-current.getX();
                if(dirX == 0 & dirY == 0){
                    continue;
                }
                Node jumpPoint = jump(current,dirX,dirY);
                if(jumpPoint!=null){
                    if(closed[jumpPoint.getY()][jumpPoint.getX()]==true){
                        continue;
                    }
                    successors.add(jumpPoint);
                }
            }
        return successors;
    }
    
    /**
     * @param current The node neighbours of which we want to identify
     * @return list of nodes, which are our list of pruned neighbours
     */
    public DynamicList identifyNeighbours(Node current){
        DynamicList neighbours = new DynamicList();
        
        int x = current.getX();
        int y = current.getY();
        
        if(current.getPrevious()==null){
            for(int i = -1; i<2;i++){
                for(int j = -1; j<2; j++){
                    if(i==0&&j==0){
                        continue;
                    }
                    if(checkValidNode(x+i,y+j)){
                        Node neighbourNode = new Node(x+i, y+j);
                        neighbours.add(neighbourNode);   
                    }
                }
            }
        } else {
            int[] direction = current.parentDirection();
            int dx = direction[0];
            int dy = direction[1];

            if(dx!=0 && dy!=0){
                    if(checkValidNode(x+dx,y)){
                        Node neighbourNode = new Node(x + dx, y);
                        neighbours.add(neighbourNode);
                    }
                    if (checkValidNode(x, y + dy)) {
                        Node neighbourNode = new Node(x, y + dy);
                        neighbours.add(neighbourNode);
                    }
                    if (!checkValidNode(x - dx, y)) {
                        Node neighbourNode = new Node(x - dx, y + dy);
                        neighbours.add(neighbourNode);
                    }
                    if(!checkValidNode(x,y - dy)){
                        Node neighbourNode = new Node(x + dx, y - dy);
                        neighbours.add(neighbourNode);
                    }
                    if(checkValidNode(x + dx,y + dy)){
                        Node neighbourNode = new Node(x + dx, y + dy);
                        neighbours.add(neighbourNode);   
                    }
            } else {
                if (dx != 0) {
                    if (!checkValidNode(x, y + 1)) {
                        Node neighbourNode = new Node(x + dx, y + 1);
                        neighbours.add(neighbourNode);
                    }
                    if (!checkValidNode(x, y - 1)) {
                        Node neighbourNode = new Node(x + dx, y - 1);
                        neighbours.add(neighbourNode);
                    }
                    if (checkValidNode(x + dx, y)) {
                        Node neighbourNode = new Node(x + dx, y);
                        neighbours.add(neighbourNode);
                    }
                } else {
                    if (!checkValidNode(x + 1, y)) {
                        Node neighbourNode = new Node(x + 1, y + dy);
                        neighbours.add(neighbourNode);
                    }
                    if (!checkValidNode(x - 1, y)) {
                        Node neighbourNode = new Node(x - 1, y + dy);
                        neighbours.add(neighbourNode);
                    }
                    if (checkValidNode(x, y + dy)) {
                        Node neighbourNode = new Node(x, y + dy);
                        neighbours.add(neighbourNode);
                    }
                }
            }
        }
        return neighbours;
    }
    /**
     * 
     * @param initial
     * @param dx
     * @param dy
     * @return 
     */
    private Node jump(Node initial, int dx, int dy){
        int x = initial.getX();
        int y = initial.getY();
        Node diagonalForced = new Node(x+dx, y+dy);
        
        if(!checkValidNode(x+dx,y+dy)){
            return null;
        }
        considered[y][x] = true;
        if(diagonalForced.getX()==Finish.getX()&&diagonalForced.getY()==Finish.getY()){
            return Finish;
        }
   
        if(dx!=0&&dy!=0){
            if(forcedNeighbourCheck(diagonalForced,dx,dy)){
                return diagonalForced;
            }
            if(jump(diagonalForced,dx,0)!=null||jump(diagonalForced,0,dy)!=null){
                return diagonalForced;
            }
        } else {
            if(forcedNeighbourCheck(diagonalForced,dx,dy)){
                return diagonalForced;
            }
        }
        return jump(diagonalForced,dx,dy);
    }
    /**
     * Checks for forced neighbours of initial node.
     * @param initial node whose forced neighbours we check
     * @param dx movement direction in the x-axis
     * @param dy y-axis movement-
     * @return true if algorithm needs to add new jump point because of forced neighbours, false o/w
     */
    private boolean forcedNeighbourCheck(Node initial, int dx, int dy){
        int x = initial.getX();
        int y = initial.getY();
        //diagonal forced neighbour check
        if(dx!=0&& dy!=0){
            if(!checkValidNode(x - dx,y) && checkValidNode(x-dx,y+dy)){
                return true;
            }
            if(!checkValidNode(x, y - dy) && checkValidNode(x+dx,y-dy)) {
                return true;
            }
        }
        //horizontal forced neighbour check
        else if(dx!=0){
            if (!checkValidNode(x, y + 1) && checkValidNode(x+dx,y+1)) {
                return true;
            }
            if (!checkValidNode(x, y - 1) && checkValidNode(x+dx,y-1)) {
                return true;
            }
        }
        // vertical forced neighbour check
        else if(dy!=0){
            if (!checkValidNode(x - 1, y) && checkValidNode(x+1,y+dy)) {
                return true;
            }
            if (!checkValidNode(x + 1, y) && checkValidNode(x-1,y+dy)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkValidNode(int x, int y) {
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

    public void markPath() {
        Node previous = Finish;
        while (previous.getPrevious() != null) {
            map[previous.getY()][previous.getX()] = 'J';
            previous = previous.getPrevious();
        }
    }

    public int getCount() {
        return finalcount;
    }
    
    public boolean[][] getClosed() {
        return closed;
    }
    
    public boolean[][] getConsidered() {
        return considered;
    }
}
