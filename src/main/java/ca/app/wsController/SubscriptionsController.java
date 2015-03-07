package ca.app.wsController;

import ca.app.integration.service.SubscribeService;
import ca.app.integration.vo.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionsController {
    
    @Autowired
    SubscribeService subscribeService;

    @RequestMapping( value = "/event", method= RequestMethod.GET)
    @ResponseBody
    public APIResult create(@RequestParam(value="url") String eventUrl) {
        System.out.println("create eventUrl: " + eventUrl);
        return subscribeService.processEvent(eventUrl, "asdf");
    }
}
