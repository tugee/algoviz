/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.datastructures;

/**
 *
 * @author tuukk
 * We implement a List structure to allow for
 */
public class DynamicList{
    Node[] nodeList;
    int values;

    public DynamicList(){
        this.nodeList = new Node[10];
        this.values = 0;
    }
    
    public void add(Node newNode){
        if(nodeList.length==values){
            this.grow();
        }
        nodeList[values++] = newNode;
    }
    
    public int size(){
        return values;
    }
    
    public Node get(int index){
        if(index < values){
            return nodeList[index];
        }
        return null;
    }
    
    private void grow(){
        Node[] nodeListNew = new Node[nodeList.length*2];
        for(int i = 0; i < nodeList.length;i++){
            nodeListNew[i]=nodeList[i];
        }
        nodeList = nodeListNew;
    }
}
