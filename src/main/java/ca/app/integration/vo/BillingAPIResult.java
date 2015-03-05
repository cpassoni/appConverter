package ca.app.integration.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "result")
public class BillingAPIResult implements Serializable {
	private static final long serialVersionUID = -7027507409588330850L;

	private boolean success;
	private String message;

    public BillingAPIResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
