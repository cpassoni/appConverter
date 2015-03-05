package ca.app.integration.service;

import ca.app.integration.vo.APIResult;

public interface SubscribeService {
    
    public APIResult processEvent(String eventUrl, String token);
}
