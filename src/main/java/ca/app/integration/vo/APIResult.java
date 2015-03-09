package ca.app.integration.vo;

import ca.app.integration.type.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlRootElement(name = "result")
public class APIResult implements Serializable {
	private static final long serialVersionUID = -7599199539526987847L;

	private boolean success;
	private boolean asynchronous = false;
	private ErrorCode errorCode;
	private String message;
	private String accountIdentifier;
	private String userIdentifier;
	private String id;

    public APIResult() {
    }

    public APIResult(boolean success, String message) {
		this(success, null, message);
	}

	public APIResult(boolean success, ErrorCode errorCode, String message) {
		super();
		this.success = success;
		this.errorCode = errorCode;
		this.message = message;
	}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlTransient
	@JsonIgnore
	public boolean isAsynchronous() {
		return asynchronous;
	}

    @Override
    public String toString() {
        return "APIResult{" +
                "success=" + success +
                ", asynchronous=" + asynchronous +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", accountIdentifier='" + accountIdentifier + '\'' +
                ", userIdentifier='" + userIdentifier + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
