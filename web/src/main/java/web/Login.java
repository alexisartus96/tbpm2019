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

	public void login() throws IOException {
//		Properties props = PropertiesMannager.getProperties();

//		if (user.equals(props.getProperty("user.decanato")) && password.equals(props.getProperty("pass.decanato"))) {
//			activeUser = "DECANATO";
//		} else if (user.equals(props.getProperty("user.dgrc")) && password.equals(props.getProperty("pass.dgrc"))) {
//			activeUser = "DGRC";
//		} else {
//			activeUser = null;
//		}
		activeUser=user;
	}

	public void logout() throws IOException {
		user = null;
		password = null;
		activeUser = null;
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
}
