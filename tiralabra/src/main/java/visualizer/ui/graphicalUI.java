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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import visualizer.algorithms.Astar;
import visualizer.algorithms.Dijkstra;
import visualizer.datastructures.Node;
import visualizer.logic.Logic;

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
    
    @Override
    public void start(Stage window) {
        Canvas drawable = new Canvas(520, 520);
        map = new char[512][512];
        drawer = drawable.getGraphicsContext2D();
        BorderPane setting = new BorderPane();
        
        VBox buttons = new VBox();
        Button aStarFinder = new Button("Find path (A*)");
        Button dijkstraFinder = new Button("Find path (Dijkstra)");
        Button clearField = new Button("Clear field");
        Button benchmark = new Button("Benchmark");
        ChoiceBox choiceBox = new ChoiceBox();
        Label settledLabel = new Label("Nodes accessed: 0");
        Label timeTaken = new Label("Took: 0 ms");
        Label pathLength = new Label("Path length: 0");
        // add icons
        choiceBox.getItems().add("Start");
        choiceBox.getItems().add("End");
        choiceBox.getItems().add("Wall");
        
        ChoiceBox mapBox = new ChoiceBox();
        mapBox.getItems().add("user.map");
        mapBox.getItems().add("Paris_0_512.map");
        mapBox.getItems().add("Moscow_2_512.map");
        mapBox.getItems().add("Berlin_0_512.map");
        mapBox.getItems().add("maze512-16-6.map");

        buttons.getChildren().add(aStarFinder);
        buttons.getChildren().add(dijkstraFinder);
        buttons.getChildren().add(clearField);
        buttons.getChildren().add(choiceBox);
        buttons.getChildren().add(mapBox);
        buttons.getChildren().add(benchmark);
        buttons.getChildren().add(settledLabel);
        buttons.getChildren().add(timeTaken);
        buttons.getChildren().add(pathLength);
        buttons.setSpacing(10);
        
        setting.setLeft(buttons);
        setting.setCenter(drawable);
        
        drawer.setFill(Color.WHITE);
        drawer.clearRect(0, 0, 512, 512);
        //need to separate OnDragged and OnClicked
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
                drawer.setFill(Color.YELLOW);
                drawer.fillRect((int) kohtaX, (int) kohtaY, 6, 6);
            }
        });
        
        aStarFinder.setOnAction((event)->{
            Astar algorithm = new Astar(map,start,finish);
            long now = System.nanoTime();
            double pathlength = algorithm.findPath();
            long end = System.nanoTime();
            map = algorithm.finalMap();
            settledLabel.setText("Nodes accessed: " + algorithm.getCount());
            timeTaken.setText("Took: "+((end-now)/1000000)+" ms");
            pathLength.setText("Path length: " + pathlength);
            boolean[][] settled = algorithm.getClosed();
            for(int i = 0; i<512;i++){
                for(int j = 0;j<512;j++){
                    if(map[i][j]=='A'){
                        drawer.setFill(Color.RED);
                        drawer.fillRect(j, i, 3, 3);
                    } else if(settled[i][j]==true && map[i][j]!='D'){
                        drawer.setFill(Color.CYAN);
                        drawer.fillRect(j, i, 1, 1);
                    }
                }
            }
        });
        
        dijkstraFinder.setOnAction((event) -> {
            Dijkstra algorithm = new Dijkstra(map, start, finish);
            long now = System.nanoTime();
            double pathlength = algorithm.findPath();
            long end = System.nanoTime();
            settledLabel.setText("Nodes accessed: " + algorithm.getCount());
            timeTaken.setText("Took: " + ((end - now) / 1000000) + " ms");
            pathLength.setText("Path length: "+ pathlength);
            map = algorithm.finalMap();
            boolean[][] settled = algorithm.getClosed();
            for (int i = 0; i < 512; i++) {
                for (int j = 0; j < 512; j++) {
                    if (map[i][j] == 'D') {
                        drawer.setFill(Color.MAGENTA);
                        drawer.fillRect(j, i, 3, 3);
                    } else if (settled[i][j] == true && map[i][j]!='A') {
                        drawer.setFill(Color.ORANGE);
                        drawer.fillRect(j, i, 1, 1);
                    }
                }
            }
        });
        
        benchmark.setOnAction((event)->{
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
        });
        
        clearField.setOnAction((event)-> {
            drawer.setFill(Color.WHITE);
            drawer.clearRect(0, 0, 520, 520);
            drawer.setFill(Color.GREEN);
            drawer.fillRect(start.getX(), start.getY(), 6, 6);
            drawer.setFill(Color.YELLOW);
            drawer.fillRect(finish.getX(), finish.getY(), 6, 6);
            map = new char[512][512];
        });
        
        Scene view = new Scene(setting);
        
        window.setScene(view);
        window.setTitle("Hei Maailma!");
        window.show();
    }
}
