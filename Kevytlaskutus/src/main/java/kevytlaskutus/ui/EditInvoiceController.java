package kevytlaskutus.ui;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.Product;

public class EditInvoiceController extends BaseController implements Initializable {

    @FXML
    private Pane editFormContainerPane;
   
    @FXML 
    private Button saveFormButton;

    @FXML 
    private Button addNewRowButton;
        
    private Form form;
    
    private String actionType;
   
    private Invoice currentInvoice;
    
    private List<Product> products;
   
    private FormActionFactory actionFactory;
    
    public EditInvoiceController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.currentInvoice = this.appService.getCurrentInvoice();
        this.products = this.appService.getProducts();
        
        this.setActionType();
        
        super.primaryNotice.setupNotice();
        super.noticePane.getChildren().add(super.primaryNotice.notice);
        
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setSaveButtonAction();
        this.setAddNewRowButtonAction();
    }
   
    public void setupForm() {
        
        this.form = new Form();
                
        this.form.addDatePicker("Date", currentInvoice.getCreatedDate());
        this.form.addTextField("Invoice Number", "" + "" + this.currentInvoice.getInvoiceNumber());
        this.form.addTextField("Reference Number", "" + currentInvoice.getReferenceNumber());
        this.form.addTextField("Payment due in number of days", "" + this.currentInvoice.getPaymentTerm());
        this.form.addDatePicker("Due Date", this.currentInvoice.getDueDate());
        this.form.addTextField("Overdue Penalty Interest rate", "10.0");
        this.form.addTextField("Discount", "" + this.currentInvoice.getDiscount());
        
        String customerName = "";
        if (currentInvoice.getCustomer() != null) {
            customerName = currentInvoice.getCustomer().getName();
        }      
        this.form.addDropDown("Customer", this.createCustomerNameList(), customerName);
        
        this.form.addTextField("Customer Contact Name", this.currentInvoice.getCustomerContactName());
        this.form.addTextField("Customer Reference", this.currentInvoice.getCustomerReference());
        this.form.addTextField("Our Reference", this.currentInvoice.getCompanyReference());
        this.form.addTextField("Delivery Terms", this.currentInvoice.getDeliveryTerms());
        this.form.addDatePicker("Delivery Date", this.currentInvoice.getDeliveryDate());
        this.form.addTextField("Delivery Information", this.currentInvoice.getDeliveryInfo());
        this.form.addTextField("Additional Information", this.currentInvoice.getAdditionalInfo());
        
        for (Product product : this.currentInvoice.getProducts()) {
            this.form.addProductItem(products, product.getName());
        }
        this.form.addProductItem(products, "");
       
        this.form.addTextField("Amount", "" + this.currentInvoice.getAmount());

        this.editFormContainerPane.getChildren().add(this.form.getForm());
       
    }
  
    private ObservableList createCustomerNameList() {
        List<CustomerCompany> customers = this.appService.getCustomerCompanies();
        ObservableList<String> customerNames = FXCollections.observableArrayList(); 
        for (CustomerCompany customer : customers) {
            customerNames.add(customer.getName());
        }
        return customerNames;
    }
   
    public void setActionType() {
        if (this.currentInvoice.getId() > 0) {
            this.actionType = "UpdateInvoice";
        } else {
            this.actionType = "NewInvoice";
        }
    }
    
    private void setAddNewRowButtonAction() {
        this.addNewRowButton.setOnAction(e-> {
            this.form.addProductItem(products, "");
        });
    }   
    
    private void setSaveButtonAction() {
        this.saveFormButton.setOnAction(e-> {
            this.actionFactory.execute(this.actionType, this.form.getFormFields(), this.currentInvoice.getId());
            this.viewFactory.showManageInvoicesView();
        });
    }

}
