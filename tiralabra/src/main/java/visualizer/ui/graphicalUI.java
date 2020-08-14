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
        
        ChoiceBox choiceBox = new ChoiceBox();
        // add icons
        choiceBox.getItems().add("Start");
        choiceBox.getItems().add("End");
        choiceBox.getItems().add("Wall");
        

        buttons.getChildren().add(aStarFinder);
        buttons.getChildren().add(dijkstraFinder);
        buttons.getChildren().add(clearField);
        buttons.getChildren().add(choiceBox);
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
            algorithm.findPath();
            map = algorithm.finalMap();
            boolean[][] settled = algorithm.getClosed();
            for(int i = 0; i<512;i++){
                for(int j = 0;j<512;j++){
                    if(map[i][j]=='P'){
                        drawer.setFill(Color.RED);
                        drawer.fillRect(j, i, 2, 2);
                    } else if(settled[i][j]==true){
                        drawer.setFill(Color.CYAN);
                        drawer.fillRect(j, i, 1, 1);
                    }
                }
            }
        });
        
        dijkstraFinder.setOnAction((event) -> {
            Dijkstra algorithm = new Dijkstra(map, start, finish);
            algorithm.findPath();
            map = algorithm.finalMap();
            boolean[][] settled = algorithm.getClosed();
            for (int i = 0; i < 512; i++) {
                for (int j = 0; j < 512; j++) {
                    if (map[i][j] == 'P') {
                        drawer.setFill(Color.MAGENTA);
                        drawer.fillRect(j, i, 2, 2);
                    } else if (settled[i][j] == true) {
                        drawer.setFill(Color.ORANGE);
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
