package controlador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import persistencia.Solicitud;

public class MostrarSolicitudFlecha implements ExecutionListener{

	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		Query q=em.createNamedQuery("Solicitud.byId");
		
		
		q.setParameter("ci", new Long((String) execution.getVariable("solicitud")));
		q.setParameter("idProceso", execution.getVariable("identificacion"));
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		
		Solicitud solicitud=(Solicitud) q.getSingleResult();
		
		et.commit();	
		
		
		execution.setVariable("apellido", solicitud.getNombre());
		execution.setVariable("nombre", solicitud.getApellido());
		execution.setVariable("informacion_familiar", solicitud.getDatosFamiliares());
		execution.setVariable("ci", solicitud.getId().getCi());
		execution.setVariable("nivel_educativo", solicitud.getNivelEducativo());
		execution.setVariable("ingresos", solicitud.getIngresos());
		execution.setVariable("integrantes_familia", solicitud.getIntegrantesFamilia());
		execution.setVariable("otros_ingresos", solicitud.getOtrosIngresos());
		execution.setVariable("mail_solicitante", solicitud.getMailSolicitante());
		execution.setVariable("rango_subsidio", solicitud.getRangoSubsidio());
	}

}
