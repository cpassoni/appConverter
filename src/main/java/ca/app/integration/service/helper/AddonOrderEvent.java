package ca.app.integration.service.helper;

import ca.app.integration.type.ErrorCode;
import ca.app.integration.type.EventType;
import ca.app.integration.vo.APIResult;
import ca.app.integration.vo.OrderInfo;
import ca.app.user.service.ISVService;
import ca.app.user.vo.AccountBean;
import ca.app.user.vo.AddonBean;
import org.apache.tomcat.jni.Time;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddonOrderEvent extends AbstractEvent {

    @Autowired
    private ISVService isvService;

    public APIResult process(){
        if (eventInfo.getType() != EventType.ADDON_ORDER) {
            throw new RuntimeException("eventInfo not of the right type.");
        }
        APIResult result = new APIResult();
        try {
            AccountBean accountBean = new AccountBean();
            accountBean.setUuid(eventInfo.getPayload().getAccount().getAccountIdentifier());
            AddonBean addonBean = new AddonBean();
            OrderInfo orderInfo = eventInfo.getPayload().getOrder();
            addonBean.setCode(orderInfo.getAddonOfferingCode());
            addonBean.setAddonIdentifier(String.format("%s-%s-%s", accountBean.getUuid(), addonBean.getCode(), Time.now()));
            if (!orderInfo.getItems().isEmpty()) {
                addonBean.setQuantity(orderInfo.getItems().get(0).getQuantity());
            }
            isvService.createAddon(addonBean, accountBean);
            result.setSuccess(true);
            result.setId(addonBean.getAddonIdentifier());
        } catch (ObjectNotFoundException onfe) {
            result.setSuccess(false);
            result.setErrorCode(ErrorCode.ACCOUNT_NOT_FOUND);
            result.setMessage(onfe.getMessage());
        }
        return result;
    }
}
