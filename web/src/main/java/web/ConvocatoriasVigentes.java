package web;

import java.io.Serializable;
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

import beans.ConvocatoriaBean;
import constantes.Constantes;

@Named
@SessionScoped
public class ConvocatoriasVigentes implements Serializable {
	private static final long serialVersionUID = -6931979465784683151L;
	private static final Logger logger = Logger.getLogger(ConvocatoriasVigentes.class);

	private String user = "admin";
	private String password = "test";

	public List<ConvocatoriaBean> getConvocatorias() {
		List<ConvocatoriaBean> convocatorias = new ArrayList<ConvocatoriaBean>();

		try {

			// Obtengo las instancias del proceso adjudicacionMovilidadAcademica
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

				// Si la movilidad esta aprobada la agrego a la lista de movilidades que voy a mostrar
				if (variables.containsKey("rn_aprobacionCronograma") && variables.get("rn_aprobacionCronograma").equals("Si")) {
					String movilidad = variables.get("movilidad");
					String convocatoria = variables.get("convocatoria");
					String numero = process.getString("id");
					
					// Me fijo si la movilidad se encuentra recibiendo postulaciones
					jsonResponse = Unirest.get(Constantes.host+"/activiti-rest/service/runtime/tasks")
							.basicAuth(user, password)
							.queryString("processInstanceId", numero)
							.asJson().getBody().getObject();
					Boolean recibirPostulaciones = jsonResponse.getJSONArray("data").getJSONObject(0).getString("name").equals("Recibir postulaciones");

					ConvocatoriaBean convocatoriaBean = new ConvocatoriaBean();
					convocatoriaBean.setMovilidad(movilidad);
					convocatoriaBean.setConvocatoria(convocatoria);
					convocatoriaBean.setNumero(numero);
					convocatoriaBean.setRecibirPostulaciones(recibirPostulaciones);
					convocatorias.add(convocatoriaBean);
				}

			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Ocurrio un error al obtener las convocatorias vigentes."));
			e.printStackTrace();
		}

		return convocatorias;
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
		(new ConvocatoriasVigentes()).getConvocatorias();
	}
}

