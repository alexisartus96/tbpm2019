package persistencia;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class IngresarSolicitud implements TaskListener{

	public void notify(DelegateTask delegateTask) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		HashMap<String, Object> variables= (HashMap<String, Object>) delegateTask.getVariables();
		
		Solicitud solicitud= new Solicitud();
		SolicitudPK soliPK = new SolicitudPK();
		try {
			soliPK.setCi((Long) variables.get("ci"));
		}catch(Exception e) {
			soliPK.setCi((Integer) variables.get("ci"));
		}
		try {
			soliPK.setIdProceso((Long) variables.get("identificacion"));
		}catch(Exception e) {
			soliPK.setCi((Integer) variables.get("identificacion"));
		}
		solicitud.setId(soliPK);
		
		solicitud.setApellido((String) variables.get("apellido"));
		solicitud.setDatosFamiliares((String) variables.get("informacion_familiar"));
		
		try {
			solicitud.setIngresos((Long) variables.get("ingresos"));
		}catch(Exception e) {
			solicitud.setIngresos((Integer) variables.get("ingresos"));
		}
		solicitud.setMailSolicitante((String)variables.get("mail_solicitante"));
		solicitud.setNombre((String)variables.get("nombre"));
		solicitud.setNivelEducativo((String) variables.get("nivel_educativo"));
		try {
			solicitud.setIntegrantesFamilia((Long) variables.get("integrantes_familia"));
		}catch(Exception e) {
			solicitud.setIntegrantesFamilia((Integer) variables.get("integrantes_familia"));
		}
		try {
			solicitud.setOtrosIngresos((Long) variables.get("otros_ingresos"));
		}catch(Exception e) {
			solicitud.setOtrosIngresos((Integer) variables.get("otros_ingresos"));
		}
		if(((String)delegateTask.getTaskDefinitionKey()).compareTo("ingresar_solicitud")==0 || ((String)delegateTask.getTaskDefinitionKey()).compareTo("Recibir_info_faltante")==0) {
			solicitud.setValidado(true);
		}else {
			solicitud.setValidado((Boolean) delegateTask.getVariable("solicitud_valida"));
		}
		
		if(delegateTask.getVariable("rango_subsidio")!=null)solicitud.setRangoSubsidio((String) delegateTask.getVariable("rango_subsidio"));
		
		if(delegateTask.getVariable("prioridad")!=null)solicitud.setPrioridad((Long) delegateTask.getVariable("prioridad"));
		
		if(delegateTask.getVariable("confirmar_subsidio")!=null && (((String)delegateTask.getTaskDefinitionKey()).compareTo("confirmar_subsidio")==0)) {
			solicitud.setConfirmado((Boolean)delegateTask.getVariable("confirmar_subsidio"));
		}
		
		
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
		e.setVariable("solicitud_valida", false);
		e.setVariable("confirmar_subsidio", false);
		e.setVariable("informacion_adicional", null);
		e.setVariable("rango_subsidio", null);
		e.setVariable("prioridad", null);
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		em.merge(solicitud);
		et.commit();

		ArrayList list_solicitudes= (ArrayList) delegateTask.getVariable("list_solicitudes");
//		ArrayList list_solicitudes_invalidas= (ArrayList) delegateTask.getVariable("list_solicitudes_invalidas");
//		
//		if(delegateTask.getVariable("list_solicitudes")==null) {
//			list_solicitudes= new ArrayList();
//		}
//		
//		if(delegateTask.getVariable("list_solicitudes_invalidas")==null) {
//			list_solicitudes_invalidas= new ArrayList();
//		}
//		
//		
//		
//		if(((String)delegateTask.getTaskDefinitionKey()).compareTo("ingresar_solicitud")==0) {
//			list_solicitudes.add(solicitud.getId().getCi());
//		}else {
//			
//			if(solicitud.getValidado()) {
//				//Si no esta en la lista de validas la agrego
//				if(!list_solicitudes.contains(solicitud.getId().getCi())) {
//					list_solicitudes.add(solicitud.getId().getCi());
//					delegateTask.setVariable("list_solicitudes",list_solicitudes);
//				}
//				//Si esta en las invalidas la saco
//				if(((ArrayList) delegateTask.getVariable("list_solicitudes_invalidas")).contains(solicitud.getId().getCi())) {
//					list_solicitudes_invalidas.remove(solicitud.getId().getCi());
//					delegateTask.setVariable("list_solicitudes_invalidas", list_solicitudes_invalidas);
//				}
//			}else {
//				//Si no esta en la lista de validas la agrego
//				if(!list_solicitudes_invalidas.contains(solicitud.getId().getCi())) {
//					list_solicitudes_invalidas.add(solicitud.getId().getCi());
//					delegateTask.setVariable("list_solicitudes_invalidas",list_solicitudes_invalidas);
//				}
//				//Si esta en las invalidas la saco
//				if(list_solicitudes.contains(solicitud.getId().getCi())) {
//					list_solicitudes.remove(solicitud.getId().getCi());
//					delegateTask.setVariable("list_solicitudes", list_solicitudes);
//				}
//			}
//			
//		}
//		e.setVariable("list_solicitudes", list_solicitudes);
//		e.setVariable("list_solicitudes_invalidas", list_solicitudes_invalidas);
//		
	}

}
