package ca.app.user.service;

import ca.app.user.vo.AccountBean;
import ca.app.user.vo.AddonBean;
import ca.app.user.vo.UserBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("service")
public interface ISVService {
	public Long createAccount(AccountBean accountBean, UserBean adminBean);

	public void createUser(UserBean userBean, AccountBean accountBean);

	public UserBean readUserByOpenID(String openId);

	@GET
	@Path("users")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<UserBean> readUsers();

	@GET
	@Path("accounts/{uuid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public AccountBean readAccountByUUID(@PathParam("uuid") String accountUuid);

	public AccountBean readAccountByID(Long accountId);

	public AccountBean readAccountByUserID(Long userId);

	@GET
	@Path("accounts")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<AccountBean> readAccounts();

	public void update(AccountBean accountBean);

	public void update(UserBean userBean);

	public void delete(AccountBean accountBean);

	public void deleteUser(Long userId);

	public void createAddon(AddonBean addonBean, AccountBean accountBean);

	public void updateAddon(AddonBean addonBean);

	public void deleteAddon(AddonBean addonBean);
}
