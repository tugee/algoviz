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
public class AstarTest {
    Astar algorithm;
    private final double tolerance = 1;
    public AstarTest() {
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

    @Test
    public void testSomeMethod() {
        double distance = algorithm.findPath();
        System.out.println(distance);
        assertEquals(140, distance,tolerance);
    }
    @Test
    public void wallTest() {
        char[][] map2 = new char[3][3];
        for(int i = 0; i<2;i++){
            map2[i][1] = '@';
        }
        Astar algorithmShort= new Astar(map2);
        double distance = algorithmShort.findPath();
        System.out.println(distance);
        map2 = algorithmShort.finalMap();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(map2[i][j] + " ");
            }
            System.out.println("");
        }
        assertEquals(4, distance, tolerance);
    }
    @Test
    public void impossibleTest() {
        char[][] map2 = new char[100][100];
        for(int i = 0; i<100;i++){
            map2[i][49] = '@';
        }
        for(int i = 0; i<100;i++){
            for(int j = 0; j<100;j++){
                System.out.print(map2[i][j]+" ");
            }
            System.out.println("");
        }
        Astar algorithmImpossible = new Astar(map2);
        double distance = algorithmImpossible.findPath();
        System.out.println(distance);
        assertEquals(0, distance, tolerance);
    }
    
}
