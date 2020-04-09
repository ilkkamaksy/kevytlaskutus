/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

/**
 *
 * @author ilkka
 */
public class NoticeError implements Notice {
    protected String noticeType = "error";
    protected String message;
    
    public NoticeError(String message) {
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
