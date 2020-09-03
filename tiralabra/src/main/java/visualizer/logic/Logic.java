/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.logic;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import visualizer.datastructures.DynamicList;
import visualizer.datastructures.Node;

/**
 *
 * @author tuukk
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
            System.out.println("Virhe:  kakka" + e.getMessage());
        }
        return null;
    }
    
    public DynamicList scenarioGetter(String filename){
        DynamicList scenarios = new DynamicList();
        try(Scanner reader = new Scanner(Paths.get("map/"+filename))){
            int scenarionumber = 0;
            while(reader.hasNextLine()){
                String rivi = reader.nextLine();
                String[] parts = rivi.split("\\s+");
                if(Integer.valueOf(parts[0]) > scenarionumber){
                    return scenarios;
                }
                int xStart = Integer.valueOf(parts[4]);
                int yStart = Integer.valueOf(parts[5]);
                System.out.println(yStart);
                Node start = new Node(xStart,yStart);
                
                int xFinish = Integer.valueOf(parts[6]);
                int yFinish = Integer.valueOf(parts[7]);
                System.out.println(yFinish);
                Node finish = new Node(xFinish,yFinish);
                
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
