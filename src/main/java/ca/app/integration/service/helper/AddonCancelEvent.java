package ca.app.integration.service.helper;

import ca.app.integration.vo.APIResult;

public class AddonCancelEvent extends Event {
    public APIResult process(){
        APIResult apiResult = new APIResult();
        apiResult.setSuccess(true);
        return apiResult;
    }
}
