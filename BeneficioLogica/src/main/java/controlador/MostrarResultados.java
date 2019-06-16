package controlador;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import persistencia.Solicitud;

public class MostrarResultados implements TaskListener{

	public void notify(DelegateTask delegateTask) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		Query q = em.createNamedQuery("Solicitud.ConfirmadasByProcesoId");
		DelegateExecution execution= delegateTask.getExecution();
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
		ArrayList<Solicitud> solicitudes= (ArrayList<Solicitud>) q.getResultList();
		String resultados= "";
		for (Solicitud solicitud : solicitudes) {
			resultados+="Solicitante: "+ solicitud.getNombre()+solicitud.getApellido()+
						" Rango de subsidio: "+ solicitud.getRangoSubsidio() + ", ";
		}
		
		execution.setVariable("resultados", resultados);
		if(resultados.compareTo("")==0) execution.setVariable("resultados", "No se asignaron beneficios");
	}

}
