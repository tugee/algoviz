/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.datastructures;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tuukk
 */
public class NodeTest {
    Node testNode;
    double tolerance = 1e-6;
    public NodeTest() {
        testNode = new Node(10,20);
        testNode.setMinDistance(10);
        
    }

    /**
     * Test of getX method, of class Node.
     */
    @Test
    public void testGetX() {
        int result = testNode.getX();
        assertEquals(result,10);
    }

    /**
     * Test of getY method, of class Node.
     */
    @Test
    public void testGetY() {
        int result = testNode.getY();
        assertEquals(result,20);
    }


    /**
     * Test of getMinDistance method, of class Node.
     */
    @Test
    public void testGetMinDistance() {
        double result = testNode.getMinDistance();
        assertEquals(result, 10,tolerance);
    }

    /**
     * Test of getPrevious method, of class Node.
     */
    @Test
    public void testGetPrevious() {
        System.out.println("getPrevious");
        Node previous = new Node(10, 10);
        testNode.setPrevious(previous);
        Node result = testNode.getPrevious();
        assertEquals(previous, result);
    }

}
