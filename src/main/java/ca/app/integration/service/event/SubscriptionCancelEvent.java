package ca.app.integration.service.event;

import ca.app.integration.type.ErrorCode;
import ca.app.integration.type.EventType;
import ca.app.integration.vo.APIResult;
import ca.app.user.service.ISVService;
import ca.app.user.vo.AccountBean;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
Class to handle cancel subscription
*/
@Service
public class SubscriptionCancelEvent extends AbstractEvent {
    @Autowired
    private ISVService isvService;

    public APIResult process(){
        if (eventInfo.getType() != EventType.SUBSCRIPTION_CANCEL) {
            throw new RuntimeException("eventInfo not of the right type.");
        }
        APIResult result = new APIResult();
        try {
            AccountBean accountBean = new AccountBean();
            accountBean.setUuid(eventInfo.getPayload().getAccount().getAccountIdentifier());
            isvService.delete(accountBean);
            result.setSuccess(true);
            result.setMessage(String.format("Successfully deleted account with identifier %s", eventInfo.getPayload().getAccount().getAccountIdentifier()));
        } catch (ObjectNotFoundException e) {
            result.setSuccess(false);
            result.setErrorCode(ErrorCode.ACCOUNT_NOT_FOUND);
            result.setMessage(String.format("Could not find account with identifier %s", eventInfo.getPayload().getAccount().getAccountIdentifier()));
        }
        
        return result;
    }
}
