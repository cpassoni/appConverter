package ca.app.integration.service.impl;

import ca.app.integration.service.SubscribeService;
import ca.app.integration.service.helper.Event;
import ca.app.integration.service.helper.EventFactory;
import ca.app.integration.vo.APIResult;
import ca.app.integration.type.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubscribeServiceImpl implements SubscribeService {

    private static final String UNKNOWN = "unknown";
    private static final String X_FORWARDED_FOR = "x-forwarded-for";
    private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";


    @Autowired
    private EventFactory eventFactory;

    public APIResult processEvent(String eventUrl, String token){
        Event event = eventFactory.getSubscribeEvent(eventUrl, token);
        APIResult apiResult = new APIResult();
        try {
            System.out.println("event.process();");
            apiResult = event.process();
        }  catch (RuntimeException e) {
            apiResult.setSuccess(false);
            apiResult.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            StringBuilder message = new StringBuilder(e.getMessage() != null ? e.getMessage() : e.toString()).append("\n");
            int i = 0;
            for (StackTraceElement element : e.getStackTrace()) {
                message.append(element.toString()).append("\n");
                if (i++ > 5) {
                    break;
                }
            }
            apiResult.setMessage(message.toString());
        }
//        apiResult.setMessage(String.format("From IP: %s. %s", extractIpAddress(request), result.getMessage()));
        return apiResult;

    }
//
//    private String extractIpAddress(HttpServletRequest request) {
//        String ip = request.getHeader(X_FORWARDED_FOR);
//        if (Strings.isNullOrEmpty(ip) || ip.equalsIgnoreCase(UNKNOWN)) {
//            ip = request.getHeader(HTTP_CLIENT_IP);
//        }
//        if (Strings.isNullOrEmpty(ip) || ip.equalsIgnoreCase(UNKNOWN)) {
//            ip = request.getHeader(HTTP_X_FORWARDED_FOR);
//        }
//        if (Strings.isNullOrEmpty(ip) || ip.equalsIgnoreCase(UNKNOWN)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
}
