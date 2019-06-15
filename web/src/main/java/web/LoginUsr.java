package web;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.UsuariosBpsWeb;


public class LoginUsr {
	
	public LoginUsr() {
		
	}

	public Boolean loguin(String usr, String pasw) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		Query q= em.createNamedQuery("Usuario.login");
		q.setParameter("usuario", usr);
		q.setParameter("contraseña", pasw);
		List<UsuariosBpsWeb> usuarios= q.getResultList();
		et.commit();
		return (usuarios.size()>0);
	}
}
