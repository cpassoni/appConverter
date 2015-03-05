package ca.app.wsController;

import ca.app.integration.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class SubscriptionsController {
    
    @Autowired
    SubscribeService subscribeService;

    //@RequestMapping( method= RequestMethod.GET,  produces={"application/json","application/xml"})
    @RequestMapping( value = "/event", method= RequestMethod.GET)
    @ResponseBody
    public void create(@RequestParam(value="url") String eventUrl) {
        System.out.println("create eventUrl: " + eventUrl);
        subscribeService.processEvent(eventUrl, "asdf");
        //return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }

}
