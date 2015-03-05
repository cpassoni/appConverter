package ca.app.integration.service.impl;

import ca.app.config.ServerConfiguration;
import ca.app.integration.service.AppDirectIntegrationAPI;
import ca.app.integration.vo.BillingAPIResult;
import ca.app.integration.vo.EventInfo;
import ca.app.integration.vo.UsageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AppDirectIntegrationAPIImpl implements AppDirectIntegrationAPI {

    @Autowired
    private ServerConfiguration serverConfiguration;

    public EventInfo readEvent(String eventToken){
        RestTemplate restTemplate = new RestTemplate();
        String url = (serverConfiguration.getAppDirectBaseUrl().endsWith("/")?serverConfiguration.getAppDirectBaseUrl():serverConfiguration.getAppDirectBaseUrl()+"/") + "api/integration/v1/events/{token}";
        return  restTemplate.getForObject(url, EventInfo.class, eventToken);
    }

    public BillingAPIResult billUsage(UsageBean usageBean){
        RestTemplate restTemplate = new RestTemplate();
        String url = serverConfiguration.getAppDirectBaseUrl() + "api/integration/v1/billing/usage";
        return  restTemplate.getForObject(url, BillingAPIResult.class);

    }

}
