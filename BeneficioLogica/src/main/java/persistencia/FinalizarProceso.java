package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FinalizarProceso implements ExecutionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static EntityManager em;
	
	@Transactional
	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		Query q = em.createNamedQuery("Proceso.byId");
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
		Proceso proceso = (Proceso) q.getSingleResult();
		proceso.setFinalizado(true);
		em.merge(proceso);
		et.commit();
	}

}
