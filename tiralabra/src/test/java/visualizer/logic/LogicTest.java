/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.logic;

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
public class LogicTest {
    
    public LogicTest() {
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
     * Test of mapReader method, of class Logic.
     */
    @Test
    public void testMapReader() {
        System.out.println("mapReader");
        Logic instance = new Logic();
        instance.mapReader("user.map");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}