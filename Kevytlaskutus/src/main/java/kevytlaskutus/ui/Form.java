package kevytlaskutus.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Product;


/**
 * Class for creating Forms. 
 */
public class Form {
   
    private AppService appService;
    
    private EditInvoiceController editInvoiceController;
    
    private VBox form;
 
    public Form(AppService appService) {
        this.appService = appService;
        form = new VBox(10);
    }
    
    public void setEditInvoiceController(EditInvoiceController editInvoiceController) {
        this.editInvoiceController = editInvoiceController;
    }
   
    public void addLineItem() {
        Product product = new Product();
        product.setName("");
        product.setPriceUnit("");
        product.setDescription("");
        product.setInvoiceId(this.appService.getCurrentInvoice().getId());
        this.appService.getCurrentInvoice().getProducts().add(product);
        this.setLineItem(product);
    }
    
    public void setLineItem(Product selectedProduct) {
        
        FormFieldText nameField = new FormFieldText("Product Name", selectedProduct.getName());
        nameField.setOnChangeHandler(selectedProduct, "Name");
       
        String price = "";
        if (selectedProduct.getPrice() != null) {
            price = "" + selectedProduct.getPrice();
        }
        FormFieldDecimal priceField = new FormFieldDecimal("Product Price", price);
        priceField.setOnChangeHandler(selectedProduct, "Price");
        priceField.setCallback(this.editInvoiceController, "updateTotals");
        
        FormFieldText priceUnitField = new FormFieldText("Product Price Unit", selectedProduct.getPriceUnit());
        priceUnitField.setOnChangeHandler(selectedProduct, "PriceUnit");
        
        FormFieldText descriptionField = new FormFieldText("Product Description", selectedProduct.getDescription());
        descriptionField.setOnChangeHandler(selectedProduct, "Description");
        

        VBox container = new VBox(10);
        Separator separator = new Separator();
        container.getChildren().add(separator);
        
        HBox topRow = new HBox(10);
        topRow.getChildren().addAll(nameField.getField(), priceField.getField(), priceUnitField.getField());
        HBox bottomRow = new HBox(10);
        bottomRow.getChildren().addAll(descriptionField.getField());
        container.getChildren().addAll(topRow, bottomRow);
        this.addRemoveProductItemButton(selectedProduct, container);
        
        this.addNodesToForm(container);
    }
    
    public void addRemoveProductItemButton(Product product, VBox node) {
        Button button = new Button("Remove");
        button.setOnAction(e-> {
            this.form.getChildren().remove(node);
            product.setInvoiceId(0);
            this.editInvoiceController.updateTotals();
        });
        
        node.getChildren().add(button);
    }
   
    public void addTextField(String label, String field, Object onChangeObject, String onChangeObjectProperty) {
        FormFieldText formField = new FormFieldText(label, field);
        formField.setOnChangeHandler(onChangeObject, onChangeObjectProperty);
        this.addNodesToForm(formField.getField());
    }
    
    public void addHiddenField(String label, String field) {
        TextField inputField = new TextField(field);
        inputField.setVisible(false);
        this.addNodesToForm(inputField);
    }
   
    public void addDecimalField(String label, String field, Object onChangeObject, String onChangeObjectProperty, Object callBackObject, String callBackMethodName) { 
        FormFieldDecimal formField = new FormFieldDecimal(label, field);
        formField.setOnChangeHandler(onChangeObject, onChangeObjectProperty);
        formField.setCallback(callBackObject, callBackMethodName);
        this.addNodesToForm(formField.getField());
    }
    
    public void addIntegerField(String label, String field, Object onChangeObject, String onChangeObjectProperty) {     
        FormFieldInteger formField = new FormFieldInteger(label, field);
        formField.setOnChangeHandler(onChangeObject, onChangeObjectProperty);
        this.addNodesToForm(formField.getField()); 
    }
    
    public void addIntegerField(String label, String field, Object onChangeObject, String onChangeObjectProperty, Object callBackObject, String callBackMethodName) {     
        FormFieldInteger formField = new FormFieldInteger(label, field);
        formField.setOnChangeHandler(onChangeObject, onChangeObjectProperty);
        formField.setCallback(callBackObject, callBackMethodName);
        this.addNodesToForm(formField.getField()); 
    }
    
    public void addDatePicker(String label, String dateString, Object onChangeObject, String onChangeObjectProperty) {
        FormFieldDatePicker formField = new FormFieldDatePicker(label, dateString);
        formField.setOnChangeHandler(onChangeObject, onChangeObjectProperty);
        this.addNodesToForm(formField.getField());
    }
    
    public void addDatePicker(String label, String dateString, Object onChangeObject, String onChangeProperty, Object callBackObject, String callBackMethodName) {
        FormFieldDatePicker formField = new FormFieldDatePicker(label, dateString);
        formField.setOnChangeHandler(onChangeObject, onChangeProperty);
        formField.setCallback(callBackObject, callBackMethodName);
        this.addNodesToForm(formField.getField());
    }
  
    public void addDropDown(String label, ObservableList<String> options, String selectedItem, Object onChangeObject, String onChangeProperty) {
        FormFieldDropdown formField = new FormFieldDropdown(label, options, selectedItem, this.appService);
        formField.setOnChangeHandler(onChangeObject, onChangeProperty);
        this.addNodesToForm(formField.getField());
    }
   
    private void addNodesToForm(Node... node) {
        form.getChildren().addAll(node);
    }
  
    public VBox getForm() {
        return form;
    }
}
