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
    
    public NodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getX method, of class Node.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Node instance = null;
        int expResult = 0;
        int result = instance.getX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getY method, of class Node.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Node instance = null;
        int expResult = 0;
        int result = instance.getY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPriority method, of class Node.
     */
    @Test
    public void testGetPriority() {
        System.out.println("getPriority");
        Node instance = null;
        int expResult = 0;
        int result = instance.getPriority();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinDistance method, of class Node.
     */
    @Test
    public void testGetMinDistance() {
        System.out.println("getMinDistance");
        Node instance = null;
        int expResult = 0;
        int result = instance.getMinDistance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrevious method, of class Node.
     */
    @Test
    public void testGetPrevious() {
        System.out.println("getPrevious");
        Node instance = null;
        Node expResult = null;
        Node result = instance.getPrevious();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of visit method, of class Node.
     */
    @Test
    public void testVisit() {
        System.out.println("visit");
        Node instance = null;
        instance.visit();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVisited method, of class Node.
     */
    @Test
    public void testGetVisited() {
        System.out.println("getVisited");
        Node instance = null;
        boolean expResult = false;
        boolean result = instance.getVisited();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compare method, of class Node.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Node o1 = null;
        Node o2 = null;
        Node instance = null;
        int expResult = 0;
        int result = instance.compare(o1, o2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
