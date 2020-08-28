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
    private boolean[][] considered;
    
    public JPS(char[][] map, Node start, Node finish) {
        this.height = map.length;
        this.width = map[0].length;
        this.pq = new PriorityQueue<Node>(height * width);
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
            System.out.println("Minheap is currently processing "+node.getX() + " " + node.getY());
            if (node.equals(Finish)) {
                System.out.println("HELLO!");
                markPath(node);
                return distanceFromBeginning[node.getY()][node.getX()];
            }

            closed[node.getY()][node.getX()] = true;
            for(Node next : identifySuccessors(node)){
                if(next.equals(Finish)){
                    System.out.println("HELLO!");
                }
                int neighbourY = next.getY();
                int neighbourX = next.getX();
                
                
                double currentDistance = distanceFromBeginning[neighbourY][neighbourX];
                double newDistance = node.getMinDistance() + heuristicDistanceEnd(neighbourX,neighbourY,node);

                if (newDistance <= currentDistance) {
                    System.out.println("Added");
                    distanceFromBeginning[neighbourY][neighbourX] = newDistance;
                    Node newNode = new Node(neighbourX, neighbourY);
                    System.out.println(neighbourX + " " + neighbourY);
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
        System.out.println("neighbour count " + neighbours.size());
        for(Node neighbour: neighbours){
            System.out.println(neighbour.getX()+" neighbour XY "+ neighbour.getY());
        }
            for(Node neighbour : neighbours){
                
                int y = current.getY();
                int x = current.getX();
                int dirY = neighbour.getY()-current.getY();
                int dirX = neighbour.getX()-current.getX();
                if(dirX == 0 & dirY == 0){
                    continue;
                }
                System.out.println(dirX + "direction succession" + dirY);
//                System.out.println("neighbour x "+neighbour.getX()+" "+neighbour.getY());
//                System.out.println(dir[0]+" "+dir[1]);
                Node jumpPoint = jump(current,dirX,dirY);
                if(jumpPoint!=null){
                    System.out.println("reached");
                    System.out.println("jump point added: "+ jumpPoint.getX() + " " + jumpPoint.getY());
                    successors.add(jumpPoint);
                }
            }
            System.out.println("Returning " + successors.size());
        return successors;
    }
    
    public ArrayList<Node> identifyNeighbours(Node current){
        ArrayList<Node> neighbours = new ArrayList<>();
        if(current.getPrevious()==null){
            System.out.println("We havereached a point where we need to generate new leads!");
            for(int i = -1; i<2;i++){
                for(int j = -1; j<2; j++){
                    if(i==0&&j==0){
                        continue;
                    }
                    if(checkValidNode(current.getX()+i,current.getY()+j)){
                        Node neighbourNode = new Node(current.getX()+i, current.getY()+j);
                        neighbours.add(neighbourNode);   
                    }
                }
            }
        } else {
            System.out.println("Desparately trying to prune something");
            int[] direction = current.parentDirection();
            
            int dx = direction[0];
            int dy = direction[1];
            System.out.println(dx+" "+ dy);
            if(dx!=0 && dy!=0){
                System.out.println("diagonal pruning");
                    if(checkValidNode(current.getX()+dx,current.getY())){
                        Node neighbourNode = new Node(current.getX() + dx, current.getY());
                        neighbours.add(neighbourNode);
                    }
                    if (checkValidNode(current.getX(), current.getY() + dy)) {
                        Node neighbourNode = new Node(current.getX(), current.getY() + dy);
                        neighbours.add(neighbourNode);
                    }
                    if (!checkValidNode(current.getX() - dx, current.getY()) && checkValidNode(current.getX() - dx, current.getY() + dy)) {
                        Node neighbourNode = new Node(current.getX() - dx, current.getY() + dy);
                        neighbours.add(neighbourNode);
                    }
                    if(!checkValidNode(current.getX(),current.getY() - dy) && checkValidNode(current.getX() + dx, current.getY() - dy)){
                        Node neighbourNode = new Node(current.getX() + dx, current.getY() - dy);
                        neighbours.add(neighbourNode);
                    }
                    if(checkValidNode(current.getX() + dx,current.getY() + dy)){
                        Node neighbourNode = new Node(current.getX() + dx, current.getY() + dy);
                        neighbours.add(neighbourNode);   
                    }
            } else {
                if (dy == 0) {
                    System.out.println("horizontal pruning");
                    if (!checkValidNode(current.getX(), current.getY() + 1)) {
                        Node neighbourNode = new Node(current.getX() + dx, current.getY() + 1);
                        neighbours.add(neighbourNode);
                    }
                    if (!checkValidNode(current.getX(), current.getY() - 1)) {
                        Node neighbourNode = new Node(current.getX() + dx, current.getY() - 1);
                        neighbours.add(neighbourNode);
                    }
                    if (checkValidNode(current.getX() + dx, current.getY())) {
                        Node neighbourNode = new Node(current.getX() + dx, current.getY());
                        neighbours.add(neighbourNode);
                    }
                } else {
                    System.out.println("vertical pruning");
                    if (!checkValidNode(current.getX() + 1, current.getY())) {
                        Node neighbourNode = new Node(current.getX() + 1, current.getY() + dy);
                        neighbours.add(neighbourNode);
                    }
                    if (!checkValidNode(current.getX() - 1, current.getY())) {
                        Node neighbourNode = new Node(current.getX() - 1, current.getY() + dy);
                        neighbours.add(neighbourNode);
                    }
                    if (checkValidNode(current.getX(), current.getY() + dy)) {
                        Node neighbourNode = new Node(current.getX(), current.getY() + dy);
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
        int candidateX = initial.getX()+dx;
        int candidateY = initial.getY()+dy;
        System.out.println(dx+" distance "+dy);
        System.out.println(candidateX+" jumpcandidate "+candidateY);
        Node diagonalForced = new Node(candidateX, candidateY);
        if(!checkValidNode(candidateX,candidateY)){
            System.out.println("Problems!");
            return null;
        }
        considered[candidateY][candidateX] = true;
        if(candidateX==Finish.getX()&&candidateY==Finish.getY()){
            System.out.println(initial.getX()+" asd "+initial.getY());
            System.out.println("REACHED" +candidateX + candidateY);
            System.out.println(diagonalForced.getX()+" "+diagonalForced.getY());
            return diagonalForced;
        }
   
        if(dx!=0&&dy!=0){
            if(forcedNeighbourCheck(initial,dx,dy)){
                return diagonalForced;
            }
            if(jump(diagonalForced,dx,0)!=null||jump(diagonalForced,0,dy)!=null){
                return diagonalForced;
            }
        } else {
            if(forcedNeighbourCheck(initial,dx,dy)){
                return initial;
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
        //diagonal forced neighbour check
        if(dx!=0&& dy!=0){
            if(!checkValidNode(initial.getX() - dx,initial.getY()) && checkValidNode(initial.getX()-dx,initial.getY()+dy)){
                System.out.println((initial.getX()-dx)+" forced neighbour " + (initial.getY()+dy));
                return true;
            }
            if(!checkValidNode(initial.getX(), initial.getY() - dy) && checkValidNode(initial.getX()+dx,initial.getY()-dy)) {
                System.out.println((initial.getX() + dx) + " forced neighbour " + (initial.getY() - dy));
                return true;
            }
        }
        //horizontal forced neighbour check
        else if(dx!=0){
            if (!checkValidNode(initial.getX(), initial.getY() + 1) && checkValidNode(initial.getX()+dx,initial.getY()+1)) {
                System.out.println((initial.getX() + dx) + " forced neighbour " + (initial.getY() + 1));
                return true;
            }
            if (!checkValidNode(initial.getX(), initial.getY() - 1) && checkValidNode(initial.getX()+dx,initial.getY()-1)) {
                System.out.println((initial.getX() + dx) + " forced neighbour " + (initial.getY() - 1));
                return true;
            }
        }
        // vertical forced neighbour check
        else if(dy!=0){
            if (!checkValidNode(initial.getX() - 1, initial.getY()) && checkValidNode(initial.getX()-1,initial.getY()+dy)) {
                System.out.println((initial.getX() - 1) + " forced neighbour " + (initial.getY() + dy));
                return true;
            }
            if (!checkValidNode(initial.getX() + 1, initial.getY()) && checkValidNode(initial.getX()+1,initial.getY()+dy)) {
                System.out.println((initial.getX() + 1) + " forced neighbour " + (initial.getY() + dy));
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
