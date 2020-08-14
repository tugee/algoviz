/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.logic;

import java.nio.file.Paths;
import java.util.Scanner;

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
            System.out.println("Virhe: " + e.getMessage());
        }
        return null;
    }
}
