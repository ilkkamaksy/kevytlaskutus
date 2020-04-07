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
        this.setupInitialNoticeMessage();
        this.notice.getChildren().add(noticeMessage);
        this.notice.managedProperty().bind(this.notice.visibleProperty());
    }
    
    private void setupInitialNoticeMessage() {
        this.noticeMessage = new Text();
        this.noticeMessage.setText("");
        this.noticeMessage.setVisible(true);
    }
       
    public void addNoticeMessage() {
        this.toggleNoticeVisibility(this.appService.isNoticePending());
        this.setNoticeMessageText(this.appService.getPendingNotice());
    }
    
    protected void toggleNoticeVisibility(boolean visible) {
        this.noticeMessage.setVisible(visible);
    }
    
    protected void setNoticeMessageText(String message) {
        this.noticeMessage.setText(message);
        this.noticeMessage.setFill(Color.GREEN);
    }
}
