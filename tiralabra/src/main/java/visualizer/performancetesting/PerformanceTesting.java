/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.performancetesting;
import java.util.Arrays;
import visualizer.algorithms.*;
import visualizer.datastructures.DynamicList;
import visualizer.logic.Logic;

/**
 *
 * @author tuukk
 * Unfortunately had to recycle code here, because I have not implemented the algorithm as an abstract class.
 */
public class PerformanceTesting {
    Logic logic = new Logic();
    char[][] map;
    DynamicList testNodes;
    
    public PerformanceTesting(String filename){
        this.testNodes = logic.scenarioGetter(filename + ".scen", 20);
        this.map = logic.mapReader(filename);
    }
    
    public long aStarPerformance(){
        long time = 0;
        
        for(int i = 0; i < testNodes.size() - 1; i++){
            Astar algorithm = new Astar(map, testNodes.get(i), testNodes.get(i + 1));
                algorithm.findPath();
                long now = System.nanoTime();
                algorithm.findPath();
                long end = System.nanoTime();
            time = time + (end-now);
        }
        return time;
    }
    
    public long dijkstraPerformance() {
        long time = 0;

        for (int i = 0; i < testNodes.size() - 1; i++) {

            Astar algorithm = new Astar(map, testNodes.get(i), testNodes.get(i + 1), true);    
                algorithm.findPath();
                long now = System.nanoTime();
                algorithm.findPath();
                long end = System.nanoTime();
            time = time + (end-now);
        }
        return time;
    }
    
    public long JPSPerformance() {
        long time = 0;

        for (int i = 0; i < testNodes.size() - 1; i++) {
            JPS algorithm = new JPS(map, testNodes.get(i), testNodes.get(i + 1));
                algorithm.findPath();
                long now = System.nanoTime();
                algorithm.findPath();
                long end = System.nanoTime();
                time = time + (end - now);
        }
        return time;
    }
}
