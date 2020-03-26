package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import kevytlaskutus.domain.*;
import kevytlaskutus.ui.*;

public class ScreenController {
    private HashMap<String, BorderPane> screenMap = new HashMap<>();
    private Scene main;
    private HBox bottomMenu;
    private Company currentCompany;
   
    public ScreenController(Scene main) {
        this.main = main;
        this.currentCompany = new Company();
    }

    public void addScreen(String name, BorderPane pane){
         screenMap.put(name, pane);
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void addGlobalBottomMenu(HBox menu) {
        this.bottomMenu = menu;
    }
    
    public void setCurrentCompany(Company company) {
        this.currentCompany = company;
    }
    
    public Company getCurrentCompany() {
        return this.currentCompany;
    }
    
    public void redDrawForm(Form form) {
//        form.redDraw();
    }
   
    public void activate(String name){
        BorderPane screen = screenMap.get(name);
        screen.setBottom(bottomMenu);
        main.setRoot( screen );
    }
   
}