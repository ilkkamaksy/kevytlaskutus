/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author ilkka
 */
public class Dashboard {
    
    private Stage stage;
    private VBox content;
    
    public Dashboard(Stage stage) {
        
        this.stage = stage;
        stage.setTitle("Dashboard");
        
        this.content = new VBox(10);
        this.content.setMaxWidth(400);
        this.content.setMinWidth(280);
    }
   
    public VBox getContent() {
        return content;
    }
    
    public Stage getStage() {
        return stage;
    }
    
}
