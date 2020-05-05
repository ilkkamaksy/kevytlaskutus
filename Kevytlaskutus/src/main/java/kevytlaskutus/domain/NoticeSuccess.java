/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

/**
 * The class for success notices.
 */
public class NoticeSuccess implements Notice {
    
    protected String noticeType = "success";
    protected String message;
    
    public NoticeSuccess(String message) {
        this.message = message;
    }
    
    @Override
    public String getNoticeMessage() {
        return this.message;
    }

    @Override
    public String getNoticeType() {
        return this.noticeType;
    }
    
}
