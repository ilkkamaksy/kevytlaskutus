package kevytlaskutus.ui;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.ManagedCompany;

public class EditInvoiceController extends BaseController implements Initializable {

    @FXML
    private Pane editFormContainerPane;
   
    @FXML 
    private Button saveFormButton;
    
    private Form form;
    
    private String actionType;
    
    private Invoice currentInvoice;
    
    private FormActionFactory actionFactory;
    
    public EditInvoiceController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.currentInvoice = this.appService.getCurrentInvoice();
        this.setActionType();
        this.form = new Form();
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setButtonAction();
    }
   
    public void setupForm() {
      
        if (currentInvoice.getId() == 0) {
            this.form.addFormField("Date", "");
            this.form.addFormField("Invoice Number", "");
            this.form.addFormField("Reference Number", "");
            this.form.addFormField("Payment due in number of days", "14");
            this.form.addFormField("Due date", "");
            this.form.addFormField("Overdue Penalty Interest rate", "");
            this.form.addFormField("Discount", "");
            this.form.addFormField("Discount Date", "");
            this.form.addFormField("Customer Id", "");
            this.form.addFormField("Customer Contact Name", "");
            this.form.addFormField("Customer Reference", "");
            this.form.addFormField("Our Reference", "");
            this.form.addFormField("Delivery Terms", "");
            this.form.addFormField("Delivery Date", "");
            this.form.addFormField("Delivery Information", "");
            this.form.addFormField("Additional Information", "");
            this.form.addFormField("Amount", "");
        } else {
            this.form.getForm().getChildren().clear();
        
            this.form.addFormField("Date", "" + currentInvoice.getCreatedDate().toString());
            this.form.addFormField("Invoice Number", "" + currentInvoice.getInvoiceNumber());
            this.form.addFormField("Reference Number", "" + currentInvoice.getReferenceNumber());
            this.form.addFormField("Payment due in number of days", "" + currentInvoice.getPaymentTerm());
            this.form.addFormField("Due date", "" + currentInvoice.getDueDate().toString());
            this.form.addFormField("Overdue Penalty Interest rate", "" + currentInvoice.getPenaltyInterest());
            this.form.addFormField("Discount", "" + currentInvoice.getDiscount());
            this.form.addFormField("Discount Date", "" + currentInvoice.getDiscountDate());
            this.form.addFormField("Customer Id", "" + currentInvoice.getCustomerId());
            this.form.addFormField("Customer Contact Name", "" + currentInvoice.getCustomerContactName());
            this.form.addFormField("Customer Reference", "" + currentInvoice.getCustomerReference());
            this.form.addFormField("Our Reference", "" + currentInvoice.getCompanyReference());
            this.form.addFormField("Delivery Terms", "" + currentInvoice.getDeliveryTerms());
            this.form.addFormField("Delivery Date", "" + currentInvoice.getDeliveryDate());
            this.form.addFormField("Delivery Information", "" + currentInvoice.getDeliveryInfo());
            this.form.addFormField("Additional Information", "" + currentInvoice.getAdditionalInfo());
            this.form.addFormField("Amount", "" + currentInvoice.getAmount());
        }
   
        this.editFormContainerPane.getChildren().add(this.form.getForm());
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
