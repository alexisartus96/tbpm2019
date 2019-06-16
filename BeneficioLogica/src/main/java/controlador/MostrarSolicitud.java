package controlador;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import persistencia.Solicitud;

public class MostrarSolicitud implements TaskListener{
	private static EntityManager em;

	public void notify(DelegateTask delegateTask) {
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		
		Query q=em.createNamedQuery("Solicitud.byId");
		
		
		q.setParameter("ci", new Long((String) delegateTask.getVariable("solicitud")));
		try {
			if(delegateTask.getVariable("identificacion") instanceof Long) {
				q.setParameter("idProceso",(Long)delegateTask.getVariable("identificacion"));
			}else if(delegateTask.getVariable("identificacion") instanceof Integer) {
				q.setParameter("idProceso",(Integer)delegateTask.getVariable("identificacion"));
			}else if(delegateTask.getVariable("identificacion") instanceof String) {
				q.setParameter("idProceso",new Long((String)delegateTask.getVariable("identificacion")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		
		Solicitud solicitud=(Solicitud) q.getSingleResult();
		
		et.commit();	
		
		
		delegateTask.getExecution().setVariable("apellido", solicitud.getNombre());
		delegateTask.getExecution().setVariable("nombre", solicitud.getApellido());
		delegateTask.getExecution().setVariable("informacion_familiar", solicitud.getDatosFamiliares());
		delegateTask.getExecution().setVariable("ci", solicitud.getId().getCi());
		delegateTask.getExecution().setVariable("nivel_educativo", solicitud.getNivelEducativo());
		delegateTask.getExecution().setVariable("ingresos", solicitud.getIngresos());
		delegateTask.getExecution().setVariable("integrantes_familia", solicitud.getIntegrantesFamilia());
		delegateTask.getExecution().setVariable("otros_ingresos", solicitud.getOtrosIngresos());
		delegateTask.getExecution().setVariable("mail_solicitante", solicitud.getMailSolicitante());
		delegateTask.getExecution().setVariable("rango_subsidio", solicitud.getRangoSubsidio());
		delegateTask.getExecution().setVariable("prioridad", solicitud.getPrioridad());
		delegateTask.getExecution().setVariable("solicitud_valida", solicitud.getValidado());
		delegateTask.getExecution().setVariable("url_datos_solicitante", solicitud.getUrl_doc());
		
	}

}
