/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizer.ui;

import static java.lang.Thread.sleep;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import visualizer.algorithms.Astar;
import visualizer.algorithms.JPS;
import visualizer.datastructures.Node;
import visualizer.logic.Logic;
import visualizer.performancetesting.PerformanceTesting;

/**
 *
 * @author tuukk
 */
public class graphicalUI extends Application{
    
    char[][] map;
    Logic logic = new Logic();
    AnimationTimer loop;
    GraphicsContext drawer;
    Node start = new Node(0,0);
    Node finish = new Node(511,511);
    boolean canvasStart = false;
    boolean canvasFinish = false;
    PerformanceTesting test;
    
    @Override
    public void start(Stage window) {
        Canvas drawable = new Canvas(520, 520);
        map = new char[512][512];
        drawer = drawable.getGraphicsContext2D();
        BorderPane setting = new BorderPane();
        
        VBox buttons = new VBox();
        Button aStarFinder = new Button("Find path (A*)");
        Button dijkstraFinder = new Button("Find path (Dijkstra)");
        Button jpsFinder = new Button("Find path (JPS)");
        
        Button clearField = new Button("Clear field");
        Button loader = new Button("Load map");
        Button benchmark = new Button("Run performance tests");
        ChoiceBox choiceBox = new ChoiceBox();
        Label dijkstraTime = new Label("Dijsktra took: 0 ms");
        Label aStarTime = new Label("A* took: 0 ms");
        Label JPSTime = new Label("JPS took: 0 ms");
        Label pathLength = new Label("Path length: 0");
        Label benchmarksTime = new Label("");
        // add icons
        choiceBox.getItems().add("Start");
        choiceBox.getItems().add("End");
        choiceBox.getItems().add("Wall");
        choiceBox.setValue("Wall");
        
        ChoiceBox mapBox = new ChoiceBox();
        mapBox.getItems().add("user.map");
        mapBox.getItems().add("Paris_0_512.map");
        mapBox.getItems().add("Moscow_2_512.map");
        mapBox.getItems().add("Berlin_0_512.map");
        mapBox.getItems().add("maze512-16-6.map");
        mapBox.setValue("user.map");

        buttons.getChildren().add(aStarFinder);
        buttons.getChildren().add(dijkstraFinder);
        buttons.getChildren().add(jpsFinder);
        buttons.getChildren().add(clearField);
        buttons.getChildren().add(choiceBox);
        buttons.getChildren().add(mapBox);
        buttons.getChildren().add(loader);
        buttons.getChildren().add(dijkstraTime);
        buttons.getChildren().add(aStarTime);
        buttons.getChildren().add(JPSTime);
        buttons.getChildren().add(pathLength);
        buttons.getChildren().add(benchmark);
        buttons.getChildren().add(benchmarksTime);
        buttons.setSpacing(10);
        
        setting.setLeft(buttons);
        setting.setCenter(drawable);
        
        drawer.setFill(Color.WHITE);
        drawer.clearRect(0, 0, 512, 512);
        
        drawable.setOnMouseDragged((event) -> {
            double kohtaX = event.getX();
            double kohtaY = event.getY();
            String value = (String) choiceBox.getValue();
            if(value == "Wall") {
                drawer.setFill(Color.BLACK);
                drawer.fillRect((int) kohtaX, (int) kohtaY, 6, 6);
                for (int i = -3; i < 3; i++) {
                    for (int j = -3; j < 3; j++) {
                        map[(int) kohtaY + i][(int) kohtaX + j] = '@';
                    }
                }
            }

        });
        /**
         *
         * Allows user to set Start and End nodes on canvas, first checks whether drop down has correct option selected.
         */
        drawable.setOnMouseClicked((event)->{
            double kohtaX = event.getX();
            double kohtaY = event.getY();
            String value = (String) choiceBox.getValue();
            if (value == "Start") {
                drawer.setFill(Color.WHITE);
                drawer.fillRect(start.getX(), start.getY(), 6, 6);
                
                start = new Node((int) kohtaX, (int) kohtaY);

                drawer.setFill(Color.GREEN);
                drawer.fillRect((int) kohtaX, (int) kohtaY, 6, 6);
                
            } else if(value == "End"){
                drawer.setFill(Color.WHITE);
                drawer.fillRect(finish.getX(), finish.getY(), 6, 6);
                finish = new Node((int)kohtaX,(int)kohtaY);
                drawer.setFill(Color.ORCHID);
                drawer.fillRect((int) kohtaX, (int) kohtaY, 6, 6);
            }
        });
        
        aStarFinder.setOnAction((event)->{
            Astar algorithm = new Astar(map,start,finish);
            long now = System.nanoTime();
            double pathlength = algorithm.findPath();
            long end = System.nanoTime();
            if (pathlength != 0) {
                algorithm.markPath();
            }
            map = algorithm.finalMap();
            aStarTime.setText("A* took: "+((float)(end-now)/1000000)+" ms, Accessed nodes: "+algorithm.getCount());
            pathLength.setText("Path length: " + pathlength);
            boolean[][] settled = algorithm.getClosed();
            for(int i = 0; i<512;i++){
                for(int j = 0;j<512;j++){
                    if(map[i][j]=='A'){
                        drawer.setFill(Color.RED);
                        drawer.fillRect(j, i, 3, 3);
                    } else if(settled[i][j]==true && map[i][j]!='D' && map[i][j]!='J'){
                        drawer.setFill(Color.web("0x0000FF",0.3));
                        drawer.fillRect(j, i, 1, 1);
                    }
                }
            }
            drawer.setFill(Color.GREEN);
            drawer.fillRect(start.getX(), start.getY(), 6, 6);
            drawer.setFill(Color.ORCHID);
            drawer.fillRect(finish.getX(), finish.getY(), 6, 6);
        });
        
        dijkstraFinder.setOnAction((event) -> {
            Astar algorithm = new Astar(map, start, finish,true);
            long now = System.nanoTime();
            double pathlength = algorithm.findPath();
            long end = System.nanoTime();
            if(pathlength!=0){
                algorithm.markPath();
            }
            dijkstraTime.setText("Dijkstra took: " + ((float)(end - now) / 1000000)+" ms, Accessed nodes: "+algorithm.getCount());
            pathLength.setText("Path length: "+ pathlength);
            map = algorithm.finalMap();
            boolean[][] settled = algorithm.getClosed();
            for (int i = 0; i < 512; i++) {
                for (int j = 0; j < 512; j++) {
                    if (map[i][j] == 'D') {
                        drawer.setFill(Color.FUCHSIA);
                        drawer.fillRect(j, i, 3, 3);
                    } else if (settled[i][j] == true && map[i][j]!='A' && map[i][j]!='J') {
                        drawer.setFill(Color.web("#FF7F50",0.3));
                        drawer.fillRect(j, i, 1, 1);
                    }
                }
            }
            drawer.setFill(Color.GREEN);
            drawer.fillRect(start.getX(), start.getY(), 6, 6);
            drawer.setFill(Color.ORCHID);
            drawer.fillRect(finish.getX(), finish.getY(), 6, 6);
        });
        
        jpsFinder.setOnAction((event) -> {
            JPS algorithm = new JPS(map, start, finish);
            long now = System.nanoTime();
            double pathlength = algorithm.findPath();
            long end = System.nanoTime();
            if (pathlength != 0) {
                algorithm.markPath();
            }
            map = algorithm.finalMap();
            JPSTime.setText("JPS took: " + ((float)(end - now) / 1000000)+" ms, Accessed nodes: "+algorithm.getCount());
            pathLength.setText("Path length: " + pathlength);
            boolean[][] settled = algorithm.getClosed();
            boolean[][] considered = algorithm.getConsidered();
            for (int i = 0; i < 512; i++) {
                for (int j = 0; j < 512; j++) {
                    if (map[i][j] == 'J') {
                        drawer.setFill(Color.GREEN);
                        drawer.fillRect(j, i, 5, 5);
                    } else if (settled[i][j] == true && map[i][j] != 'D' && map[i][j]!='A') {
                        drawer.setFill(Color.web("0x0000FF", 1));
                        drawer.fillRect(j, i, 1, 1);
                    } else if(considered[i][j]==true) {
                        drawer.setFill(Color.web("#808080", 0.1));
                        drawer.fillRect(j, i, 1, 1);
                    }
                }
            }
            drawer.setFill(Color.GREEN);
            drawer.fillRect(start.getX(), start.getY(), 6, 6);
            drawer.setFill(Color.ORCHID);
            drawer.fillRect(finish.getX(), finish.getY(), 6, 6);
        });
        
        loader.setOnAction((event)->{
            String value = (String) mapBox.getValue();
            map = logic.mapReader(value);
            drawer.clearRect(0,0,520,520);
            for (int i = 0; i < 512; i++) {
                for (int j = 0; j < 512; j++) {
                    if(map[i][j]=='@'){
                        drawer.setFill(Color.BLACK);
                        drawer.fillRect(j, i, 1, 1);
                    }
                    if(map[i][j]=='.'){
                        drawer.setFill(Color.WHITE);
                        drawer.fillRect(j, i, 1, 1);
                    }
                }
            }
            drawer.setFill(Color.GREEN);
            drawer.fillRect(start.getX(), start.getY(), 6, 6);
            drawer.setFill(Color.ORCHID);
            drawer.fillRect(finish.getX(), finish.getY(), 6, 6);
        });
        
        benchmark.setOnAction((event)->{
           String value = (String) mapBox.getValue();
           test = new PerformanceTesting(value);
           if("user.map".equals(value)){
               benchmarksTime.setText("Select non-user map to run benchmark tests");
               return;
           }
           long[] benchmarkResults = new long[3];
           benchmarkResults[2] = test.JPSPerformance();
           benchmarkResults[1] = test.aStarPerformance();
           benchmarkResults[0] = test.dijkstraPerformance();
           benchmarksTime.setText("Benchmarks took Dijkstra: " + benchmarkResults[0]/1000000.0 + " ms, A*: "+ benchmarkResults[1]/1000000.0 +" ms, JPS: "+benchmarkResults[2]/1000000.0+" ms");
        });
        
        
        clearField.setOnAction((event)-> {
            drawer.setFill(Color.WHITE);
            drawer.clearRect(0, 0, 520, 520);
            drawer.setFill(Color.GREEN);
            drawer.fillRect(start.getX(), start.getY(), 6, 6);
            drawer.setFill(Color.ORCHID);
            drawer.fillRect(finish.getX(), finish.getY(), 6, 6);
            map = new char[512][512];
        });
        
        Scene view = new Scene(setting);
        
        window.setScene(view);
        window.setTitle("Hei Maailma!");
        window.show();
    }
}
