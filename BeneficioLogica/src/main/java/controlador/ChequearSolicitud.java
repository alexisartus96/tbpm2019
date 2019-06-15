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

public class ChequearSolicitud implements TaskListener{

	private static EntityManager em;

	public void notify(DelegateTask delegateTask) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
//		Query q = em.createQuery("SELECT Solicitud FROM solicitud WHERE solicitud.id.ci='"+execution.getVariable("identificacion")+"'");
		Query q = em.createNamedQuery("Solicitud.byId");
		q.setParameter("ci", delegateTask.getVariable("solicitud"));
		try {
			if(delegateTask.getVariable("identificacion") instanceof Long) {
				q.setParameter("idProceso",(Long)delegateTask.getVariable("identificacion"));
			}else if(delegateTask.getVariable("identificacion") instanceof Integer) {
				q.setParameter("idProceso",(Integer)delegateTask.getVariable("identificacion"));
			}else if(delegateTask.getVariable("identificacion") instanceof String) {
				q.setParameter("idProceso",new Long((String)delegateTask.getVariable("identificacion")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		Solicitud solicitud= (Solicitud) q.getSingleResult();
		
//		ArrayList<Solicitud> solicitudes= (ArrayList<Solicitud>) q.getResultList();
		ArrayList list_solicitudes_invalidas= (ArrayList) delegateTask.getVariable("list_solicitudes_invalidas");
		Boolean hay_solicitudes_invalidas= (Boolean) delegateTask.getVariable("hay_solicitudes_invalidas");
		if(list_solicitudes_invalidas==null) {
			hay_solicitudes_invalidas= true;
			list_solicitudes_invalidas=new ArrayList();
		}
		if((Boolean) delegateTask.getVariable("solicitud_valida")==false) {
			list_solicitudes_invalidas.add(solicitud.getId().getCi());
			solicitud.setValidado(false);
		}else {
			solicitud.setValidado(true);
		}
		em.merge(solicitud);
//		q= em.createNamedQuery("Solicitud.byProcesoId");
//		q.setParameter("idProceso", delegateTask.getVariable("identificacion"));
//		ArrayList<Solicitud> solicitudes= (ArrayList<Solicitud>) q.getResultList();
//		
//		for (Solicitud solicitud2 : solicitudes) {
//			if(!solicitud2.getValidado()) {
//				list_solicitudes_invalidas.add(solicitud2.getId().getCi());
//			}
//		}
		
		et.commit();
		
		delegateTask.getExecution().setVariable("list_solicitudes_invalidas", list_solicitudes_invalidas);
		delegateTask.getExecution().setVariable("hay_solicitudes_invalidas", hay_solicitudes_invalidas);
	}

}
