/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;


import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import kevytlaskutus.dao.*;
import kevytlaskutus.domain.*;
import kevytlaskutus.ui.*;
import kevytlaskutus.Main;


/**
 *
 * @author ilkka
 */
public class AppUi extends Application {
    
    private AppService appService;
     
    private Stage primaryStage;
    
    private Scene mainScene;
   
    private VBox managedCompanyNodes;
    private VBox customerCompanyNodes;
   
    private ManagedCompany currentManagedCompany;
    
    private FormUi editManagedCompanyForm;
    
    private FormActionFactory formActions;
    
    private ScreenController screenController;
    
    private BottomMenu bottomMenu; 
        
    @Override
    public void init() throws Exception {
        ManagedCompanyDao managedCompanyDao = new ManagedCompanyDao();
        CustomerCompanyDao customerCompanyDao = new CustomerCompanyDao();
        appService = new AppService(managedCompanyDao, customerCompanyDao);
    }
   
    @Override
    public void start(Stage stage) {

        this.primaryStage = stage;

        this.mainScene = new Scene(new BorderPane(), 600, 500);
        this.screenController = new ScreenController(this.mainScene);
        
        primaryStage.setTitle("Kevytlaskutus");

        this.makeDashboardScene();
        BorderPane dashboard = this.makeScene(managedCompanyNodes);
        
        VBox newManagedCompanyForm = this.makeNewManagedCompanyScene();
        BorderPane newManagedCompany = this.makeScene(newManagedCompanyForm);
        
        VBox newCustomerForm = this.makeNewCustomerCompanyScene();
        BorderPane newCustomer = this.makeScene(newCustomerForm);

        // this.editManagedCompanyForm = new FormUi();
        // this.makeEditManagedCompanyScene();
        // BorderPane editManagedCompany = this.makeScene(this.editManagedCompanyForm.getForm());
        
//        Form form = new Form(this.screenController.getCurrentCompany());
//        form.make();
//        BorderPane editManagedCompany = this.makeScene(form.getForm());
        
        this.screenController.addScreen("Dashboard", dashboard);
        this.screenController.addScreen("Add New Managed Company", newManagedCompany);
//        this.screenController.addScreen("Edit Managed Company", editManagedCompany);
        this.screenController.addScreen("Add New Customer", newCustomer);
        
        this.createBottomMenu();
        this.screenController.addGlobalBottomMenu(this.bottomMenu.getMenu());

        this.screenController.activate("Dashboard");
        primaryStage.setScene(this.mainScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("closing");
        });
        
        formActions = new FormActionFactory(appService);
    }
    
    private void createBottomMenu() {
        this.bottomMenu = new BottomMenu(this.screenController);
        this.bottomMenu.addMenuItem("Dashboard");
        this.bottomMenu.addMenuItem("Add New Managed Company");
        this.bottomMenu.addMenuItem("Add New Customer");
    }
  
    private BorderPane makeScene(VBox content) {
        VBox container = new VBox(10);
        container.setMaxWidth(400);
        container.setMinWidth(280); 
        
        ScrollPane sceneScrollbar = new ScrollPane();       
        BorderPane pane = new BorderPane(sceneScrollbar);
        
        sceneScrollbar.setContent(content);
       
        return pane;
    }
   
    private void makeDashboardScene() {
        managedCompanyNodes = new VBox(10);
        redrawManagedCompanies();
    }
   
    private VBox makeNewManagedCompanyScene() {
        
        FormUi form = new FormUi();
        form.addFormField("Name", "");
        form.addFormField("Register Id", "");
        form.addFormField("Phone", "");
        form.addFormField("Street address", "");
        form.addFormField("Postcode", "");
        form.addFormField("Commune/City", "");
        form.addFormField("OVT", "");
        form.addFormField("Provider", "");
        form.addFormField("IBAN", "");
        form.addFormField("BIC", "");
        form.setSubmitButtonText("Save");
        VBox content = form.make();
        this.setFormAction(form, "newManagedCompany");
       
        return content;
    }

    public void makeEditManagedCompanyScene() {
       
        if ( this.screenController.getCurrentCompany() == null ) {
            editManagedCompanyForm.addFormField("Name", "");
            editManagedCompanyForm.addFormField("Register Id", "");
            editManagedCompanyForm.addFormField("Phone", "");
            editManagedCompanyForm.addFormField("Street address", "");
            editManagedCompanyForm.addFormField("Postcode", "");
            editManagedCompanyForm.addFormField("Commune/City", "");
            editManagedCompanyForm.addFormField("OVT", "");
            editManagedCompanyForm.addFormField("Provider", "");
            return;
        }
       
        this.editManagedCompanyForm.getForm().getChildren().clear();
        
        editManagedCompanyForm.setSubmitButtonText("Save");
        editManagedCompanyForm.addFormField("Name", screenController.getCurrentCompany().getName());
        editManagedCompanyForm.addFormField("Register Id", screenController.getCurrentCompany().getRegId());
        editManagedCompanyForm.addFormField("Phone", screenController.getCurrentCompany().getPhone());
        editManagedCompanyForm.addFormField("Street address", screenController.getCurrentCompany().getStreet());
        editManagedCompanyForm.addFormField("Postcode", screenController.getCurrentCompany().getPostcode());
        editManagedCompanyForm.addFormField("Commune/City", screenController.getCurrentCompany().getCommune());
        editManagedCompanyForm.addFormField("OVT", screenController.getCurrentCompany().getOvtId());
        editManagedCompanyForm.addFormField("Provider", screenController.getCurrentCompany().getProvider());
        // editManagedCompanyForm.addFormField("IBAN", this.currentManagedCompany.getIban());
        // editManagedCompanyForm.addFormField("BIC", this.currentManagedCompany.getBic());
        editManagedCompanyForm.make();
        setFormAction(editManagedCompanyForm, "updateManagedCompany");

    }

    private VBox makeNewCustomerCompanyScene() {
        
        FormUi form = new FormUi();
        form.addFormField("Name", "");
        form.addFormField("Register Id", "");
        form.addFormField("Phone", "");
        form.addFormField("Street address", "");
        form.addFormField("Postcode", "");
        form.addFormField("Commune/City", "");
        form.addFormField("OVT", "");
        form.addFormField("Provider", "");
        form.setSubmitButtonText("Save");
        VBox content = form.make();
        this.setFormAction(form, "newCustomerCompany");
        
        return content;
        
    }
    
    private void makeManageCompanyScene() {

//        primaryStage.setTitle("Manage Company: " + this.currentUserCompany.getName());
//
//        userCompanyNodes = new VBox(10);
//        userCompanyNodes.setMaxWidth(400);
//        userCompanyNodes.setMinWidth(280);
//        redrawUserCompanies();
//        
//        manageCompanyScene = this.prepareScene(userCompanyNodes);    
    }
   
    public Button makeMenuButton(String buttonText, Scene scene) {
        Button button = new Button(buttonText);
        button.setOnAction(e-> {
            this.screenController.activate(buttonText);
        });
        return button;
    }
   
    public void redrawManagedCompanies() {
        managedCompanyNodes.getChildren().clear();     
        
        List<ManagedCompany> managedCompanies = appService.getManagedCompanies();
        managedCompanies.forEach(company->{
            managedCompanyNodes.getChildren().add(createCompanyNode(company));
        });     
    }
   
    public void redrawCustomerCompanies() {
        customerCompanyNodes.getChildren().clear();     
        
        List<CustomerCompany> customerCompanies = appService.getCustomerCompanies();
        customerCompanies.forEach(company->{
            customerCompanyNodes.getChildren().add(createCompanyNode(company));
        });     
    }
    
    public Node createCompanyNode(Company company) {
//        CompanyNode node = new CompanyNode(this.screenController, company, "Edit Managed Company", this); 
//        return node.getCompanyNode();
            return null;
    }
    
    public Node xxxcreateCompanyNode(ManagedCompany company) {
        
        HBox box = new HBox(10);
        Label label  = new Label(company.getName());
        label.setMinHeight(28);
        Button selectButton = new Button("Manage");
//        button.setOnAction(e->{
//            todoService.markDone(todo.getId());
//            redrawTodolist();
//        });
        
        Button editButton = new Button("Edit");
        editButton.setOnAction(e->{
            this.currentManagedCompany = company;
            this.makeEditManagedCompanyScene();
            this.screenController.activate("Edit Managed Company");
        });
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e->{
            this.appService.deleteManagedCompany(company.getId());
            this.redrawManagedCompanies();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().addAll(
                label, 
                spacer, 
                selectButton,
                editButton,
                deleteButton
        );
        return box;
    }
 
    private void setFormAction(FormUi form, String action) {
        HashMap<String, TextField> formFields = form.getFormFields();
        form.getSubmitButton().setOnAction(e->{
//            formActions.getAction(action, formFields, this.currentManagedCompany);
//            this.redrawManagedCompanies();
//            this.makeEditManagedCompanyScene();
        });
    }
   
    @Override
    public void stop() {
        System.out.println("Closing program");
    }    
   
    public static void main(String[] args)  {
        AppUi.launch(args);
    } 
}
