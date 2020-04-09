/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.util.ArrayDeque;

/**
 *
 * @author ilkka
 */
public class NoticeQueue {
    
    protected ArrayDeque<Notice> notices;
    
    public NoticeQueue() {
        this.notices = new ArrayDeque();
    }
    
    public void addNotice(Notice notice) {
        this.notices.addLast(notice);
    }
    
    public boolean hasPendingNotice() {
        return this.notices.size() > 0;
    }
    
    public Notice getPendingNotice() {
        return this.notices.poll();
    }
}
