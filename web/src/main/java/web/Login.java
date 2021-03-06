package web;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

//import properties.PropertiesMannager;

@Named
@SessionScoped
@ManagedBean
public class Login extends BaseController implements Serializable {
	private static final long serialVersionUID = -247740285157529093L;

	private String user;
	private String password;
	private String activeUser;
	private String usrTipo;
	private Boolean bps;

	public void login() throws IOException {
//		if (user.contentEquals("bps") && password.contentEquals("12345")) {
//			activeUser="bps";
//		} else if (user.contentEquals("beneficios") && password.contentEquals("12345")) {
//			activeUser="beneficios";
//		}
//		activeUser=user;
		System.out.println("hola");
		Long login=(new LoginUsr()).loguin(user, password);
		usrTipo= login.toString();
		if(login> new Long(0)) {
			activeUser=user;
		}else {
			errorMessage("Credenciales Invalidas.");
		}
	}

	public void logout() throws IOException {
		user = null;
		password = null;
		activeUser = null;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		FacesContext.getCurrentInstance().getExternalContext().redirect(servletContext.getContextPath());
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

	public String getUsrTipo() {
		return usrTipo;
	}

	public void setUsrTipo(String usrTipo) {
		this.usrTipo = usrTipo;
	}
}
