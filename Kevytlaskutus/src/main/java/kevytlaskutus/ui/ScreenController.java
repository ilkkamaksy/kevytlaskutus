package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ScreenController {
    private HashMap<String, BorderPane> screenMap = new HashMap<>();
    private Scene main;
    private HBox bottomMenu;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void addScreen(String name, BorderPane pane){
         screenMap.put(name, pane);
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
        main.setRoot( screenMap.get(name) );
    }
    
    public HashMap<String, BorderPane> getScreenMap() {
        return this.screenMap;
    }
}