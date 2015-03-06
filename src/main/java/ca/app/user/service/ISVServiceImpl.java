package ca.app.user.service;

import ca.app.user.dao.GenericDAO;
import ca.app.user.model.Account;
import ca.app.user.model.Addon;
import ca.app.user.model.User;
import ca.app.user.vo.AccountBean;
import ca.app.user.vo.AddonBean;
import ca.app.user.vo.UserBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "isvService")
public class ISVServiceImpl implements ISVService {

	@Autowired
	private GenericDAO<Account, Long> accountDao;
	@Autowired
	private GenericDAO<User, Long> userDao;
	@Autowired
	private GenericDAO<Addon, Long> addonDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long createAccount(AccountBean accountBean, UserBean adminBean) {
		User admin = new User(adminBean);
		admin.setAdmin(true);
		Account account = new Account(accountBean);
		admin.setAccount(account);
		account.getUsers().add(admin);
		accountDao.saveOrUpdate(account);
		return account.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createUser(UserBean userBean, AccountBean accountBean) {
		Account account = readAccount(accountBean);
		User user = new User(userBean);
		user.setAccount(account);
		account.getUsers().add(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserBean readUserByOpenID(String openId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("openId", openId));
		User user = userDao.findUniqueByCriteria(User.class, criteria);
		if (user == null) {
			return null;
		}
		return new UserBean(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserBean> readUsers() {
		List<UserBean> userBeans = new ArrayList<UserBean>();
		List<User> users = userDao.findAll();
		for (User user : users) {
			userBeans.add(new UserBean(user));
		}
		return userBeans;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public AccountBean readAccountByUUID(String accountUuid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Account.class)
			.add(Restrictions.eq("uuid", accountUuid));
		Account account = accountDao.findUniqueByCriteria(Account.class, criteria);
		if (account == null) {
			return null;
		}
		return new AccountBean(account);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public AccountBean readAccountByID(Long accountId) {
        return new AccountBean(accountDao.findById(accountId));
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public AccountBean readAccountByUserID(Long userId) {
        User user = userDao.findById(userId);
        return new AccountBean(user.getAccount());
    }

	private Account readAccount(AccountBean accountBean) {
		Account account = null;
		if (account == null && accountBean.getId() != null) {
			DetachedCriteria criteria = DetachedCriteria.forClass(Account.class)
				.add(Restrictions.eq("id", accountBean.getId()));
			account = accountDao.findUniqueByCriteria(Account.class, criteria);
		}
		if (account == null && accountBean.getUuid() != null) {
			DetachedCriteria criteria = DetachedCriteria.forClass(Account.class)
				.add(Restrictions.eq("uuid", accountBean.getUuid()));
			account = accountDao.findUniqueByCriteria(Account.class, criteria);
		}
		if (account == null) {
            return null;
			//throw new ObjectNotFoundException(accountBean.toString());
		}
		return account;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AccountBean> readAccounts() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Account.class)
				.addOrder(Order.desc("id"));
		List<Account> accounts = accountDao.findByCriteria(Account.class, criteria, 0, 25);
		List<AccountBean> accountBeans = new ArrayList<AccountBean>(accounts.size());
		for (Account account : accounts) {
			accountBeans.add(new AccountBean(account));
		}
		return accountBeans;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(UserBean userBean) {
		User user = userDao.findById(userBean.getId());
		user.populate(userBean);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(AccountBean accountBean) {
		Account account = readAccount(accountBean);
		account.populate(accountBean);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(AccountBean accountBean) {
		Account account = readAccount(accountBean);
		accountDao.delete(account);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(Long userId) {
		User user = userDao.findById(userId);
		user.getAccount().getUsers().remove(user);
		user.setAccount(null);
		userDao.delete(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createAddon(AddonBean addonBean, AccountBean accountBean) {
		Account account = readAccount(accountBean);
		Addon addon = new Addon(addonBean);
		addon.setAccount(account);
		account.getAddons().add(addon);
	}

	private Addon readAddon(AddonBean addonBean) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Addon.class)
				.add(Restrictions.eq("addonIdentifier", addonBean.getAddonIdentifier()));
		Addon addon = addonDao.findUniqueByCriteria(Addon.class, criteria);
		if (addon == null) {
			//throw new ObjectNotFoundException(addonBean.toString());
            return null;
		}
		return addon;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAddon(AddonBean addonBean) {
		Addon addon = readAddon(addonBean);
		addon.populate(addonBean);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAddon(AddonBean addonBean) {
		Addon addon = readAddon(addonBean);
		Account account = addon.getAccount();
		account.getAddons().remove(addon);
		addon.setAccount(null);
		addonDao.delete(addon);
	}
}
