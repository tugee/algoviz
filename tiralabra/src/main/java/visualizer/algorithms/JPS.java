/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.algorithms;
import java.util.ArrayList;
import visualizer.datastructures.MinHeap;
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
    private MinHeap pq;
    private int height;
    private int width;
    private int[] directions = {0, -1, 1};
    private boolean[][] considered;
    
    public JPS(char[][] map, Node start, Node finish) {
        this.height = map.length;
        this.width = map[0].length;
        this.pq = new MinHeap(height * width);
        this.map = map;
        this.distanceFromBeginning = new double[height][width];
        this.closed = new boolean[height][width];
        this.considered = new boolean[height][width];

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
        int xDistance = finish.getX()-x;
        int yDistance = finish.getY()-y;
        double distance = Math.sqrt(xDistance*xDistance + yDistance*yDistance);
        return distance;
    }
    
    public double findPath() {
        distanceFromBeginning[Start.getY()][Start.getX()] = 0;
        Start.setMinDistance(0);
        pq.add(Start);
        System.out.println(Start.getX()+" "+Start.getY());
        while(!pq.isEmpty()) {
            Node node = pq.poll();
//            System.out.println("Minheap is currently processing "+node.getX() + " " + node.getY());
            if (node.equals(Finish)) {
//                System.out.println("HELLO!");
                markPath(node);
                return distanceFromBeginning[node.getY()][node.getX()];
            }

            closed[node.getY()][node.getX()] = true;
            ArrayList<Node> successors = identifySuccessors(node);
            for(Node next : successors){
                if(next.equals(Finish)){
                    System.out.println("HELLO!");
                }
                int neighbourY = next.getY();
                int neighbourX = next.getX();
                
                
                double currentDistance = distanceFromBeginning[neighbourY][neighbourX];
                double newDistance = node.getMinDistance() + heuristicDistanceEnd(neighbourX,neighbourY,node);

                if (newDistance <= currentDistance) {
//                    System.out.println("Added");
                    distanceFromBeginning[neighbourY][neighbourX] = newDistance;
                    Node newNode = new Node(neighbourX, neighbourY);
//                    System.out.println(neighbourX + " new Neighbour " + neighbourY);
                    newNode.setHeuristicDistanceEnd(heuristicDistanceEnd(neighbourX, neighbourY, Finish));
                    newNode.setMinDistance(newDistance);
                    newNode.setPrevious(node);
                    pq.add(newNode);
                }
            }
        }
        System.out.println(Finish.getX() + " " + Finish.getY());
        return 0;
    }
    
    public ArrayList<Node> identifySuccessors(Node current){
        ArrayList<Node> successors = new ArrayList<>();
        ArrayList<Node> neighbours = identifyNeighbours(current);
//        System.out.println("neighbour count " + neighbours.size());
            for(Node neighbour : neighbours){
//                System.out.println(neighbour.getX() + " neighbour XY " + neighbour.getY());
                int dirY = neighbour.getY()-current.getY();
                int dirX = neighbour.getX()-current.getX();
                if(dirX == 0 & dirY == 0){
                    continue;
                }
//                System.out.println(dirX + "direction succession" + dirY);
//                System.out.println("neighbour x "+neighbour.getX()+" "+neighbour.getY());
//                System.out.println(dir[0]+" "+dir[1]);
                Node jumpPoint = jump(current,dirX,dirY);
                if(jumpPoint!=null){
                    if(closed[jumpPoint.getY()][jumpPoint.getX()]==true){
                        continue;
                    }
//                    System.out.println("reached");
//                    System.out.println("jump point added: "+ jumpPoint.getX() + " " + jumpPoint.getY());
                    successors.add(jumpPoint);
                }
            }
//            System.out.println("Returning " + successors.size());
        return successors;
    }
    
    /**
     * @param current The node neighbours of which we want to identify
     * @return list of nodes, which are our list of pruned neighbours
     */
    public ArrayList<Node> identifyNeighbours(Node current){
        ArrayList<Node> neighbours = new ArrayList<>();
        
        int x = current.getX();
        int y = current.getY();
        
        if(current.getPrevious()==null){
//            System.out.println("We havereached a point where we need to generate new leads!");
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
//            System.out.println("Desparately trying to prune something");
            int[] direction = current.parentDirection();
//            System.out.println(current.getPrevious().getX() + " parent " + current.getPrevious().getY());
            int dx = direction[0];
            int dy = direction[1];
//            System.out.println(dx+" "+ dy);

            if(dx!=0 && dy!=0){
//                System.out.println("diagonal pruning");
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
//                    System.out.println("horizontal pruning");
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
//                    System.out.println("vertical pruning");
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
    public Node jump(Node initial, int dx, int dy){
        int x = initial.getX();
        int y = initial.getY();
//        System.out.println(dx+" distance "+dy);
//        System.out.println(x+" jumpcandidate "+y);
        Node diagonalForced = new Node(x+dx, y+dy);
        
        if(!checkValidNode(x+dx,y+dy)){
//            System.out.println("Problems!");
            return null;
        }
        considered[y][x] = true;
        if(diagonalForced.getX()==Finish.getX()&&diagonalForced.getY()==Finish.getY()){
//            System.out.println(initial.getX()+" asd "+initial.getY());
//            System.out.println("REACHED" +x + y);
//            System.out.println(diagonalForced.getX()+" "+diagonalForced.getY());
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
    public boolean forcedNeighbourCheck(Node initial, int dx, int dy){
        int x = initial.getX();
        int y = initial.getY();
        //diagonal forced neighbour check
        if(dx!=0&& dy!=0){
            if(!checkValidNode(x - dx,y) && checkValidNode(x-dx,y+dy)){
//                System.out.println((x-dx)+" forced neighbour " + (y+dy));
                return true;
            }
            if(!checkValidNode(x, y - dy) && checkValidNode(x+dx,y-dy)) {
//                System.out.println((x + dx) + " forced neighbour " + (y - dy));
                return true;
            }
        }
        //horizontal forced neighbour check
        else if(dx!=0){
            if (!checkValidNode(x, y + 1) && checkValidNode(x+dx,y+1)) {
//                System.out.println((x + dx) + " forced neighbour " + (y + 1));
                return true;
            }
            if (!checkValidNode(x, y - 1) && checkValidNode(x+dx,y-1)) {
//                System.out.println((x + dx) + " forced neighbour " + (y - 1));
                return true;
            }
        }
        // vertical forced neighbour check
        else if(dy!=0){
            if (!checkValidNode(x - 1, y) && checkValidNode(x+1,y+dy)) {
//                System.out.println((x - 1) + " forced neighbour " + (y + dy));
                return true;
            }
            if (!checkValidNode(x + 1, y) && checkValidNode(x-1,y+dy)) {
//                System.out.println((x + 1) + " forced neighbour " + (y + dy));
                return true;
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
    public boolean[][] getConsidered() {
        return considered;
    }
}
