/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.util.ArrayDeque;

/**
 * NoticeQueue object containing pending notices.
 */
public class NoticeQueue {
    
    protected ArrayDeque<Notice> notices;
    
    public NoticeQueue() {
        this.notices = new ArrayDeque();
    }
    
    /**
     * Add a Notice to the NoticeQueue
     *
     * @param notice the notice to be added
     * @see Notice
     */
    public void addNotice(Notice notice) {
        this.notices.addLast(notice);
    }
    
    /**
     * Checks if there is a Notice pending in the NoticeQueue
     *
     * @return boolean
     * @see Notice
     */
    public boolean hasPendingNotice() {
        return this.notices.size() > 0;
    }
    
    /**
     * Retrieves the first pending Notice from the NoticeQueue
     *
     * @return Notice
     * @see Notice
     */
    public Notice getPendingNotice() {
        return this.notices.poll();
    }
}
