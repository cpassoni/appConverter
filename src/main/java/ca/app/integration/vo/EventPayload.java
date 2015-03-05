package ca.app.integration.vo;

import java.io.Serializable;
import java.util.HashMap;

public class EventPayload implements Serializable {
	private static final long serialVersionUID = 3080925569209286979L;

	private UserInfo user;
	private CompanyInfo company;
	private AccountInfo account;
	private AddonInstanceInfo addonInstance;
	private AddonBindingInfo addonBinding;
	private OrderInfo order;
	private NoticeInfo notice;
	private HashMap<String, String> configuration = new HashMap<String, String>();

    public EventPayload() {
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public CompanyInfo getCompany() {
        return company;
    }

    public void setCompany(CompanyInfo company) {
        this.company = company;
    }

    public AccountInfo getAccount() {
        return account;
    }

    public void setAccount(AccountInfo account) {
        this.account = account;
    }

    public AddonInstanceInfo getAddonInstance() {
        return addonInstance;
    }

    public void setAddonInstance(AddonInstanceInfo addonInstance) {
        this.addonInstance = addonInstance;
    }

    public AddonBindingInfo getAddonBinding() {
        return addonBinding;
    }

    public void setAddonBinding(AddonBindingInfo addonBinding) {
        this.addonBinding = addonBinding;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }

    public NoticeInfo getNotice() {
        return notice;
    }

    public void setNotice(NoticeInfo notice) {
        this.notice = notice;
    }

    public HashMap<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(HashMap<String, String> configuration) {
        this.configuration = configuration;
    }
}
