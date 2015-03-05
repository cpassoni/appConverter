package ca.app.service.impl;


import ca.app.service.OAuthService;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OAuthServiceImpl implements OAuthService{

    @Override
    public void fetch(String url) {
        OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
        URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/dummyChange");
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        consumer.sign(request);
        request.connect();
        
    }
}
