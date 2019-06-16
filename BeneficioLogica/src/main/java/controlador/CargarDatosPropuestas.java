package controlador;



import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.mashape.unirest.http.Unirest;

import persistencia.Proceso;
import persistencia.Solicitud;

@Transactional
public class CargarDatosPropuestas implements ExecutionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static EntityManager em;
	
	@SuppressWarnings("deprecation")
	@Transactional
	public void notify(DelegateExecution execution) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("EmployeePU");
		em = emf.createEntityManager();
		
		EntityTransaction et= em.getTransaction();
		et.begin();
		Query q = em.createNamedQuery("Proceso.findAll");
		@SuppressWarnings("unchecked")
		List<Proceso> procesos = (List<Proceso>) q.getResultList();
		int procesosTerminados = 0;
		Date fechaActual = new Date();
		for (Proceso proceso : procesos) {
			if (proceso.getFinalizado() != null && (proceso.getFechaPrevista().getYear() == fechaActual.getYear())) {
				procesosTerminados++;
			}
		}
		et.commit();
		JSONObject body = new JSONObject()
				.put("processDefinitionKey", "process")
				.put("includeProcessVariables", "true");
		Boolean existePropuesta = false;
		try {
			JSONObject jsonResponse = Unirest.post("http://localhost:8082/activiti-rest/service/query/process-instances")
					.basicAuth("admin", "test")
					.header("Content-Type", "application/json")
					.body(body)
				.asJson().getBody().getObject();
			JSONArray processList = jsonResponse.getJSONArray("data");
			for (int i = 0; i < processList.length(); i++) {
				JSONObject process = processList.getJSONObject(i);
				Map<String, String> variables = parseVariables(process.getJSONArray("variables"));
				if (variables.get("propuesta_aprobada") != null && variables.get("propuesta_aprobada").equals("true")) {
					existePropuesta = true;
					break;
				}		
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		execution.setVariable("existe_llamado_activo", existePropuesta);
		execution.setVariable("llamados_finalizados", procesosTerminados);
	}	
	
	/**
	 * Por el momento delvuelve en el mapa solo las variables del proceso de tipo string
	 * 
	 * @param variables
	 * @return
	 */
	private Map<String, String> parseVariables(JSONArray variables) {
		Map<String, String> varMap = new HashMap<String, String>();

		for (int i = 0; i < variables.length(); i++) {
			try {
				JSONObject var = variables.getJSONObject(i);
				if (var.getString("type").equals("string")) {
					String key = var.getString("name");
					String value = var.getString("value");
					varMap.put(key, value);
				}
			} catch (Exception e) {
				// logger.log(Level.WARN, e);
			}
		}

		return varMap;

	}
}
