package web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;

import beans.PropuestaDT;
import constantes.Constantes;

@Named
@SessionScoped
public class ListarPropuestasController implements Serializable {
	private static final long serialVersionUID = -6931979465784683151L;
	private static final Logger logger = Logger.getLogger(ListarPropuestasController.class);

	private String user = "admin";
	private String password = "test";

	public List<PropuestaDT> getPropuestas() {
		List<PropuestaDT> propuestas = new ArrayList<PropuestaDT>();

		try {

			JSONObject body = new JSONObject()
					.put("processDefinitionKey", "process")
					.put("includeProcessVariables", "true");

			JSONObject jsonResponse = Unirest.post(Constantes.host+"/activiti-rest/service/query/process-instances")
					.basicAuth(user, password)
					.header("Content-Type", "application/json")
					.body(body)
					.asJson().getBody().getObject();

			JSONArray processList = jsonResponse.getJSONArray("data");
			for (int i = 0; i < processList.length(); i++) {
				JSONObject process = processList.getJSONObject(i);

				Map<String, String> variables = parseVariables(process.getJSONArray("variables"));

				if (variables.containsKey("propuesta_validada") && variables.get("propuesta_validada").equals("true")) {
					
					String fecha_prevista = variables.get("fecha_prevista_string");
					String bases_llamado = variables.get("bases_llamado");
					String numero = process.getString("id");
					
					jsonResponse = Unirest.get(Constantes.host+"/activiti-rest/service/runtime/tasks")
							.basicAuth(user, password)
							.queryString("processInstanceId", numero)
							.asJson().getBody().getObject();
					Boolean recibirSolicitud = jsonResponse.getJSONArray("data").getJSONObject(0).getString("name").equals("Recibir Solicitudes");
					
					PropuestaDT propuesta = new PropuestaDT();
					SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
					propuesta.setFecha_prevista_date(df.parse(fecha_prevista));
					propuesta.setIdentificacion(Long.valueOf(numero));
					propuesta.setBases_llamado(bases_llamado);
					propuesta.setRecibirSolicitud(recibirSolicitud);
					propuestas.add(propuesta);
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Ocurrio un error al obtener las convocatorias vigentes."));
			e.printStackTrace();
		}
		
		return propuestas;
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

	public static void main(String args[]) {
		(new ListarPropuestasController()).getPropuestas();
	}
}

