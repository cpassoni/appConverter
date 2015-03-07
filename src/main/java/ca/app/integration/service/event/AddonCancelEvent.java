package ca.app.integration.service.event;

import ca.app.integration.vo.APIResult;
import org.springframework.stereotype.Service;

@Service
public class AddonCancelEvent extends AbstractEvent {
    public APIResult process(){
        APIResult apiResult = new APIResult();
        apiResult.setSuccess(true);
        return apiResult;
    }
}
