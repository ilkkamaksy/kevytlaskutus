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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Product;


/**
 * Class for creating Forms. 
 */
public class Form {
   
    AppService appService;
    
    private VBox form;
    
    private HashMap<String, Node> formFields;
    
    private int productRowCount = 1;
  
    public Form(AppService appService) {
        this.appService = appService;
        this.formFields = new HashMap<>();
        form = new VBox(10);
    }
   
    public void addLineItem() {
        Product product = new Product();
        product.setName("");
        product.setPrice(new BigDecimal(0));
        product.setPriceUnit("");
        product.setDescription("");
        product.setInvoiceId(this.appService.getCurrentInvoice().getId());
        this.appService.getCurrentInvoice().getProducts().add(product);
        this.setLineItem(product);
    }
    
    public void setLineItem(Product selectedProduct) {
        this.addTextField("Product Name #" + this.productRowCount, selectedProduct.getName(), selectedProduct, "Name");
        this.addDecimalField("Product Price #" + this.productRowCount, "" + selectedProduct.getPrice(), selectedProduct, "Price");
        this.addTextField("Product Price Unit #" + this.productRowCount, selectedProduct.getPriceUnit(), selectedProduct, "PriceUnit");
        this.addTextField("Product Description #" + this.productRowCount, selectedProduct.getDescription(), selectedProduct, "Description");
        this.addHiddenField("Product ID #" + this.productRowCount, "" + selectedProduct.getId());
        
        this.addRemoveProductItemButton(productRowCount, selectedProduct);
        
        this.productRowCount++;
    }
    
    public void addRemoveProductItemButton(int index, Product product) {
        Button button = new Button("Remove");
        button.setOnAction(e-> {
            for (int i = 0; i < this.form.getChildren().size(); i++) {
                if(this.form.getChildren().get(i).toString().contains("Product Name #" + index)) {
                    for (int j = i+9; j >= i; j--) {
                        this.form.getChildren().remove(this.form.getChildren().get(j));
                    }
                    break;
                }
                i++;
            }
            product.setInvoiceId(0);
            this.productRowCount--;
        });
        
        this.addNodesToForm(button);
    }
   
    public void addTextField(String label, String field, Object object, String property) {
        TextField inputField = new TextField(field);
        this.addNodesToForm(new Label(label), inputField);
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, String.class);
                method.invoke(object, newValue);
                
                method = object.getClass().getDeclaredMethod("get" + property);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void addHiddenField(String label, String field) {
        TextField inputField = new TextField(field);
        inputField.setVisible(false);
        this.addNodesToForm(inputField);
        this.formFields.put(label, inputField);
    }
    
    public void addDecimalField(String label, String field) { 
        Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");
        UnaryOperator<Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };
        TextFormatter<Double> formatter = new TextFormatter<>(filter);

        TextField inputField = new TextField(field);
        inputField.setTextFormatter(formatter);
        
        this.addNodesToForm(new Label(label), inputField);
        this.formFields.put(label, inputField);
    }
    
    public void addDecimalField(String label, String field, Object object, String property) { 
        Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");
        UnaryOperator<Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };
        TextFormatter<Double> formatter = new TextFormatter<>(filter);

        TextField inputField = new TextField(field);
        inputField.setTextFormatter(formatter);
        
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, BigDecimal.class);
                
                if (isNotEmpty(newValue) && isNumeric(newValue)) {
                    method.invoke(object, new BigDecimal(newValue));
                }
                
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.addNodesToForm(new Label(label), inputField);
    }
    
    public void addIntegerField(String label, String field, Object object, String property) {     
        UnaryOperator<Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("[0-9]*")) { 
                return change;
            }
            return null;
        };
        TextField inputField = new TextField(field);
        inputField.setTextFormatter(new TextFormatter<String>(integerFilter));  
        
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, Integer.class);
                
                if (isNumeric(newValue)) {
                    Integer val = Integer.valueOf(newValue);
                    method.invoke(object, val);
                }
                
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.addNodesToForm(new Label(label), inputField); 
    }
    
    public void addDatePicker(String label, Date presetDate, Object object, String property) {
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(getLocalDateForString(presetDate));
        this.addNodesToForm(new Label(label), datePicker);
        
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, java.sql.Date.class);
                
                if (newValue != null) {
                    Date date = Date.valueOf(newValue);
                    method.invoke(object, date);
                }
                
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private LocalDate getLocalDateForString(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date.toString());
    }
    
    public void addDropDown(String label, ObservableList<String> options, String selectedItem, Object object, String property) {
        form.getChildren().add(new Label(label));
        ComboBox dropdown = new ComboBox(options);
        
        this.setDropDownPresetOption(dropdown, options, selectedItem);
        
        dropdown.valueProperty().addListener((observable, oldValue, newValue) -> {
            Method method;
            try {
                method = object.getClass().getDeclaredMethod("set" + property, CustomerCompany.class);
               
                if (newValue != null) {
                    CustomerCompany customer = this.appService.getCustomerCompanyByName((String) newValue);
                    method.invoke(object, customer);
                }
                
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.addNodesToForm(dropdown);
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
   
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static boolean isNotEmpty(String str) {
        if (str == null || str.isBlank()) {
            return false;
        }
        return true;
    }
}
