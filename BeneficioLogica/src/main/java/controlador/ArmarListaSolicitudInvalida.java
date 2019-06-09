package controlador;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import persistencia.Solicitud;

public class ArmarListaSolicitudInvalida implements ExecutionListener{
	private static EntityManager em;

	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
//		Query q = em.createQuery("SELECT Solicitud FROM solicitud WHERE solicitud.id.ci='"+execution.getVariable("identificacion")+"'");
		Query q = em.createNamedQuery("Solicitud.ByProcesoIdOrderPrioridad");
		q.setParameter("idProceso", execution.getVariable("identificacion"));
		ArrayList<Solicitud> solicitudes= (ArrayList<Solicitud>) q.getResultList();
		
		ArrayList<String> list_solicitudes_invalidas=new ArrayList<String>();
		ArrayList<String> list_solicitudes= new ArrayList<String>();
		
		Boolean hay_solicitudes_invalidas=false;
		
		for (Solicitud solicitud : solicitudes) {
			if(solicitud.getValidado()) {
				list_solicitudes.add(Long.toString(solicitud.getId().getCi()));
			}else {
				list_solicitudes_invalidas.add(Long.toString(solicitud.getId().getCi()));
				hay_solicitudes_invalidas=true;
			}
		}
		
		et.commit();

		execution.setVariable("list_solicitudes_invalidas", list_solicitudes_invalidas);
		execution.setVariable("list_solicitudes", list_solicitudes);
		execution.setVariable("hay_solicitudes_invalidas", hay_solicitudes_invalidas);
		// TODO Auto-generated method stub
	}
}
