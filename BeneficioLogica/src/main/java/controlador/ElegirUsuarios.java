package controlador;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import persistencia.UsuariosAgesic;

public class ElegirUsuarios implements ExecutionListener{

	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		Query q=em.createNamedQuery("UsuariosAgesic.findAll");
		List<UsuariosAgesic> usuarios= q.getResultList();
		String usrs="";
		int i = 0;
		

		String integrante1="";
		String integrante2= "";
		String integrante3= "";
		
		
		if(execution.getVariable("integrante1") instanceof Long) {
			integrante1= ((Long) execution.getVariable("integrante1")).toString();
		}else if(execution.getVariable("integrante1") instanceof Integer) {
			integrante1= ((Integer) execution.getVariable("integrante1")).toString();
		}else if(execution.getVariable("integrante1") instanceof String) {
			integrante1= (String) execution.getVariable("integrante1");
		}
		
		if(execution.getVariable("integrante2") instanceof Long) {
			integrante2= ((Long) execution.getVariable("integrante2")).toString();
		}else if(execution.getVariable("integrante2") instanceof Integer) {
			integrante2= ((Integer) execution.getVariable("integrante2")).toString();
		}else if(execution.getVariable("integrante2") instanceof String) {
			integrante2= (String) execution.getVariable("integrante2");
		}
		
		if(execution.getVariable("integrante3") instanceof Long) {
			integrante3= ((Long) execution.getVariable("integrante3")).toString();
		}else if(execution.getVariable("integrante3") instanceof Integer) {
			integrante3= ((Integer) execution.getVariable("integrante3")).toString();
		}else if(execution.getVariable("integrante3") instanceof String) {
			integrante3= (String) execution.getVariable("integrante3");
		}
		for (UsuariosAgesic usuariosAgesic : usuarios) {
			if(Integer.toString(i).compareTo(integrante1)==0) execution.setVariable("integrante1", usuariosAgesic.getId().getUsr());
			if(Integer.toString(i).compareTo(integrante2)==0) execution.setVariable("integrante2", usuariosAgesic.getId().getUsr());
			if(Integer.toString(i).compareTo(integrante3)==0) execution.setVariable("integrante3", usuariosAgesic.getId().getUsr());
			i++;
		}
		
		
		execution.setVariable("usuarios", usrs);
	}

}
