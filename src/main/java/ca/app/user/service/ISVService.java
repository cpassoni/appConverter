package ca.app.user.service;

import ca.app.user.vo.AccountBean;
import ca.app.user.vo.AddonBean;
import ca.app.user.vo.UserBean;

import java.util.List;

public interface ISVService {
	public Long createAccount(AccountBean accountBean, UserBean adminBean);

	public void createUser(UserBean userBean, AccountBean accountBean);

	public UserBean readUserByOpenID(String openId);

	public List<UserBean> readUsers();

	public AccountBean readAccountByUUID(String accountUuid);

	public AccountBean readAccountByID(Long accountId);

	public AccountBean readAccountByUserID(Long userId);

	public List<AccountBean> readAccounts();

	public void update(AccountBean accountBean);

	public void update(UserBean userBean);

	public void delete(AccountBean accountBean);

	public void deleteUser(Long userId);

	public void createAddon(AddonBean addonBean, AccountBean accountBean);

	public void updateAddon(AddonBean addonBean);

	public void deleteAddon(AddonBean addonBean);
}
