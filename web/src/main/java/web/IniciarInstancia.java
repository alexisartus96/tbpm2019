package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;

import beans.PropuestaDT;
import constantes.Constantes;

@Named
@SessionScoped
public class IniciarInstancia implements Serializable {
	private static final Logger logger = Logger.getLogger(IniciarInstancia.class);

	private static final long serialVersionUID = -4732430916973701308L;

	private PropuestaDT datosPropuesta;

	@PostConstruct
	public void init() {
		logger.log(Level.INFO, "Init");
		datosPropuesta = new PropuestaDT();
	}

	public void actionConfirm(ActionEvent actionEvent) throws UnirestException {
		logger.log(Level.INFO, "Action confirm");
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			// Inicio la instancia del proceso
			JSONObject jsonResponse = Unirest.post(Constantes.host+"/activiti-rest/service/runtime/process-instances")
					.basicAuth(Constantes.user, Constantes.password)
					.header("Content-Type", "application/json")
					.header("accept", "application/json")
					.body(new JSONObject().put("processDefinitionKey", Constantes.processKey))
					.asJson().getBody().getObject();
			String processId = jsonResponse.getString("id");

			// Obtengo la taskId de la tarea
			jsonResponse = Unirest.get(Constantes.host+"/activiti-rest/service/runtime/tasks")
					.basicAuth(Constantes.user, Constantes.password)
					.header("Content-Type", "application/json")
					.header("accept", "application/json")
					.queryString("processInstanceId", processId)
					.asJson().getBody().getObject();
			String taskId = jsonResponse.getJSONArray("data").getJSONObject(0).getString("id");

			// Seteo a admin como responsable de la task
			JSONObject body = new JSONObject()
					.put("assignee", "admin");

			Unirest.put(Constantes.host+"/activiti-rest/service/runtime/tasks/{taskId}")
					.basicAuth(Constantes.user, Constantes.password)
					.routeParam("taskId", taskId)
					.header("Content-Type", "application/json")
					.body(body)
					.asJson();

			// Hago el submit del form
			Map<String, Object> props = new HashMap();
			props.put("identificacion", datosPropuesta.getIdentificacion());
			props.put("fecha_prevista", datosPropuesta.getFecha_prevista());
			props.put("bases_llamado", datosPropuesta.getBases_llamado());
			props.put("monto_total", datosPropuesta.getMonto_total());
			props.put("alto", datosPropuesta.getAlto());
			props.put("medio", datosPropuesta.getMedio());
			props.put("bajo", datosPropuesta.getBajo());
			props.put("descripcion", datosPropuesta.getDescripcion());
			props.put("persona_bps", datosPropuesta.getPersona_bps());
			props.put("mail", datosPropuesta.getMail());

			List<JSONObject> properties = new ArrayList();
			for (Map.Entry<String, Object> e : props.entrySet()) {
				String key = e.getKey();
				Object value = e.getValue();
				properties.add(new JSONObject()
						.put("id", key)
						.put("value", value));
			}

			body = new JSONObject()
					.put("taskId", taskId)
					.put("properties", properties);

			RequestBodyEntity cosa = Unirest.post(Constantes.host+"/activiti-rest/service/form/form-data")
					.basicAuth(Constantes.user, Constantes.password)
					.header("Content-Type", "application/json")
					.header("accept", "application/json")				
					.body(body);

		} catch (UnirestException e) {
			context.addMessage(null, new FacesMessage("Error", "Ocurrio un error al crear la propuesta."));
			throw e;
		}
		datosPropuesta = new PropuestaDT();
	}

	public PropuestaDT getDatosPropuesta() {
		return datosPropuesta;
	}

	public void setDatosMovilidad(PropuestaDT datosPropuesta) {
		this.datosPropuesta = datosPropuesta;
	}

}
