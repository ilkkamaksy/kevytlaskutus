package kevytlaskutus.ui;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    
    @FXML 
    private Button cancelButton;

    @FXML 
    protected Text totalAmountExcludingTaxes;
    
    @FXML 
    private Text totalTaxesAmount;
    
    @FXML 
    private Text totalAmountIncludingTaxes;
   
    @FXML
    private Text invoiceDueDate;
    
    @FXML
    private Text invoiceReferenceNumber;
    
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
       
        super.primaryNotice.setupNotice();
        super.noticePane.getChildren().add(super.primaryNotice.notice);
        super.primaryNotice.showPendingNoticeMessage();
        
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setSaveButtonAction();
        this.setAddNewRowButtonAction();
        this.setCancelButtonAction();
       
        this.updateTotals();
        this.updateInvoiceDueDate();
        this.updateInvoiceReferenceNumber();
    }
   
    public void setupForm() {
        
        this.form = new Form(this.appService);
        this.form.setEditInvoiceController(this);
       
        String customerName = "";
        if (currentInvoice.getCustomer() != null) {
            customerName = currentInvoice.getCustomer().getName();
        }      
        this.form.addDropDown("Customer", this.createCustomerNameList(), customerName, currentInvoice, "Customer");
        
        this.form.addDatePicker("Date", currentInvoice.getCreatedDate().toString(), currentInvoice, "CreatedDate", this, "updateInvoiceDueDate");
        this.form.addIntegerField("Invoice Number", "" + "" + this.currentInvoice.getInvoiceNumber(), this.currentInvoice, "InvoiceNumber", this, "updateInvoiceReferenceNumber");
        this.form.addIntegerField("Payment due in number of days", "" + this.currentInvoice.getPaymentTerm(), this.currentInvoice, "PaymentTerm", this, "updateInvoiceDueDate");
        this.form.addDecimalField("Overdue Penalty Interest %", "" + this.currentInvoice.getPenaltyInterest(), this.currentInvoice, "PenaltyInterest", this, "updateTotals");
        
        this.form.addDecimalField("VAT %", "" + this.currentInvoice.getVatPercentage(), this.currentInvoice, "VatPercentage", this, "updateTotals");
        this.form.addDecimalField("Discount %", "" + this.currentInvoice.getDiscount(), this.currentInvoice, "Discount", this, "updateTotals");
      
        this.form.addTextField("Customer Contact Name", this.currentInvoice.getCustomerContactName(), this.currentInvoice, "CustomerContactName");
        this.form.addTextField("Customer Reference", this.currentInvoice.getCustomerReference(), this.currentInvoice, "CustomerReference");
        this.form.addTextField("Our Reference", this.currentInvoice.getCompanyReference(), this.currentInvoice, "CompanyReference");
        this.form.addTextField("Delivery Terms", this.currentInvoice.getDeliveryTerms(), this.currentInvoice, "DeliveryTerms");
        this.form.addDatePicker("Delivery Date", this.currentInvoice.getDeliveryDate().toString(), currentInvoice, "DeliveryDate");
        this.form.addTextField("Delivery Information", this.currentInvoice.getDeliveryInfo(), this.currentInvoice, "DeliveryInfo");
        this.form.addTextField("Additional Information", this.currentInvoice.getAdditionalInfo(), this.currentInvoice, "AdditionalInfo");
  
        List<Product> products = this.currentInvoice.getProducts();
        for (Product product : products) {
            this.form.setLineItem(product);
        }
        if (products.size() == 0) {
            this.form.addLineItem();
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
   
    private void setAddNewRowButtonAction() {
        this.addNewRowButton.setOnAction(e-> {
            this.form.addLineItem();
        });
    }   
    
    private void setSaveButtonAction() {
        this.saveFormButton.setOnAction(e-> {
            boolean success = this.actionFactory.execute("SaveInvoice", this.currentInvoice.getId());
            if (success) {
                this.viewFactory.showManageInvoicesView();
            } else {
                this.viewFactory.showEditInvoiceView();
            }
        });
    }
    
    private void setCancelButtonAction() {
        this.cancelButton.setOnAction(e-> {
            this.viewFactory.showManageInvoicesView();
        });
    }

    public void updateTotals() {
        this.appService.updateCurrentInvoiceTotal();
        this.updateTotalField(this.totalAmountExcludingTaxes, currentInvoice.getAmount().toString());
        this.updateTotalField(this.totalTaxesAmount, this.appService.getCurrentInvoiceTotalTaxes().toString());
        this.updateTotalField(this.totalAmountIncludingTaxes, this.appService.getCurrentInvoiceTotalIncludingTaxes().toString());
    }
    
    private void updateTotalField(Text field, String textExpression) {
        field.textProperty().setValue(textExpression + " €"); 
    }
    
    public void updateInvoiceDueDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        String formattedDate = df.format(this.currentInvoice.getDueDate());
        this.invoiceDueDate.textProperty().setValue(formattedDate);
    }
    
    public void updateInvoiceReferenceNumber() {
        this.appService.updateInvoiceReferenceNumber();
        this.invoiceReferenceNumber.textProperty().setValue(this.currentInvoice.getReferenceNumber().toString());
    }
}
