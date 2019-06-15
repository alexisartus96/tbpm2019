package controlador;

import java.beans.ExceptionListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import persistencia.Solicitud;

public class CargarValida implements ExecutionListener{

	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		Query q=em.createNamedQuery("Solicitud.byId");
		q.setParameter("ci", new Long((String)execution.getVariable("solicitud")));
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
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		
		Solicitud solicitud=(Solicitud) q.getSingleResult();
		
		et.commit();	
		
		
		execution.setVariable("solicitud_valida", solicitud.getValidado());
		
	}

}
