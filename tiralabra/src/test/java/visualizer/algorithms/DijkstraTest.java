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

/**
 *
 * @author tuukk
 */
public class DijkstraTest {
    Dijkstra algorithm;
    private final double tolerance = 1;
    public DijkstraTest() {
        char[][] map = new char[100][100];
        algorithm = new Astar(map);
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
     * Test of findPath method, of class Dijkstra.
     */
    @Test
    public void testFindPath() {
        System.out.println("findPath");
        Dijkstra instance = null;
        instance.findPath();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
