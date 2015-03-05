package ca.app.integration.service.helper;

import ca.app.config.ServerConfiguration;
import ca.app.integration.type.ErrorCode;
import ca.app.integration.vo.APIResult;
import ca.app.user.dao.GenericDAO;
import ca.app.user.model.Account;
import ca.app.user.model.User;
import ca.app.user.service.ISVService;
import ca.app.user.vo.AccountBean;
import ca.app.user.vo.UserBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SubscriptionOrderEvent extends Event {
    @Autowired
    private GenericDAO<User, Long> userDao;
    @Autowired
    private ISVService isvService;
    @Autowired
    private ServerConfiguration serverConfiguration;

    public APIResult process(){
        APIResult result = new APIResult();
        // Create the account.
        UserBean adminBean = new UserBean();
        adminBean.setUuid(eventInfo.getCreator().getUuid());
        adminBean.setOpenId(eventInfo.getCreator().getOpenId());
        adminBean.setEmail(eventInfo.getCreator().getEmail());
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                .add(Restrictions.eq("openId", adminBean.getOpenId()));

        List<User> existingUsers = userDao.findByCriteria(User.class, criteria);
        User existingUser = existingUsers == null || existingUsers.isEmpty() ? null : existingUsers.get(0);
        if (existingUser != null) {
            Account existingAccount = existingUser.getAccount();
            if (existingAccount.isAppDirectManaged()) {
                // We already have an AppDirect account mapped for this user. Reconcile.
                existingAccount.setEditionCode(eventInfo.getPayload().getOrder().getEditionCode());
                existingAccount.setMaxUsers(eventInfo.getPayload().getOrder().getMaxUsers());
                result.setSuccess(true);
                result.setErrorCode(null);
                result.setAccountIdentifier(existingAccount.getUuid());
                result.setMessage("An account with this user already exists but is correctly mapped.");
                return result;
            }
            result.setSuccess(false);
            result.setErrorCode(ErrorCode.USER_ALREADY_EXISTS);
            result.setMessage("An account with this user already exists.");
            return result;
        } else {
            adminBean.setFirstName(eventInfo.getCreator().getFirstName());
            adminBean.setLastName(eventInfo.getCreator().getLastName());
            adminBean.setAdmin(true);
            AccountBean accountBean = new AccountBean();
            accountBean.setUuid(eventInfo.getPayload().getCompany().getUuid());
            accountBean.setName(accountBean.getUuid());
            accountBean.setEditionCode(eventInfo.getPayload().getOrder().getEditionCode());
            accountBean.setMaxUsers(eventInfo.getPayload().getOrder().getMaxUsers());
            accountBean.setAppDirectManaged(true);
            accountBean.setAppDirectBaseUrl("appDirectBaseUrl");//TODO: correct me
            isvService.createAccount(accountBean, adminBean);
            result.setSuccess(true);
            result.setAccountIdentifier(accountBean.getUuid());
            return result;
        }
    }
}
