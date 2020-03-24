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
    
    private Scene dashboardScene;
    
    private Scene newManagedCompanyScene;
    private Scene editManagedCompanyScene;
    private Scene manageCompanyScene;
    
    private Scene newCustomerCompanyScene;
    private Scene edtCustomerCompanyScene;
    
    private VBox managedCompanyNodes;
    private VBox customerCompanyNodes;
    
    private Label menuLabel = new Label();
    
    private ManagedCompany currentManagedCompany;
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

        this.screenController.addScreen("Dashboard", dashboard);
        this.screenController.addScreen("Add New Managed Company", newManagedCompany);
        this.screenController.addScreen("Add New Customer", newCustomer);
        
        dashboard = this.addMenuToScene(dashboard);
        newManagedCompany = this.addMenuToScene(newManagedCompany);
        newCustomer = this.addMenuToScene(newCustomer);        

        this.screenController.activate("Dashboard");
        primaryStage.setScene(this.mainScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("closing");
        });
        
        formActions = new FormActionFactory(appService);
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
    
    public BorderPane addMenuToScene(BorderPane scene) {
        
        BottomMenu bottomMenu = new BottomMenu(this.screenController);
        
        for ( String key : this.screenController.getScreenMap().keySet() ) {
            this.screenController.getScreenMap().get(key);
            bottomMenu.addMenuItem(key, this.screenController.getScreenMap().get(key));
        }
        
        scene.setBottom(bottomMenu.getMenu());
        
        return scene;
    }
    
    private void makeDashboardScene() {

        managedCompanyNodes = new VBox(10);
        managedCompanyNodes.setMaxWidth(400);
        managedCompanyNodes.setMinWidth(280); 
        
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

    private VBox makeEditManagedCompanyScene() {
        FormUi form = new FormUi();
        form.setSubmitButtonText("Save");
        form.addFormField("Name", this.currentManagedCompany.getName());
        form.addFormField("Register Id", this.currentManagedCompany.getRegId());
        form.addFormField("Phone", this.currentManagedCompany.getPhone());
        form.addFormField("Street address", this.currentManagedCompany.getStreet());
        form.addFormField("Postcode", this.currentManagedCompany.getPostcode());
        form.addFormField("Commune/City", this.currentManagedCompany.getCommune());
        form.addFormField("OVT", this.currentManagedCompany.getOvtId());
        form.addFormField("Provider", this.currentManagedCompany.getProvider());
        form.addFormField("IBAN", this.currentManagedCompany.getIban());
        form.addFormField("BIC", this.currentManagedCompany.getBic());
        VBox content = form.make();
        this.setFormAction(form, "updateManagedCompany");
       
        return content;
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
            managedCompanyNodes.getChildren().add(createManagedCompanyNode(company));
        });     
    }
   
    public Node createManagedCompanyNode(ManagedCompany company) {
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
            primaryStage.setTitle("Edit company: " + company.getName());
            primaryStage.setScene(this.editManagedCompanyScene);   
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
            formActions.getAction(action, formFields, this.currentManagedCompany);
            this.redrawManagedCompanies();
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
