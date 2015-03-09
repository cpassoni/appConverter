package ca.app.integration.service;

import ca.app.integration.vo.APIResult;
import org.springframework.stereotype.Service;

@Service
public interface EventService {
    
    public APIResult processEvent(String eventUrl, String token);
}
