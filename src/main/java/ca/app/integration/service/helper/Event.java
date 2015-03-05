package ca.app.integration.service.helper;

import ca.app.integration.vo.APIResult;
import ca.app.integration.vo.EventInfo;
import ca.app.user.service.ISVService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Event {


    protected EventInfo eventInfo;

    public void setEventInfo(EventInfo eventInfo){
        this.eventInfo = eventInfo;
    }

    abstract public APIResult process();
}
