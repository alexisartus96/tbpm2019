package controlador;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import persistencia.UsuariosAgesic;

public class MostrarUsuarios implements ExecutionListener{

	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		Query q=em.createNamedQuery("UsuariosAgesic.findAll");
		List<UsuariosAgesic> usuarios= q.getResultList();
		String usrs="";
		int i = 0;
		for (UsuariosAgesic usuariosAgesic : usuarios) {
			usrs+= ", "+i+"-"+usuariosAgesic.getId().getUsr();
			i++;
		}
		
		
		execution.setVariable("usuarios", usrs);
	}

}
