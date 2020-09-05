/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.algorithms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
public class JPSTest {
    int side = 512;
    double delta = 1.0e-6;
    Logic logic = new Logic();
    public JPSTest() {
    }

    /**
     * Test of findPath method, of class JPS.
     */
    @Test
    public void testFindDiagonalPath() {
        System.out.println("findPath");
        char[][] map = new char[side][side];
        JPS instance = new JPS(map, new Node(0, 0), new Node(511, 511));
        double result = instance.findPath();
        assertEquals(result, 722.663130373, delta);
    }
    
    @Test
    public void findsSameLengthAsDijkstraTest() {
        char[][] map = logic.mapReader("Moscow_2_512.map");
        DynamicList scenarios = logic.scenarioGetter("Moscow_2_512.map.scen",10);
        for(int i = 0; i < scenarios.size(); i = i + 2){
            Node start = scenarios.get(i);
            Node finish = scenarios.get(i+1);
            
            JPS instance = new JPS(map, start, finish);
            double lengthJPS = instance.findPath();
            
            Astar algorithm = new Astar(map,start,finish,true);
            double lengthDijsktra = algorithm.findPath();
            
            boolean eq = Math.abs(lengthJPS - lengthDijsktra) < delta;
            if (!eq) {
                fail("The pathfinder did not find the correct path on the map");
            }           
        }
    }

    @Test
    public void unreachableGoalTest() {
        char[][] map = new char[side][side];
        for(int i = 0; i < 512; i++){
            map[20][i] = '@';
        }
        JPS instance = new JPS(map,new Node(10,10), new Node (10, 30));
        double result = instance.findPath();
        assertEquals(0.0, result,delta);
    }
    
    @Test
    public void visitedNodesWhenOnSameLine() {
        char[][] map = new char[side][side];
        JPS instance = new JPS(map, new Node(10, 10), new Node(10, 30));
        int visitedNodes = instance.getCount();
        assertEquals(0, visitedNodes);
    }
    
    @Test
    public void testAllOnMap(){
        char[][] map = logic.mapReader("Moscow_2_512.map");
        DynamicList scenarios = logic.scenarioGetter("Moscow_2_512.map.scen", 10);
        for(int i = 0; i < scenarios.size(); i = i + 2){
            JPS algorithm = new JPS(map, scenarios.get(i),scenarios.get(i+1));
            double pathLength = algorithm.findPath();
            boolean eq = Math.abs(pathLength - scenarios.get(i+1).getMinDistance()) < delta;
            if(!eq){
                fail("The pathfinder did not find the correct path on the map");
            }
        }
    }    
}
