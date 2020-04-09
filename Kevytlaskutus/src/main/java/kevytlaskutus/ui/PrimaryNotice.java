/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kevytlaskutus.domain.AppService;

/**
 *
 * @author ilkka
 */
public class PrimaryNotice {
    
    AppService appService;
            
    protected HBox notice;
    protected Text noticeMessage;
    
    public PrimaryNotice(AppService appService) {
        this.appService = appService;
    }
    
    public void setupNotice() {
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
       
    public void showPendingNoticeMessage() {
        if (this.appService.hasNoticePending()) {
            this.noticeMessage.setVisible(this.appService.hasNoticePending());
            this.setNoticeMessage(this.appService.getPendingNotice());
        }
    }
   
    private void setNoticeMessage(kevytlaskutus.domain.Notice notice) {
        this.noticeMessage.setText(notice.getNoticeMessage());
        this.setNoticeType(notice);
    }
    
    private void setNoticeType(kevytlaskutus.domain.Notice notice) {
        if (notice.getNoticeType().equals("success")) {
            this.noticeMessage.setFill(Color.GREEN);
        } else {
            this.noticeMessage.setFill(Color.RED);
        }
    }
}
