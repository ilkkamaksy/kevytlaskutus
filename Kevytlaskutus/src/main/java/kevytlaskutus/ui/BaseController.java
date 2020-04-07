package kevytlaskutus.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kevytlaskutus.domain.AppService;

/**
 * Base controller class for views.
 */
public abstract class BaseController {

    @FXML
    protected Pane noticePane;
    
    protected HBox notice;
    protected Text noticeMessage;
    
    protected AppService appService;
    protected ViewFactory viewFactory;
    protected String fxmlName;

    public BaseController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        this.appService = appService;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
    
    public String getFxmlName() {
        return this.fxmlName;
    }
    
    @FXML
    void manageCompaniesAction() {
        this.viewFactory.showDashBoard();
    }

    @FXML
    void manageCustomersAction() {
        this.viewFactory.showManageCustomerView();
    }

    @FXML
    void manageProductsAction() {
        this.viewFactory.showManageProductsView();
    }    
    
    protected void setupNotice() {
        this.notice = new HBox(20);
        this.notice.setPadding(new Insets(8));
        this.notice.setMinHeight(30);
        this.notice.setPrefWidth(500);
        this.noticeMessage = new Text();
        this.noticeMessage.setText("asfsasfsaf");
        this.notice.getChildren().add(noticeMessage);
        this.notice.setVisible(true);
        this.notice.managedProperty().bind(this.notice.visibleProperty());
    }
    
    protected void toggleNoticeVisibility(boolean visible) {
        this.notice.setVisible(visible);
    }
    
    protected void setNoticeMessageText(boolean success) {
        if (success) {
            this.noticeMessage.setText("A new item has been added");
            this.noticeMessage.setFill(Color.GREEN);
        } else {
            this.noticeMessage.setText("Could not add a new item, check form and try again.");
            this.noticeMessage.setFill(Color.RED);
        }
    }
}
