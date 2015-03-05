package ca.app.integration.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "account")
public class AccountInfo implements Serializable {
	private static final long serialVersionUID = -400499571158068365L;

	private String accountIdentifier;
	private String status;

	public AccountInfo() {
		super();
	}

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
