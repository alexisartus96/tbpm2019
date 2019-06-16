package controlador;

import java.io.IOException;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import alfrescoConection.InteractiveAuthentication;

public class LoginAlfresco implements TaskListener{

	public void notify(DelegateTask delegateTask) {
		// Se obtiene ticket de autenticación de login
		String alfrescoTiccketURL = "http://127.0.0.1:8080/alfresco/service/api/login?u=admin&pw=admin";
		InteractiveAuthentication ticket = new InteractiveAuthentication();
		try {
			String ticketURLResponse = ticket.getTicket(alfrescoTiccketURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
