package ca.app.integration.service.helper;

import ca.app.config.ServerConfiguration;
import ca.app.integration.service.AppDirectIntegrationAPI;
import ca.app.integration.vo.EventInfo;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventFactoryImpl implements EventFactory{

    @Autowired
    private ServerConfiguration serverConfiguration;

    @Autowired
    private AppDirectIntegrationAPI api;

    @Autowired private SubscriptionOrderEvent subscriptionOrderEvent;
    @Autowired private SubscriptionChangeEvent subscriptionChangeEvent;
    @Autowired private SubscriptionCancelEvent subscriptionCancelEvent;
    @Autowired private UserAssignmentEvent userAssignmentEvent;
    @Autowired private UserUnassignmentEvent userUnassignmentEvent;
    @Autowired private AddonOrderEvent addonOrderEvent;
    @Autowired private AddonChangeEvent addonChangeEvent;
    @Autowired private AddonCancelEvent addonCancelEvent;

    public Event getSubscribeEvent(String eventUrl, String token){
        String basePath = serverConfiguration.getAppDirectBaseUrl();

        if (!Strings.isNullOrEmpty(eventUrl)) {
            basePath = extractBasePath(eventUrl);
            token = extractToken(eventUrl).get();
        }
//        AppDirectIntegrationAPI api = JAXRSClientFactory.create(basePath, AppDirectIntegrationAPI.class);
//        appDirectIntegrationAPI

        final EventInfo eventInfo = api.readEvent(token);
        Event ret = null;
        switch(eventInfo.getType()) {
            case SUBSCRIPTION_ORDER:
                ret = subscriptionOrderEvent;
                break;
            case SUBSCRIPTION_CHANGE:
                ret = subscriptionChangeEvent;
//                processSubscriptionChangeEvent(basePath, eventInfo, result);
                break;
            case SUBSCRIPTION_CANCEL:
                ret = subscriptionCancelEvent;
                //processSubscriptionCancelEvent(eventInfo, result);
                break;
            case USER_ASSIGNMENT:
                ret = userAssignmentEvent;
                //processUserAssignmentEvent(eventInfo, result);
                break;
            case USER_UNASSIGNMENT:
                ret = userUnassignmentEvent;
                //processUserUnassignmentEvent(eventInfo, result);
                break;
            case SUBSCRIPTION_NOTICE:
                break;
            case ADDON_ORDER:
                ret = addonOrderEvent;
               // processAddonOrderEvent(eventInfo, result);
                break;
            case ADDON_CHANGE:
                ret = addonChangeEvent;
               // processAddonChangeEvent(eventInfo, result);
                break;
            case ADDON_CANCEL:
                ret = addonCancelEvent;
                // processAddonCancelEvent(eventInfo, result);
                break;
            default:
                return null;
        }
        ((AbstractEvent)ret).setEventInfo(eventInfo);
        return ret;
    }


    private static String extractBasePath(String eventUrl) {
        if (Strings.isNullOrEmpty(eventUrl))
        {
            return null;
        }
        int index = eventUrl.lastIndexOf("/api/integration/v1");
        return eventUrl.substring(0, index);

    }

    private static Optional<String>  extractToken(String eventUrl) {
        String[] path = eventUrl.split("/");
        return Optional.of(path[path.length - 1]);
    }
}
