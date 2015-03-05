package ca.app.integration.vo;

import ca.app.integration.type.NoticeType;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * The XML payload that is returned for Subscription Notice events
 *
 * @author Steve Weis (steve.weis@appdirect.com)
 */
@XmlRootElement(name = "notice")
public class NoticeInfo implements Serializable {
	private static final long serialVersionUID = 482426190291639798L;

	private NoticeType type;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NoticeType getType() {
		return type;
	}

	public void setType(NoticeType type) {
		this.type = type;
	}
}
