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
        double distance = Math.sqrt(Math.pow(finish.getX() - x, 2) + Math.pow(finish.getY() - y, 2));
        return distance;
    }
    
    public double findPath() {
        distanceFromBeginning[Start.getY()][Start.getX()] = 0;
        Start.setMinDistance(0);
        pq.add(Start);
        System.out.println(Start.getX()+" "+Start.getY());
        while(!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.equals(Finish)) {
                markPath(node);
                return distanceFromBeginning[node.getY()][node.getX()];
            }

            closed[node.getY()][node.getX()] = true;
            for(Node next : identifySuccessors(node)){
                int neighbourY = next.getY();
                int neighbourX = next.getX();
                
                
                double currentDistance = distanceFromBeginning[neighbourY][neighbourX];
                double newDistance = node.getMinDistance() + heuristicDistanceEnd(neighbourX,neighbourY,next.getPrevious());

                if (newDistance < currentDistance) {
                    System.out.println("Added");
                    distanceFromBeginning[neighbourY][neighbourX] = newDistance;
                    Node newNode = new Node(neighbourX, neighbourY);
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
            for(Node neighbour : neighbours){
                considered[neighbour.getY()][neighbour.getX()]=true;
                System.out.println(neighbours.size());
                int[] dir = neighbour.parentDirection();
//                System.out.println("neighbour x "+neighbour.getX()+" "+neighbour.getY());
//                System.out.println(dir[0]+" "+dir[1]);
                Node jumpPoint = jump(current,dir[0],dir[1]);
                if(jumpPoint!=null){
                    System.out.println("reached");
                    jumpPoint.setPrevious(current);
                    successors.add(jumpPoint);
                }
            }
        return successors;
    }
    
    public ArrayList<Node> identifyNeighbours(Node current){
        ArrayList<Node> neighbours = new ArrayList<>();
        if(current.getPrevious()==null){
            for(int i = -1; i<2;i++){
                for(int j = -1; j<2; j++){
                    if(i==0&&j==0){
                        continue;
                    }
                    if(checkValidNode(current.getX()+i,current.getY()+j)){
                        Node neighbourNode = new Node(current.getX()+i, current.getY()+j);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);   
                    }
                }
            }
        } else {
            int[] direction = current.parentDirection();
            
            int dx = direction[0];
            int dy = direction[1];
            
            if(dx!=0 && dy!=0){
                    if(checkValidNode(current.getX()+dx,current.getY())){
                        Node neighbourNode = new Node(current.getX() + dx, current.getY());
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);
                    }
                    if (checkValidNode(current.getX(), current.getY()+dy)) {
                        Node neighbourNode = new Node(current.getX(), current.getY()+dy);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);
                    }
                    if (checkBlocked(current.getX(), current.getY(), -dx, 0) && checkValidNode(current.getX() -dx, current.getY()+dy)) {
                        Node neighbourNode = new Node(current.getX() -dx, current.getY()+dy);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);
                    }
                    if(checkBlocked(current.getX(),current.getY(),0,-dy) && checkValidNode(current.getX() +dx, current.getY()-dy)){
                        Node neighbourNode = new Node(current.getX()+dx, current.getY()-dy);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);
                    }
                    if(checkValidNode(current.getX()+dx,current.getY())){
                        Node neighbourNode = new Node(current.getX() + dx, current.getY() + dy);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);   
                    }
            }
            if(dx==0){
                if(checkValidNode(current.getX(),current.getY()+dy)){
                    if(checkBlocked(current.getX(),current.getY(),1,0) && checkValidNode(current.getX()+1,current.getY()+dy)){
                        Node neighbourNode = new Node(current.getX() + 1, current.getY() + dy);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);
                    }
                    if (checkBlocked(current.getX(), current.getY(), -1, 0) && checkValidNode(current.getX() - 1, current.getY() + dy)) {
                        Node neighbourNode = new Node(current.getX() - 1, current.getY() + dy);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);
                    }
                    Node neighbourNode = new Node(current.getX(), current.getY()+dy);
                    neighbourNode.setPrevious(current);
                    neighbours.add(neighbourNode);
                }
            }
            if(dy==0){
                if(checkValidNode(current.getX()+dx,current.getY())){
                    if(checkBlocked(current.getX(),current.getY(),0,1) && checkValidNode(current.getX() + dx, current.getY() + 1)){
                        Node neighbourNode = new Node(current.getX() + dx, current.getY() + 1);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);
                    }
                    if (checkBlocked(current.getX(), current.getY(), 0, -1) && checkValidNode(current.getX() + dx, current.getY() - 1)) {
                        Node neighbourNode = new Node(current.getX() + dx, current.getY() - 1);
                        neighbourNode.setPrevious(current);
                        neighbours.add(neighbourNode);
                    }
                    Node neighbourNode = new Node(current.getX() + dx, current.getY());
                    neighbourNode.setPrevious(current);
                    neighbours.add(neighbourNode);
                }
            }
        }
        return neighbours;
    }
    
    public Node jump(Node initial, int dx, int dy){
        int candidateX = initial.getX()+dx;
        int candidateY = initial.getY()+dy;
//        System.out.println(dx+" distance "+dy);
//        System.out.println(candidateX+" jumpcandidate "+candidateY);
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
        if(dx!=0&& dy!=0){
            if(checkBlocked(initial.getX(),initial.getY(),-dx,0)){
                return true;
            }
            if (checkBlocked(initial.getX(), initial.getY(), 0, -dy)) {
                return true;
            }
        }
        if(dx!=0){
            if (checkBlocked(initial.getX(), initial.getY(), 0, 1)) {
                return true;
            }
            if (checkBlocked(initial.getX(), initial.getY(), 0, -1)) {
                return true;
            }
        }
        if(dy!=0){
            if (checkBlocked(initial.getX(), initial.getY(), -1, 0)) {
                return true;
            }
            if (checkBlocked(initial.getX(), initial.getY(), 1, 0)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkBlocked(int x, int y,int dx, int dy){
        if (x+dx < 0 || y+dy < 0 || x >= width|| y >= height || x+dx >= width || y+dy >= height){
            return false;
        }
        if(map[y+dy][x+dx]=='@'){
            return true;
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
