/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.algorithms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import visualizer.datastructures.DynamicList;
import visualizer.datastructures.Node;

/**
 *
 * @author tuukk
 */
public class JPSTest {
    
    public JPSTest() {
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
     * Test of heuristicDistanceEnd method, of class JPS.
     */
    @Test
    public void testHeuristicDistanceEnd() {
        System.out.println("heuristicDistanceEnd");
        int x = 0;
        int y = 0;
        Node finish = null;
        JPS instance = null;
        double expResult = 0.0;
        double result = instance.heuristicDistanceEnd(x, y, finish);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findPath method, of class JPS.
     */
    @Test
    public void testFindPath() {
        System.out.println("findPath");
        JPS instance = null;
        double expResult = 0.0;
        double result = instance.findPath();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of identifySuccessors method, of class JPS.
     */
    @Test
    public void testIdentifySuccessors() {
        System.out.println("identifySuccessors");
        Node current = null;
        JPS instance = null;
        DynamicList expResult = null;
        DynamicList result = instance.identifySuccessors(current);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of identifyNeighbours method, of class JPS.
     */
    @Test
    public void testIdentifyNeighbours() {
        System.out.println("identifyNeighbours");
        Node current = null;
        JPS instance = null;
        DynamicList expResult = null;
        DynamicList result = instance.identifyNeighbours(current);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of jump method, of class JPS.
     */
    @Test
    public void testJump() {
        System.out.println("jump");
        Node initial = null;
        int dx = 0;
        int dy = 0;
        JPS instance = null;
        Node expResult = null;
        Node result = instance.jump(initial, dx, dy);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of forcedNeighbourCheck method, of class JPS.
     */
    @Test
    public void testForcedNeighbourCheck() {
        System.out.println("forcedNeighbourCheck");
        Node initial = null;
        int dx = 0;
        int dy = 0;
        JPS instance = null;
        boolean expResult = false;
        boolean result = instance.forcedNeighbourCheck(initial, dx, dy);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkValidNode method, of class JPS.
     */
    @Test
    public void testCheckValidNode() {
        System.out.println("checkValidNode");
        int x = 0;
        int y = 0;
        JPS instance = null;
        boolean expResult = false;
        boolean result = instance.checkValidNode(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finalMap method, of class JPS.
     */
    @Test
    public void testFinalMap() {
        System.out.println("finalMap");
        JPS instance = null;
        char[][] expResult = null;
        char[][] result = instance.finalMap();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCount method, of class JPS.
     */
    @Test
    public void testGetCount() {
        System.out.println("getCount");
        JPS instance = null;
        int expResult = 0;
        int result = instance.getCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClosed method, of class JPS.
     */
    @Test
    public void testGetClosed() {
        System.out.println("getClosed");
        JPS instance = null;
        boolean[][] expResult = null;
        boolean[][] result = instance.getClosed();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConsidered method, of class JPS.
     */
    @Test
    public void testGetConsidered() {
        System.out.println("getConsidered");
        JPS instance = null;
        boolean[][] expResult = null;
        boolean[][] result = instance.getConsidered();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
