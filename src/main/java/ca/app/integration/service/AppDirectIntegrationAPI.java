package ca.app.integration.service;

import ca.app.integration.vo.BillingAPIResult;
import ca.app.integration.vo.EventInfo;
import ca.app.integration.vo.UsageBean;

public interface AppDirectIntegrationAPI {
    public EventInfo readEvent(String basePath);

    public BillingAPIResult billUsage(UsageBean usageBean);

}