package web;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class BaseController {

	
	protected void errorMessage(String errorMessage) {
		addMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
	}

	protected void successMessage(String successMessage) {
		addMessage(FacesMessage.SEVERITY_INFO, successMessage);
	}

	protected void warningMessage(String warnMessage) {
		addMessage(FacesMessage.SEVERITY_WARN, warnMessage);
	}

	private void addMessage(Severity severity, String infoMessage) {
		FacesMessage message = new FacesMessage(severity, infoMessage, infoMessage);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
	}
	
}
