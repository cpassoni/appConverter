package ca.app.integration.service.helper;

import ca.app.integration.type.ErrorCode;
import ca.app.integration.type.EventType;
import ca.app.integration.vo.APIResult;
import ca.app.user.dao.GenericDAO;
import ca.app.user.model.User;
import ca.app.user.vo.AccountBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserUnassignmentEvent extends Event {

    @Autowired
    private GenericDAO<User, Long> userDao;

    public APIResult process(){
        if (eventInfo.getType() != EventType.USER_UNASSIGNMENT) {
            throw new RuntimeException("eventInfo not of the right type.");
        }
        APIResult result = new APIResult();
        try {
            AccountBean accountBean = new AccountBean();
            accountBean.setUuid(eventInfo.getPayload().getAccount().getAccountIdentifier());
            User user = readUserByOpenID(eventInfo.getPayload().getUser().getOpenId());
            if (!StringUtils.equals(accountBean.getUuid(), user.getAccount().getUuid())) {
                // The user account is not the same as the account passed
                // in. We can't allow that. Fail.
                result.setSuccess(false);
                result.setErrorCode(ErrorCode.UNAUTHORIZED);
                result.setMessage("User does not belong to the expected account.");
            } else {
                user.getAccount().getUsers().remove(user);
                user.setAccount(null);
                this.userDao.delete(user);
                result.setMessage("Successfully deleted user: " + eventInfo.getPayload().getUser().getOpenId());
            }
        } catch (ObjectNotFoundException onfe) {
            // The user could not be found. Fail.
            result.setSuccess(false);
            result.setErrorCode(ErrorCode.USER_NOT_FOUND);
            result.setMessage(onfe.getMessage());
        }
        return result;
    }


    private User readUserByOpenID(String openId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                .add(Restrictions.eq("openId", openId));
        List<User> users = this.userDao.findByCriteria(User.class, criteria);
        if (users != null && users.size() == 1) {
            return users.get(0);
        }
        throw new ObjectNotFoundException(openId, User.class.toString());
    }
}
