package ca.app.wsController;

import ca.app.integration.service.EventService;
import ca.app.integration.vo.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    
    @Autowired
    EventService eventService;

    @RequestMapping( value = "/event", method= RequestMethod.GET)
    @ResponseBody
    public APIResult create(@RequestParam(value="url") String eventUrl, @RequestParam(value="token") String token) {

        System.out.println("create eventUrl: " + eventUrl);
        System.out.println("create token: " + token);
        return eventService.processEvent(eventUrl, token);
    }
}

