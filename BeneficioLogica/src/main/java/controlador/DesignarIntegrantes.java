package controlador;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class DesignarIntegrantes implements TaskListener{

	public void notify(DelegateTask delegateTask) {

//		delegateTask.getCandidates();
//		delegateTask.getTransientVariables();
//		delegateTask.getTransientVariablesLocal();
//		delegateTask.getVariableInstances();
		Map<String, Object> variables= delegateTask.getVariables();
//		delegateTask.getVariablesLocal();
//		FormService formService= ProcessEngines.getDefaultProcessEngine().getFormService();
//		FormService formService2= ProcessEngines.getProcessEngine(delegateTask.getProcessDefinitionId()).getFormService();
//		
//		StartFormData formData= formService.getStartFormData(delegateTask.getProcessDefinitionId());
//		TaskFormData taskFormData = formService.getTaskFormData(delegateTask.getTaskDefinitionKey());
//		
//		Map<String, String> properties= new HashMap<String, String>();
//		properties.put("prueba", "pruevavalor");
//		
//		formService.submitStartFormData(delegateTask.getProcessDefinitionId(), properties);
		delegateTask.addCandidateUser((String)variables.get("integrante1"));
		delegateTask.addCandidateUser((String)variables.get("integrante2"));
		delegateTask.addCandidateUser((String)variables.get("integrante3"));
		
//		System.out.println("paso designar");
		
	}

}
