package controlador;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import persistencia.Solicitud;

public class EvaluarSolicitud implements TaskListener{
	private static EntityManager em;
	
	public void notify(DelegateTask delegateTask) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
//		Query q = em.createQuery("SELECT Solicitud FROM solicitud WHERE solicitud.id.ci='"+execution.getVariable("identificacion")+"'");
		Query q = em.createNamedQuery("Solicitud.byId");
		q.setParameter("ci", new Long((String)delegateTask.getVariable("solicitud")));
		q.setParameter("idProceso", delegateTask.getVariable("identificacion"));
		Solicitud solicitud= (Solicitud) q.getSingleResult();
		
		solicitud.setValidado((Boolean) delegateTask.getVariable("solicitud_valida"));
		solicitud.setRangoSubsidio((String)delegateTask.getVariable("rango_subsidio"));
		try {
			solicitud.setPrioridad((Long) delegateTask.getVariable("prioridad"));
		}catch(Exception e) {
			solicitud.setPrioridad((Integer) delegateTask.getVariable("prioridad"));
		}
		em.merge(solicitud);
		et.commit();
		
		delegateTask.getExecution().setVariable("solicitud_valida", null);
		delegateTask.getExecution().setVariable("rango_subsidio", null);
		delegateTask.getExecution().setVariable("prioridad", null);		
	}

}
