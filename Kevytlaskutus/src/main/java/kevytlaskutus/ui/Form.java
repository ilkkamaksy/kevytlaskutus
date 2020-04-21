package kevytlaskutus.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import kevytlaskutus.domain.Product;


/**
 * Class for creating Forms. 
 */
public class Form {
   
    private VBox form;
    
    private HashMap<String, Node> formFields;
    
    private int productRowCount = 1;
  
    public Form() {
        this.formFields = new HashMap<>();
        form = new VBox(10);
    }
   
    public void addProductItem() {
        Product product = new Product();
        product.setName("");
        product.setPrice("");
        product.setPriceUnit("");
        product.setDescription("");
        this.addProductItem(product);
    }
    
    public void addProductItem(Product selectedProduct) {
        this.addTextField("Product Name #" + this.productRowCount, selectedProduct.getName());
        this.addTextField("Product Price #" + this.productRowCount, selectedProduct.getPrice());
        this.addTextField("Product Price Unit #" + this.productRowCount, selectedProduct.getPriceUnit());
        this.addTextField("Product Description #" + this.productRowCount, selectedProduct.getDescription());
        this.productRowCount++;
    }
    
    public void addTextField(String label, String field) {
        TextField inputField = new TextField(field);
        this.addNodesToForm(new Label(label), inputField);
        this.formFields.put(label, inputField);
    }
    
    public void addDatePicker(String label, Date presetDate) {
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(getLocalDateForString(presetDate));
        this.addNodesToForm(new Label(label), datePicker);
        this.formFields.put(label, datePicker);
    }
    
    private LocalDate getLocalDateForString(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date.toString());
    }
    
    public void addDropDown(String label, ObservableList<String> options, String selectedItem) {
        form.getChildren().add(new Label(label));
        ComboBox dropdown = new ComboBox(options);
        
        this.setDropDownPresetOption(dropdown, options, selectedItem);
        
        this.addNodesToForm(dropdown);
        this.formFields.put(label, dropdown);
    }
    
    private void setDropDownPresetOption(ComboBox dropdown, ObservableList<String> options, String selectedItem) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals(selectedItem)) {
                dropdown.getSelectionModel().select(selectedItem);
            }
        }
    }
   
    private ObservableList<String> createNameList(List<?> items) {
        ObservableList<String> names = FXCollections.observableArrayList(); 
        for (Object item : items) {
            Method method;
            try {
                method = item.getClass().getDeclaredMethod("getName");
                String name = (String) method.invoke(item);
                names.add(name);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return names;
    }
    
    private void addNodesToForm(Node... node) {
        form.getChildren().addAll(node);
    }
   
    public VBox getForm() {
        return form;
    }

    public HashMap<String, Node> getFormFields() {
        return this.formFields;
    }
}
