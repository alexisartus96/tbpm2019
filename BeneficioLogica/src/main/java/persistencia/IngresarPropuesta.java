package persistencia;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.joda.time.LocalDate;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class IngresarPropuesta implements TaskListener{
	private static EntityManager em;
	
	@Transactional
	public void notify(DelegateTask delegateTask) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		
		Map<String, Object> variables = delegateTask.getTransientVariables();
		Proceso proceso= new Proceso();
		
		try {
			proceso.setIdProceso((Long)variables.get("identificacion"));
		} catch (Exception e) {
			proceso.setIdProceso(((Integer) variables.get("identificacion")));
		}
		SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
		if(variables.get("fecha_prevista")!=null)
			try {
				proceso.setFechaPrevista(df.parse(((LocalDate)variables.get("fecha_prevista")).toString()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		proceso.setBasesLlamado((String)variables.get("bases_llamado"));
		try {
			proceso.setMontoTotal((Long) variables.get("monto_total"));
		}catch(Exception e) {
			proceso.setMontoTotal((Integer)variables.get("monto_total"));
		}
		
		try {
			if(variables.get("alto")!=null)proceso.setAlto((Long) variables.get("alto"));
		} catch (Exception e) {
			proceso.setAlto(((Integer) variables.get("alto")));
		}
		try {
			if(variables.get("medio")!=null)proceso.setMedio((Long) variables.get("medio"));
		} catch (Exception e) {
			proceso.setMedio(((Integer) variables.get("medio")));
		}
		try {
			if(variables.get("bajo")!=null)proceso.setBajo((Long) variables.get("bajo"));
		} catch (Exception e) {
			proceso.setBajo(((Integer) variables.get("bajo")));
		}
		if(variables.get("descripcion")!=null)proceso.setDescripcion((String) variables.get("descripcion"));
		if(variables.get("persona_bps")!=null)proceso.setContactoBps((String)variables.get("persona_bps"));
		if(variables.get("mail")!=null)proceso.setMailBps((String) variables.get("mail"));
		
		

		delegateTask.getExecution().setVariable("hay_solicitudes_invalidas", false);
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		em.merge(proceso);
		et.commit();
	}

}
