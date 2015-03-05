package ca.app.integration.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "addonBinding")
public class AddonBindingInfo implements Serializable {
	private static final long serialVersionUID = 3688351528247437199L;

	private String id;

    public AddonBindingInfo() {
    }

    public AddonBindingInfo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
