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
public class DynamicListTest {
    DynamicList instance;
    
    public DynamicListTest() {
    }
    
    
    @Before
    public void setUp() {
        instance = new DynamicList();
        instance.add(new Node(10, 11));
        instance.add(new Node(10, 12));
        instance.add(new Node(10, 13));

    }
    
    /**
     * Test of size method, of class DynamicList.
     */
    @Test
    public void testSize() {
        int expResult = 3;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class DynamicList.
     */
    @Test
    public void testGet() {
        if(!instance.get(1).equals(new Node(10,12))){
            fail("Did not equal correct node");
        }
    }
    
}
