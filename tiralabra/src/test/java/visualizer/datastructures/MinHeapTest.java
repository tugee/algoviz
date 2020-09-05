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
public class MinHeapTest {
    
    public MinHeapTest() {
    }

    /**
     * Test of add method, of class MinHeap.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Node node = new Node(10,10);
        MinHeap instance = new MinHeap(10);
        instance.add(node);
        if(instance.isEmpty()){
            fail("Not empty");
        }
    }

    /**
     * Test of poll method, of class MinHeap.
     */
    @Test
    public void testPoll() {
        System.out.println("poll");
        Node node = new Node(10, 10);
        MinHeap instance = new MinHeap(10);
        instance.add(node);
        
        Node result = instance.poll();
        assertEquals(node, result);
    }

    /**
     * Test of isEmpty method, of class MinHeap.
     */
    @Test
    public void testIsEmpty() {
        MinHeap instance = new MinHeap(10);
        boolean result = instance.isEmpty();
        assertTrue(result);
    }
    
}
