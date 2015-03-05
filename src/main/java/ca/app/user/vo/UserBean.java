package ca.app.user.vo;

import ca.app.security.vo.Role;
import ca.app.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Collections;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class UserBean implements UserDetails {
	private static final long serialVersionUID = -8955516091357057444L;

	@XmlTransient
	private Long id;
	private String uuid;
	private String openId;
	private String email;
	private String firstName;
	private String lastName;
	private String zipCode;
	private String department;
	private String timezone;
	private boolean admin = false;

    public UserBean() {
    }

    public UserBean(User user) {
		this.id = user.getId();
		this.uuid = user.getUuid();
		this.openId = user.getOpenId();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.zipCode = user.getZipCode();
		this.department = user.getDepartment();
		this.timezone = user.getTimezone();
		this.admin = user.isAdmin();
	}

	@Override
	public String getUsername() {
		return uuid;
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return Collections.singleton((GrantedAuthority) Role.USER);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
