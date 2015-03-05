package ca.app.wsController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionsController {
    
    @RequestMapping("/create")
    public void create(@RequestParam(value="url") String eventUrl) {
        System.out.println("create eventUrl: " + eventUrl);
        //return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }

    @RequestMapping("/change")
    public void change(@RequestParam(value="token") String eventUrl) {
        System.out.println("change eventUrl: " + eventUrl);
    }

    @RequestMapping("/cancel")
    public void cancel(@RequestParam(value="token") String eventUrl) {
        System.out.println("cancel eventUrl: " + eventUrl);
    }

    @RequestMapping("/status")
    public void status(@RequestParam(value="token") String eventUrl) {
        System.out.println("status   eventUrl: " + eventUrl);
    }
}
