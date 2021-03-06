/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.logic;

import java.nio.file.Paths;
import java.util.Scanner;
import visualizer.datastructures.DynamicList;
import visualizer.datastructures.Node;

/**
 *
 * @author tuukk
 * Logic class allows for reading the map file and returning a char array which is used in further calculation
 * Also reads the scenario files and returns a list of the respective start and finish nodes of each scenario for the performance testing class to run.
 */
public class Logic{
    
    public Logic(){
    }
    
    public char[][] mapReader(String filename){
        try (Scanner reader = new Scanner(Paths.get("map/"+filename))) {
            char[][] map = new char[512][512];
            int j = 0;
            while (reader.hasNextLine()) {
                String rivi = reader.nextLine();
                for(int i = 0;i<512;i++){
                    map[j][i]=rivi.charAt(i);
                }
                j++;
            }
            return map;
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        return null;
    }
    /**
     * Gets specified map's associated movingai labs scenarios.
     * @param filename name of file to find scenarios of. 
     * @param scenarioNumber How many different scenario sets do we want to run, each set contains around 10 scenarios.
     * @return DynamicList of the start and finish nodes of the different scenarios.
     */
    public DynamicList scenarioGetter(String filename, int scenarioNumber){
        DynamicList scenarios = new DynamicList();
        try(Scanner reader = new Scanner(Paths.get("map/"+filename))){
            while(reader.hasNextLine()){
                String rivi = reader.nextLine();
                String[] parts = rivi.split("\\s+");
                if(Integer.valueOf(parts[0]) > scenarioNumber){
                    return scenarios;
                }
                int xStart = Integer.valueOf(parts[4]);
                int yStart = Integer.valueOf(parts[5]);
                Node start = new Node(xStart,yStart);
                
                int xFinish = Integer.valueOf(parts[6]);
                int yFinish = Integer.valueOf(parts[7]);
                double pathLength = Double.valueOf(parts[8]);
                Node finish = new Node(xFinish,yFinish);
                finish.setMinDistance(pathLength);
                
                scenarios.add(start);
                scenarios.add(finish);
            }
            return scenarios;
        } catch (Exception e) {
                System.out.println("Virhe: asd" + e.getMessage());
        }
        return null;
    }
}
