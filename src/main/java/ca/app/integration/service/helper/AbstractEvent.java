package ca.app.integration.service.helper;

import ca.app.integration.vo.APIResult;
import ca.app.integration.vo.EventInfo;

public abstract class AbstractEvent implements Event{
    protected EventInfo eventInfo;

    public void setEventInfo(EventInfo eventInfo){
        this.eventInfo = eventInfo;
    }

    abstract public APIResult process();
}
