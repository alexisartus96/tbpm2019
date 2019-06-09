package controlador;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;

import persistencia.Proceso;
import persistencia.Solicitud;

public class EvaluarPropuesta implements ExecutionListener{

	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
//		Query q = em.createQuery("SELECT Solicitud FROM solicitud WHERE solicitud.id.ci='"+execution.getVariable("identificacion")+"'");
		Query q = em.createNamedQuery("Solicitud.ValidadasByProcesoId");
		q.setParameter("idProceso", execution.getVariable("identificacion"));		
		ArrayList<Solicitud> solicitudes= (ArrayList<Solicitud>) q.getResultList();
//		EntityTransaction et2= em.getTransaction();
//		et2.begin();
		
		
		ArrayList<String> list_titulares= new ArrayList<String>();
		ArrayList<String> list_suplentes= new ArrayList<String>();
		
		int montoSuma = 0;
		int montoAux = 0;
		int montoTotal;
		int alto;
		int medio;
		int bajo;
		Boolean montoAlcanzado=false;
		
		try {
			alto= Integer.parseInt(Long.toString((Long) execution.getVariable("alto")));
		} catch (Exception e) {
			alto= (Integer) execution.getVariable("alto");
		}
		try {
			medio= Integer.parseInt(Long.toString((Long) execution.getVariable("medio")));
		} catch (Exception e) {
			medio= (Integer) execution.getVariable("medio");
		}
		try {
			bajo= Integer.parseInt(Long.toString((Long) execution.getVariable("bajo")));
		} catch (Exception e) {
			bajo= (Integer) execution.getVariable("bajo");
		}
		try {
			montoTotal= Integer.parseInt(Long.toString((Long) execution.getVariable("monto_total")));
		} catch (Exception e) {
			montoTotal= (Integer) execution.getVariable("monto_total");
		}
		
		for (Solicitud solicitud : solicitudes) {
			if(solicitud.getRangoSubsidio().compareTo("Alto")==0) {
				montoAux= (int) (montoSuma + alto);
			}
			if(solicitud.getRangoSubsidio().compareTo("Medio")==0) {
				montoAux= (int) (montoSuma + medio);
			}
			if(solicitud.getRangoSubsidio().compareTo("Bajo")==0) {
				montoAux= (int) (montoSuma + bajo);
			}
			if(solicitud.getConfirmado()==null || solicitud.getConfirmado()) {
				if(montoAux <= montoTotal && !montoAlcanzado) {
					list_titulares.add(Long.toString(solicitud.getId().getCi()));
					montoSuma= montoAux;
				}else {
					list_suplentes.add(Long.toString(solicitud.getId().getCi()));
					montoAlcanzado=true;
				}
			}
		}
		boolean lista_completa=true;
		for (String string : list_titulares) {
			for (Solicitud solicitud : solicitudes) {
				if(Long.toString(solicitud.getId().getCi()).compareTo(string)==0){
					if(solicitud.getConfirmado()==null || !solicitud.getConfirmado()) {
						lista_completa= false;
					}
					break;
				}
			}
		}
		
		
		execution.setVariable("monto_solicitado", montoSuma);
		execution.setVariable("cantidad_solicitudes", solicitudes.size());
		execution.setVariable("cantidad_suplentes", list_suplentes.size());
		execution.setVariable("list_suplentes", list_suplentes);
		execution.setVariable("list_titulares", list_titulares);
		execution.setVariable("lista_completa", lista_completa);
		
		et.commit();
//		et2.commit();
		
		
	}

	

}
