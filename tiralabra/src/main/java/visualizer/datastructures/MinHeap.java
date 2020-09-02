/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.datastructures;

/**
 *
 * @author tuukk
 */
public class MinHeap {
    Node[] heap;
    int size;
    
    public MinHeap(int maxsize){
        this.size = 0;
        this.heap = new Node[maxsize + 1];
        Node init = new Node(-1,-1);
        heap[0] = init;
    }
    
    public void add(Node node){
        heap[++size] = node;
        int pos = size;
        while(heap[pos].compareTo(heap[parent(pos)]) < 0 ){
            swap(pos,parent(pos));
            pos = parent(pos);
        }
    }
    
    public Node poll(){
        Node first = heap[1];
        heap[1] = heap[size--];
        makeMinHeap(1);
        return first;
    }
    
    private boolean isLeaf(int position){
        if(position >= (this.size/2) && position <= size){
            return true;
        }
        return false;
    }
    
    private int parent(int pos){
        return pos/2;
    }
    
    private int leftChild(int pos){
        return 2*pos;
    }
    
    private int rightChild(int pos){
        return (2*pos) + 1;
    }
    
    private void swap(int firstPosition, int secondPosition){
        Node swap = heap[firstPosition];
        heap[firstPosition]=heap[secondPosition];
        heap[secondPosition]=swap;
    }
    
    private void makeMinHeap(int pos){
        Node current = heap[pos];
        Node leftNode = heap[leftChild(pos)];
        Node rightNode = heap[rightChild(pos)];
        
        if(!isLeaf(pos)&&!isEmpty()){
            if((current.compareTo(leftNode) > 0)|| (current.compareTo(rightNode) > 0)){
                if(leftNode.compareTo(rightNode) > 0){
                    swap(pos,rightChild(pos));
                    makeMinHeap(rightChild(pos));
                } else {
                    swap(pos, leftChild(pos));
                    makeMinHeap(leftChild(pos));
                }
            }
        }
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
}
