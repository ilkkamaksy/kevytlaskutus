/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
/**
 *
 * @author ilkka
 */
public class BottomMenu {
    
    HBox menu;
    ScreenController screenController;
   
    public BottomMenu(ScreenController screenController) {
        this.screenController = screenController;
        this.menu = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        menu.getChildren().add(spacer);
    }
    
    public HBox getMenu() {
        return this.menu;
    }   
    
    public void addMenuItem(String buttonText) {
        Button button = new Button(buttonText);
        menu.getChildren().add(button);
        button.setOnAction(e->{
            this.screenController.activate(buttonText);
        });
    }
   
}
