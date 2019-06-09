package controlador;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import persistencia.Solicitud;

public class CorregirLista implements ExecutionListener{

	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		Query q = em.createNamedQuery("Solicitud.ByProcesoIdOrderPrioridad");
		q.setParameter("idProceso", execution.getVariable("identificacion"));
		List<Solicitud> solicitudes= (List<Solicitud>) q.getResultList();
		et.commit();
		
		List list_validadas= new ArrayList();
		List list_invalidadas= new ArrayList();
		
		for (Solicitud solicitud : solicitudes) {
			if(solicitud.getValidado()) {
				list_validadas.add(solicitud.getId().getCi());
			}else {
				list_invalidadas.add(solicitud.getId().getCi());
			}
		}

		execution.setVariable("list_solicitudes", list_validadas);
		execution.setVariable("list_solicitudes_invalidas", list_invalidadas);
		//SI esta validado la saco de invalidas, si no de validas
//		if(solicitud.getValidado()) {
//			//Si no esta en la lista de validas la agrego
//			if(!((ArrayList) execution.getVariable("list_solicitudes")).contains(solicitud.getId().getCi())) {
//				execution.setVariable("list_solicitudes",((ArrayList)execution.getVariable("list_solicitudes")).add(solicitud.getId().getCi()));
//			}
//			//Si esta en las invalidas la saco
//			if(((ArrayList) execution.getVariable("list_solicitudes_invalidas")).contains(solicitud.getId().getCi())) {
//				execution.setVariable("list_solicitudes_invalidas", ((ArrayList)execution.getVariable("list_solicitudes_invalidas")).remove(solicitud.getId().getCi()));
//			}
//		}else {
//			//Si no esta en la lista de validas la agrego
//			if(!((ArrayList) execution.getVariable("list_solicitudes_invalidas")).contains(solicitud.getId().getCi())) {
//				execution.setVariable("list_solicitudes_invalidas",((ArrayList)execution.getVariable("list_solicitudes_invalidas")).add(solicitud.getId().getCi()));
//			}
//			//Si esta en las invalidas la saco
//			if(((ArrayList) execution.getVariable("list_solicitudes")).contains(solicitud.getId().getCi())) {
//				execution.setVariable("list_solicitudes", ((ArrayList)execution.getVariable("list_solicitudes")).remove(solicitud.getId().getCi()));
//			}
//		}
		
	}

}
