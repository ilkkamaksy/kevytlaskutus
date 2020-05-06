package kevytlaskutus.ui;

import kevytlaskutus.domain.AppService;

/**
 * Abstract class for form actions.
 */
public abstract class FormAction {
    
    AppService appService;
   
    public FormAction(AppService appService) {
        this.appService = appService;
    }
  
    public abstract boolean execute();
}
