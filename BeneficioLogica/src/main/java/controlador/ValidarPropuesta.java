package controlador;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ValidarPropuesta implements ExecutionListener{

	public void notify(DelegateExecution execution) {
		if((Boolean)execution.getVariable("aprobarcronograma") &&
				(Boolean)execution.getVariable("aprobarmontopropuesto") &&
				(Boolean)execution.getVariable("aprobar_comision_evaluadora")) {
			
			execution.setVariable("propuesta_validada", "true");
		}
				
		
	}
	
	

}
