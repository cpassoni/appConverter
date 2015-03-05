package ca.app.user.model;


import ca.app.user.vo.AddonBean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "isv_addons")
public class Addon implements Serializable {
	private static final long serialVersionUID = 560036415271867806L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "addon_identifier", unique = true)
	private String addonIdentifier;

	@Column(name = "code")
	private String code;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@Column(name = "quantity")
	private Integer quantity;

	public Addon() {
		super();
	}

	public Addon(AddonBean addonBean) {
		super();
		populate(addonBean);
	}

	public void populate(AddonBean addonBean) {
		setAddonIdentifier(addonBean.getAddonIdentifier());
		setCode(addonBean.getCode());
		setQuantity(addonBean.getQuantity());
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddonIdentifier() {
        return addonIdentifier;
    }

    public void setAddonIdentifier(String addonIdentifier) {
        this.addonIdentifier = addonIdentifier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
