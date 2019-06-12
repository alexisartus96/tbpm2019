package web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

//import properties.PropertiesMannager;

@Named
@SessionScoped
public class Login implements Serializable {
	private static final long serialVersionUID = -247740285157529093L;

	private String user;
	private String password;
	private String activeUser;
	private Boolean bps;

	public void login() throws IOException {
		if (user.contentEquals("bps") && password.contentEquals("12345")) {
			activeUser="bps";
		} else if (user.contentEquals("beneficios") && password.contentEquals("12345")) {
			activeUser="beneficios";
		}
		activeUser=user;
	}

	public void logout() throws IOException {
		user = null;
		password = null;
		activeUser = null;
		bps = false;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(externalContext.getApplicationContextPath());
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActiveUser() {
		return activeUser;
	}

	public Boolean getBps() {
		return bps;
	}

	public void setBps(Boolean bps) {
		this.bps = bps;
	}
}
