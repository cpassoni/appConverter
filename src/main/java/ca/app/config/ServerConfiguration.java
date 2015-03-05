package ca.app.config;

import org.springframework.stereotype.Component;

@Component
public class ServerConfiguration {
    //@Value("${appdirect.base.url}")
    private String appDirectBaseUrl = "https://www.appdirect.com";

//    @Value("${oauth.consumer.key}")
    private String oAuthConsumerKey = "asdf";

//    @Value("${oauth.consumer.secret}")
    private String oAuthConsumerSecret = "asdf";

    public String getAppDirectBaseUrl() {
        return appDirectBaseUrl;
    }

    public String getOAuthConsumerKey() {
        return oAuthConsumerKey;
    }

    public String getOAuthConsumerSecret() {
        return oAuthConsumerSecret;
    }
}
