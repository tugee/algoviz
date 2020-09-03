/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.logic;
import java.util.Arrays;
import visualizer.algorithms.*;
import visualizer.datastructures.DynamicList;

/**
 *
 * @author tuukk
 */
public class PerformanceTesting {
    Logic logic = new Logic();
    
    public PerformanceTesting(){   
    }
    public long[] runTestSuite(String filename){
        System.out.println(filename+".scen");
        DynamicList testNodes = logic.scenarioGetter(filename+".scen");
        char[][] map = logic.mapReader(filename);
        long aStarTime = 0;
        long dijkstraTime = 0;
        long JPSTime = 0;
        
        for(int i = 0; i < testNodes.size() - 1; i++){
            JPS jps = new JPS(map,testNodes.get(i),testNodes.get(i+1));
            long now = System.nanoTime();
            jps.findPath();
            long end = System.nanoTime();
            JPSTime = JPSTime + (end-now);
            
            Dijkstra dijkstra = new Dijkstra(map, testNodes.get(i), testNodes.get(i + 1));
            now = System.nanoTime();
            dijkstra.findPath();
            end = System.nanoTime();
            dijkstraTime = dijkstraTime + (end - now);
            
            Astar astar = new Astar(map, testNodes.get(i), testNodes.get(i + 1));
            now = System.nanoTime();
            astar.findPath();
            end = System.nanoTime();
            aStarTime = aStarTime + (end-now);
        }
        System.out.println(aStarTime/1000000 +" A*"+ dijkstraTime/1000000 + "Dijkstra, JPS: " + JPSTime/1000000 +" ms");
        long[] returnArray = {aStarTime/1000000,dijkstraTime/1000000,JPSTime/1000000};
        return returnArray;
    }
}
