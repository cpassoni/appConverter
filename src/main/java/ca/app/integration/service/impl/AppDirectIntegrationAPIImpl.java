package ca.app.integration.service.impl;

import ca.app.config.ServerConfiguration;
import ca.app.integration.service.AppDirectIntegrationAPI;
import ca.app.integration.vo.BillingAPIResult;
import ca.app.integration.vo.EventInfo;
import ca.app.integration.vo.UsageBean;
import com.google.gson.Gson;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AppDirectIntegrationAPIImpl implements AppDirectIntegrationAPI {

    @Autowired
    private ServerConfiguration serverConfiguration;

    public EventInfo readEvent(String eventToken) {
        String urlString = (serverConfiguration.getAppDirectBaseUrl().endsWith("/") ? serverConfiguration.getAppDirectBaseUrl() : serverConfiguration.getAppDirectBaseUrl() + "/") + "api/integration/v1/events/" + eventToken + ".json";
        return getAuthJson(urlString, EventInfo.class);
    }
    public BillingAPIResult billUsage(UsageBean usageBean) {

        String url = serverConfiguration.getAppDirectBaseUrl() + "api/integration/v1/billing/usage";
        return getAuthJson(url, BillingAPIResult.class);
    }



    private <T> T getAuthJson(String urlString, Class<T> classOfT ){
        try {
            URL url = new URL(urlString);
            OAuthConsumer consumer = new DefaultOAuthConsumer(serverConfiguration.getOAuthConsumerKey(), serverConfiguration.getOAuthConsumerSecret());
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            consumer.sign(c);
            c.connect();
            int status = c.getResponseCode();
            System.out.println("status : " + status);
            if (status == 200) {
                System.out.println(status);
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                System.out.println("response: "+ sb.toString());
                Gson gson = new Gson();
                return gson.fromJson(sb.toString(), classOfT);
            }
            else
            {
                throw new UnknownError("response: " + status);
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
