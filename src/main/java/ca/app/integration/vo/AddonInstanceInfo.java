package ca.app.integration.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "addonInstance")
public class AddonInstanceInfo implements Serializable {
	private static final long serialVersionUID = 5370444679965938449L;

	private String id;

    public AddonInstanceInfo() {
    }

    public AddonInstanceInfo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
