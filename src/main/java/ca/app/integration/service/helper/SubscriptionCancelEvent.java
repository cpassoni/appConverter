package ca.app.integration.service.helper;

import ca.app.integration.type.ErrorCode;
import ca.app.integration.type.EventType;
import ca.app.integration.vo.APIResult;
import ca.app.user.service.ISVService;
import ca.app.user.vo.AddonBean;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionCancelEvent extends Event {
    @Autowired
    private ISVService isvService;

    public APIResult process(){
        if (eventInfo.getType() != EventType.ADDON_CANCEL) {
            throw new RuntimeException("eventInfo not of the right type.");
        }
        APIResult result = new APIResult();
        try {
            AddonBean addonBean = new AddonBean();
            addonBean.setAddonIdentifier(eventInfo.getPayload().getAddonInstance().getId());
            isvService.deleteAddon(addonBean);
            result.setSuccess(true);
            result.setMessage(String.format("Successfully cancel addon: %s", eventInfo.getPayload().getAddonInstance().getId()));
        } catch (ObjectNotFoundException onfe) {
            result.setSuccess(false);
            result.setErrorCode(ErrorCode.ACCOUNT_NOT_FOUND);
            result.setMessage(onfe.getMessage());
        }
        return result;
    }
}
