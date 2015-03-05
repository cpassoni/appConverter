package ca.app.integration.vo;


import ca.app.integration.type.PricingUnit;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "usageItem")
public class UsageItemBean implements Serializable {
	private static final long serialVersionUID = 7935998093136955903L;

	private PricingUnit unit;
	private BigDecimal quantity;
	private BigDecimal price;
	private String description;

	public PricingUnit getUnit() {
		return unit;
	}

	public void setUnit(PricingUnit unit) {
		this.unit = unit;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
