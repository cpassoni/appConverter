package ca.app.user.vo;

import ca.app.user.model.Addon;

import java.io.Serializable;

public class AddonBean implements Serializable {
	private static final long serialVersionUID = 8804813209638282820L;

	private Long id;
	private String addonIdentifier;
	private String code;
	private Integer quantity;

	public AddonBean() {
		super();
	}

	public AddonBean(Addon addon) {
		setId(addon.getId());
		setAddonIdentifier(addon.getAddonIdentifier());
		setCode(addon.getCode());
		setQuantity(addon.getQuantity());
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
