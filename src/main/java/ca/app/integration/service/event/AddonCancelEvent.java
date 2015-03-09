package ca.app.integration.service.event;

import ca.app.integration.type.ErrorCode;
import ca.app.integration.type.EventType;
import ca.app.integration.vo.APIResult;
import ca.app.user.service.ISVService;
import ca.app.user.vo.AddonBean;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddonCancelEvent extends AbstractEvent {

    @Autowired
    private ISVService isvService;

    public APIResult process(){
        APIResult apiResult = new APIResult();
        if (eventInfo.getType() != EventType.ADDON_CANCEL) {
            throw new RuntimeException("eventInfo not of the right type.");
        }
        try {
            AddonBean addonBean = new AddonBean();
            addonBean.setAddonIdentifier(eventInfo.getPayload().getAddonInstance().getId());
            isvService.deleteAddon(addonBean);
            apiResult.setSuccess(true);
            apiResult.setMessage(String.format("Successfully cancel addon: %s", eventInfo.getPayload().getAddonInstance().getId()));
        } catch (ObjectNotFoundException onfe) {
            apiResult.setSuccess(false);
            apiResult.setErrorCode(ErrorCode.ACCOUNT_NOT_FOUND);
            apiResult.setMessage(onfe.getMessage());
        }
        return apiResult;
    }
}
