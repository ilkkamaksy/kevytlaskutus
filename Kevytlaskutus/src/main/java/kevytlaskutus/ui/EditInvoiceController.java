package kevytlaskutus.ui;

import java.net.URL;
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

public class EditInvoiceController extends BaseController implements Initializable {

    @FXML
    private Pane editFormContainerPane;
   
    @FXML 
    private Button saveFormButton;
    
    private Form form;
    
    private String actionType;
    
    private int defaultInvoiceNumber; 
    
    private Invoice currentInvoice;
    
    private FormActionFactory actionFactory;
    
    public EditInvoiceController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.currentInvoice = this.appService.getCurrentInvoice();
        this.defaultInvoiceNumber = this.appService.getDefaultInvoiceNumber();
        this.setActionType();
        this.form = new Form();
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setButtonAction();
    }
   
    public void setupForm() {
      
        
        
        if (currentInvoice.getId() == 0) {
            this.form.addDatePicker("Date");
            this.form.addTextField("Invoice Number", "" + defaultInvoiceNumber);
            this.form.addTextField("Reference Number", "");
            this.form.addTextField("Payment due in number of days", "14");
            this.form.addDatePicker("Due Date");
            this.form.addTextField("Overdue Penalty Interest rate", "");
            this.form.addTextField("Discount", "");
            this.form.addTextField("Discount Date", "");
            this.form.addDropDown("Customer", this.createCustomerNameList());
            this.form.addTextField("Customer Contact Name", "");
            this.form.addTextField("Customer Reference", "");
            this.form.addTextField("Our Reference", "");
            this.form.addTextField("Delivery Terms", "");
            this.form.addDatePicker("Delivery Date");
            this.form.addTextField("Delivery Information", "");
            this.form.addTextField("Additional Information", "");
            this.form.addTextField("Amount", "");
        } else {
            this.form.getForm().getChildren().clear();
        
            this.form.addTextField("Date", "" + currentInvoice.getCreatedDate().toString());
            this.form.addTextField("Invoice Number", "" + currentInvoice.getInvoiceNumber());
            this.form.addTextField("Reference Number", "" + currentInvoice.getReferenceNumber());
            this.form.addTextField("Payment due in number of days", "" + currentInvoice.getPaymentTerm());
            this.form.addTextField("Due date", "" + currentInvoice.getDueDate().toString());
            this.form.addTextField("Overdue Penalty Interest rate", "" + currentInvoice.getPenaltyInterest());
            this.form.addTextField("Discount", "" + currentInvoice.getDiscount());
            this.form.addTextField("Discount Date", "" + currentInvoice.getDiscountDate());
            this.form.addTextField("Customer Id", "" + currentInvoice.getCustomerId());
            this.form.addTextField("Customer Contact Name", "" + currentInvoice.getCustomerContactName());
            this.form.addTextField("Customer Reference", "" + currentInvoice.getCustomerReference());
            this.form.addTextField("Our Reference", "" + currentInvoice.getCompanyReference());
            this.form.addTextField("Delivery Terms", "" + currentInvoice.getDeliveryTerms());
            this.form.addTextField("Delivery Date", "" + currentInvoice.getDeliveryDate());
            this.form.addTextField("Delivery Information", "" + currentInvoice.getDeliveryInfo());
            this.form.addTextField("Additional Information", "" + currentInvoice.getAdditionalInfo());
            this.form.addTextField("Amount", "" + currentInvoice.getAmount());
        }
   
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
    
    private void setButtonAction() {
        this.saveFormButton.setOnAction(e-> {
            this.actionFactory.execute(this.actionType, this.form.getFormFields(), this.currentInvoice.getId());
        });
    }

}
