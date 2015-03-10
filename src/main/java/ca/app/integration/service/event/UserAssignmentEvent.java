package ca.app.integration.service.event;

import ca.app.integration.type.ErrorCode;
import ca.app.integration.type.EventType;
import ca.app.integration.vo.APIResult;
import ca.app.user.dao.GenericDAO;
import ca.app.user.model.User;
import ca.app.user.service.ISVService;
import ca.app.user.vo.AccountBean;
import ca.app.user.vo.UserBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    Class to handle assignment user
*/
@Service
public class UserAssignmentEvent extends AbstractEvent {


    @Autowired
    private GenericDAO<User, Long> userDao;

    @Autowired
    private ISVService isvService;


    private static final String ZIP_CODE_KEY = "zipCode";
    private static final String DEPARTMENT_KEY = "department";
    private static final String TIMEZONE_KEY = "timezone";
    private static final String APP_ADMIN = "appAdmin";


    public APIResult process(){

        if (eventInfo.getType() != EventType.USER_ASSIGNMENT) {
            throw new RuntimeException("eventInfo not of the right type.");
        }
        APIResult result = new APIResult();
        AccountBean accountBean = new AccountBean();
        accountBean.setUuid(eventInfo.getPayload().getAccount().getAccountIdentifier());
        // Read info about the user.
        UserBean userBean = new UserBean();
        userBean.setUuid(eventInfo.getPayload().getUser().getUuid());
        userBean.setOpenId(eventInfo.getPayload().getUser().getOpenId());
        userBean.setEmail(eventInfo.getPayload().getUser().getEmail());
        userBean.setFirstName(eventInfo.getPayload().getUser().getFirstName());
        userBean.setLastName(eventInfo.getPayload().getUser().getLastName());
        boolean admin = false;
        if (eventInfo.getPayload().getUser().getAttributes() != null) {
            userBean.setZipCode(eventInfo.getPayload().getUser().getAttributes().get(ZIP_CODE_KEY));
            userBean.setDepartment(eventInfo.getPayload().getUser().getAttributes().get(DEPARTMENT_KEY));
            userBean.setTimezone(eventInfo.getPayload().getUser().getAttributes().get(TIMEZONE_KEY));
            admin = Boolean.parseBoolean(eventInfo.getPayload().getUser().getAttributes().get(APP_ADMIN));
        }
        userBean.setAdmin(admin);
        // AppDirect is trying to create a new user.
        System.out.printf("search by %s \n", userBean.getOpenId());
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class).add(Restrictions.eq("openId", userBean.getOpenId()));
        List<User> existingUsers = userDao.findByCriteria(User.class, criteria);
        User existingUser = existingUsers == null || existingUsers.isEmpty() ? null : existingUsers.get(0);
        if (existingUser != null) {
            if (StringUtils.equals(existingUser.getAccount().getUuid(), accountBean.getUuid())) {
                result.setSuccess(true);
                result.setErrorCode(null);
                result.setMessage("A user with this OpenID already exists but was mapped correctly.");
            } else {
                // A user with the same OpenID already exists in a different account.
                // Fail.
                result.setSuccess(false);
                result.setErrorCode(ErrorCode.USER_ALREADY_EXISTS);
                result.setMessage("A user with this OpenID or email already exists.");
            }
        } else {
            try {
                // Create the new user.
                isvService.createUser(userBean, accountBean);
                result.setSuccess(true);
                result.setMessage("Successfully created user: " + userBean.getUsername());
            } catch (ObjectNotFoundException onfe) {
                // The account could not be found. Fail.
                result.setSuccess(false);
                result.setErrorCode(ErrorCode.ACCOUNT_NOT_FOUND);
                result.setMessage(onfe.getMessage());
            }
        }
        return result;

    }
}
