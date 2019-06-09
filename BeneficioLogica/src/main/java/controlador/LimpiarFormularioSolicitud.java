package controlador;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class LimpiarFormularioSolicitud implements TaskListener{

	public void notify(DelegateTask delegateTask) {
		DelegateExecution e =delegateTask.getExecution();
		
		e.setVariable("ci", null);
		e.setVariable("nombre", null);
		e.setVariable("apellido", null);
		e.setVariable("mail_solicitante", null);
		e.setVariable("informacion_familiar", null);
		e.setVariable("ingresos", null);
		e.setVariable("nivel_educativo", null);
		e.setVariable("integrantes_familia", null);
		e.setVariable("otros_ingresos", null);
		e.setVariable("", false);
		
	}

}
