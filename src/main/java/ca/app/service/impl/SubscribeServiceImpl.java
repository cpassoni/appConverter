package ca.app.service.impl;

import ca.app.service.SubscribeService;
import org.springframework.stereotype.Service;

@Service()
public class SubscribeServiceImpl implements SubscribeService {
    public void create(String eventUrl){
        System.out.println("Service create subscribe");
    }
}
