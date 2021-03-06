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
import visualizer.logic.Logic;
/**
 *
 * @author tuukk
 */
public class AstarTest {
    Astar algorithm;
    private final double tolerance = 1e-6;
    Logic logic = new Logic();
    public AstarTest() {
    }

    @Test
    public void diagonalTest() {
        char[][] map = new char[512][512];
        Astar algorithmDiag = new Astar(map,new Node(0,0),new Node(511,511));
        double distance = algorithmDiag.findPath();
        System.out.println(distance);
        assertEquals(722.66313, distance,tolerance);
    }
    @Test
    public void wallTest() {
        char[][] map2 = new char[3][3];
        for(int i = 0; i<2;i++){
            map2[i][1] = '@';
        }
        Astar algorithmShort= new Astar(map2,new Node(0,0),new Node(2,0));
        double distance = algorithmShort.findPath();
        System.out.println(distance);
        map2 = algorithmShort.finalMap();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(map2[i][j] + " ");
            }
            System.out.println("");
        }
        assertEquals(4.8284271, distance, tolerance);
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
        Astar algorithmImpossible = new Astar(map2,new Node(0,0),new Node(99,99));
        double distance = algorithmImpossible.findPath();
        System.out.println(distance);
        assertEquals(0, distance, tolerance);
    }
    
    @Test
    public void testOnMovingAIMap() {
        char[][] map = logic.mapReader("Moscow_2_512.map");
        DynamicList scenarios = logic.scenarioGetter("Moscow_2_512.map.scen", 10);
        for (int i = 0; i < scenarios.size(); i = i + 2) {
            Astar algorithm = new Astar(map, scenarios.get(i), scenarios.get(i + 1),false);
            double pathLength = algorithm.findPath();
            boolean eq = Math.abs(pathLength - scenarios.get(i + 1).getMinDistance()) < tolerance;
            if (!eq) {
                fail("The pathfinder did not find the correct path on the map");
            }
        }
    }
    
}
