package ca.app.integration.service.helper;

import ca.app.integration.type.ErrorCode;
import ca.app.integration.vo.APIResult;
import ca.app.user.service.ISVService;
import ca.app.user.vo.AccountBean;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionChangeEvent extends Event {

    @Autowired
    private ISVService isvService;

    public APIResult process(){
        APIResult result = new APIResult();
        try {
            AccountBean accountBean = new AccountBean();
            accountBean.setUuid(eventInfo.getPayload().getAccount().getAccountIdentifier());
            accountBean.setEditionCode(eventInfo.getPayload().getOrder().getEditionCode());
            accountBean.setMaxUsers(eventInfo.getPayload().getOrder().getMaxUsers());
            accountBean.setAppDirectManaged(true);
            accountBean.setAppDirectBaseUrl("appDirectBaseUrl"); //TODO: fix - me
            isvService.update(accountBean);
            result.setSuccess(true);
            result.setMessage(String.format("Successfully updated account with identifier %s", eventInfo.getPayload().getAccount().getAccountIdentifier()));
        } catch (ObjectNotFoundException onfe) {
            result.setSuccess(false);
            result.setErrorCode(ErrorCode.ACCOUNT_NOT_FOUND);
            result.setMessage(String.format("Could not find account with identifier %s", eventInfo.getPayload().getAccount().getAccountIdentifier()));
        }
        return result;
    }

}
