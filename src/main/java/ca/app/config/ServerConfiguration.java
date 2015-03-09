package ca.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfiguration {
    @Value("${appdirect.base.url}")
    private String appDirectBaseUrl;

    @Value("${oauth.consumer.key}")
    private String oAuthConsumerKey;

    @Value("${oauth.consumer.secret}")
    private String oAuthConsumerSecret;

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
