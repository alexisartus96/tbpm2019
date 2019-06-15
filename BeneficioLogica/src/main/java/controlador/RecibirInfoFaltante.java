package controlador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.form.FormData;

import persistencia.Solicitud;

public class RecibirInfoFaltante implements TaskListener{

	private static EntityManager em;

	public void notify(DelegateTask execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		
		Query q = em.createNamedQuery("Solicitud.byId");
		q.setParameter("ci", execution.getVariable("solicitud_invalida"));
		try {
			if(execution.getVariable("identificacion") instanceof Long) {
				q.setParameter("idProceso",(Long)execution.getVariable("identificacion"));
			}else if(execution.getVariable("identificacion") instanceof Integer) {
				q.setParameter("idProceso",(Integer)execution.getVariable("identificacion"));
			}else if(execution.getVariable("identificacion") instanceof String) {
				q.setParameter("idProceso",new Long((String)execution.getVariable("identificacion")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Solicitud solicitud= (Solicitud) q.getSingleResult();
		
		execution.setVariable("apellido", solicitud.getNombre());
		execution.setVariable("nombre", solicitud.getApellido());
		execution.setVariable("informacion_familiar", solicitud.getIntegrantesFamilia());
		execution.setVariable("ci", solicitud.getId().getCi());
		execution.setVariable("nivel_educativo", solicitud.getNivelEducativo());
		execution.setVariable("ingresos", solicitud.getIngresos());
		execution.setVariable("integrantes_familia", solicitud.getIntegrantesFamilia());
		execution.setVariable("otros_ingresos", solicitud.getOtrosIngresos());
		
	}
	
}
