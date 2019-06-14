package persistencia;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.joda.time.LocalDate;
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
		
		if(variables.get("identificacion") instanceof Long) {
			proceso.setIdProceso((Long)variables.get("identificacion"));
		}else if(variables.get("identificacion") instanceof Integer) {
			proceso.setIdProceso((Integer)variables.get("identificacion"));
		}else if(variables.get("identificacion") instanceof String) {
			proceso.setIdProceso(new Long((String)variables.get("identificacion")));
		}
		
//		try {
//			proceso.setIdProceso((Long)variables.get("identificacion"));
//		} catch (Exception e) {
//			proceso.setIdProceso(((Integer) variables.get("identificacion")));
//		}
		SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
		if(variables.get("fecha_prevista")!=null)
			try {
				if(variables.get("fecha_prevista") instanceof LocalDate) {
					proceso.setFechaPrevista(df.parse(((LocalDate)variables.get("fecha_prevista")).toString()));
					delegateTask.getExecution().setVariable("fecha_prevista_string", ((LocalDate)variables.get("fecha_prevista")).toString());
				}else if(variables.get("fecha_prevista") instanceof String) {
					proceso.setFechaPrevista(df.parse((String) variables.get("fecha_prevista")));
					delegateTask.getExecution().setVariable("fecha_prevista_string", ((String)variables.get("fecha_prevista")));
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		proceso.setBasesLlamado((String)variables.get("bases_llamado"));
		try {
			if(variables.get("monto_total") instanceof Long) {
				proceso.setMontoTotal((Long) variables.get("monto_total"));
			}else if (variables.get("monto_total") instanceof Integer) {
				proceso.setMontoTotal((Integer)variables.get("monto_total"));
			}else if(variables.get("monto_total") instanceof String) {
				proceso.setMontoTotal(new Long((String)variables.get("monto_total")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(variables.get("alto") instanceof Long) {
				proceso.setAlto((Long) variables.get("alto"));
			}else if (variables.get("alto") instanceof Integer) {
				proceso.setAlto((Integer)variables.get("alto"));
			}else if(variables.get("alto") instanceof String) {
				proceso.setAlto(new Long((String)variables.get("alto")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(variables.get("medio") instanceof Long) {
				proceso.setMedio((Long) variables.get("medio"));
			}else if (variables.get("medio") instanceof Integer) {
				proceso.setMedio((Integer)variables.get("medio"));
			}else if(variables.get("medio") instanceof String) {
				proceso.setMedio(new Long((String)variables.get("medio")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(variables.get("bajo") instanceof Long) {
				proceso.setBajo((Long) variables.get("bajo"));
			}else if (variables.get("bajo") instanceof Integer) {
				proceso.setBajo((Integer)variables.get("bajo"));
			}else if(variables.get("bajo") instanceof String) {
				proceso.setBajo(new Long((String)variables.get("bajo")));
			}
		}catch(Exception e) {
			e.printStackTrace();
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
