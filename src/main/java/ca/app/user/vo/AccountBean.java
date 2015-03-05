package ca.app.user.vo;


import ca.app.user.model.Account;
import ca.app.user.model.Addon;
import ca.app.user.model.User;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "account")
public class AccountBean implements Serializable {
	private static final long serialVersionUID = 2991393185869834690L;

	private Long id;
	private String uuid;
	private String name;
	private List<UserBean> users = new ArrayList<UserBean>();
	private String editionCode;
	private Integer maxUsers = null;
	private boolean appDirectManaged = false;
	private String appDirectBaseUrl;
	private List<AddonBean> addons = new ArrayList<AddonBean>();

	public AccountBean() {
		super();
	}

	public AccountBean(Account account) {
		this.id = account.getId();
		this.uuid = account.getUuid();
		this.name = account.getName();
		for (User user : account.getUsers()) {
			this.users.add(new UserBean(user));
		}
		for (Addon addon : account.getAddons()) {
			this.addons.add(new AddonBean(addon));
		}
		this.editionCode = account.getEditionCode();
		this.maxUsers = account.getMaxUsers();
		this.appDirectManaged = account.isAppDirectManaged();
		this.appDirectBaseUrl = account.getAppDirectBaseUrl();
	}

	@XmlTransient
	public Long getId() {
		return id;
	}

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserBean> getUsers() {
        return users;
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public boolean isAppDirectManaged() {
        return appDirectManaged;
    }

    public void setAppDirectManaged(boolean appDirectManaged) {
        this.appDirectManaged = appDirectManaged;
    }

    public String getAppDirectBaseUrl() {
        return appDirectBaseUrl;
    }

    public void setAppDirectBaseUrl(String appDirectBaseUrl) {
        this.appDirectBaseUrl = appDirectBaseUrl;
    }

    public List<AddonBean> getAddons() {
        return addons;
    }

    public void setAddons(List<AddonBean> addons) {
        this.addons = addons;
    }
}
