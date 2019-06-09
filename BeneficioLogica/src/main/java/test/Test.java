package test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class Test implements TaskListener{
	private static EntityManager em;
	
	public void notify(DelegateTask delegateTask) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		Query q=em.createNativeQuery("SELECT id FROM public.test;");
		Object obj= q.getSingleResult();
		
		System.out.println("test ok");
		
	}

}
