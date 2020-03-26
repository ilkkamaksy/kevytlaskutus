/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Node;
/**
 *
 * @author ilkka
 */
public class ScreenContent {
    private VBox container;
    
    public ScreenContent() {
        this.container = new VBox(10);
    }
    
    public void addTitle(String text) {
        Text title = new Text(text);
        this.addToContainer(title);
    }
    
    public void addContent(Node content) {
        VBox contentWrapper = new VBox(10);
        contentWrapper.getChildren().add(content);
        this.addToContainer(contentWrapper);
    }
    
    private void addToContainer(Node element) {
        this.container.getChildren().add(element);
    }
      
       
}
