/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import javafx.scene.layout.VBox;

/**
 *
 * @author ilkka
 */
public interface FormField {
    void setOnChangeHandler(Object object, String property);
    void setCallback(Object object, String property);
    VBox getField();
}
