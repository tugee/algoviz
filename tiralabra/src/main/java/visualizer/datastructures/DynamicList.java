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
public class DynamicList {
    Node[] nodeList = new Node[10];
    int values = 0;
    
    public void add(Node newNode){
        if(nodeList.length==values){
            this.grow();
        }
        nodeList[values] = newNode;
        
    }
    
    public void remove(){
        
    }
    
    public void grow(){
        Node[] nodeListNew = new Node[nodeList.length*2];
        for(int i = 0; i < nodeList.length;i++){
            nodeListNew[i]=nodeList[i];
        }
        nodeList = nodeListNew;
    }
    
}
