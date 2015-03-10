package ca.app.integration.service.impl;

import ca.app.integration.service.EventService;
import ca.app.integration.service.event.Event;
import ca.app.integration.service.event.EventFactory;
import ca.app.integration.type.ErrorCode;
import ca.app.integration.vo.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventServiceImpl implements EventService {


    @Autowired
    private EventFactory eventFactory;

    public APIResult processEvent(String eventUrl, String token){
        Event event = eventFactory.getSubscribeEvent(eventUrl, token);
        APIResult apiResult = new APIResult();
        try {
            System.out.println(event.getClass().getSimpleName() + ".process();");
            apiResult = event.process();
        }  catch (RuntimeException e) {
            apiResult.setSuccess(false);
            apiResult.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            StringBuilder message = new StringBuilder(e.getMessage() != null ? e.getMessage() : e.toString()).append("\n");
            int i = 0;
            for (StackTraceElement element : e.getStackTrace()) {
                message.append(element.toString()).append("\n");
                if (i++ > 5) {
                    break;
                }
            }
            apiResult.setMessage(message.toString());
        }
        return apiResult;

    }
}
