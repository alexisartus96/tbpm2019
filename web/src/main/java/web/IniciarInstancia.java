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

import beans.DatosMovilidadBean;

@Named
@SessionScoped
public class IniciarInstancia implements Serializable {
	private static final Logger logger = Logger.getLogger(IniciarInstancia.class);

	private static final long serialVersionUID = -4732430916973701308L;

	private DatosMovilidadBean datosMovilidad;
	String processKey = "adjudicacionMovilidadAcademica";
	String user = "admin";
	String password = "test";

	@PostConstruct
	public void init() {
		logger.log(Level.INFO, "Init");
		datosMovilidad = new DatosMovilidadBean();
	}

	public void actionConfirm(ActionEvent actionEvent) throws UnirestException {
		logger.log(Level.INFO, "Action confirm");
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			// Inicio la instancia del proceso
			JSONObject jsonResponse = Unirest.post("http://localhost:8080/activiti-rest/service/runtime/process-instances")
					.basicAuth(user, password)
					.header("Content-Type", "application/json")
					.header("accept", "application/json")
					.body(new JSONObject().put("processDefinitionKey", processKey))
					.asJson().getBody().getObject();
			String processId = jsonResponse.getString("id");

			// Obtengo la taskId de la tarea "Ingreso llamado de movilidad"
			jsonResponse = Unirest.get("http://localhost:8080/activiti-rest/service/runtime/tasks")
					.basicAuth(user, password)
					.header("Content-Type", "application/json")
					.header("accept", "application/json")
					.queryString("processInstanceId", processId)
					.asJson().getBody().getObject();
			String taskId = jsonResponse.getJSONArray("data").getJSONObject(0).getString("id");

			// Seteo a dgrc como responsable de la task
			JSONObject body = new JSONObject()
					.put("assignee", "dgrc");

			Unirest.put("http://localhost:8080/activiti-rest/service/runtime/tasks/{taskId}")
					.basicAuth(user, password)
					.routeParam("taskId", taskId)
					.header("Content-Type", "application/json")
					.body(body)
					.asJson();

			// Hago el submit del form
			Map<String, Object> props = new HashMap();
			props.put("movilidad", datosMovilidad.getMovilidad());
			props.put("convocatoria", datosMovilidad.getConvocatoria());
			props.put("fechaPrevistaInicioPostulaciones", datosMovilidad.getFechaPostulaciones());
			props.put("carrerasInvolucradas", datosMovilidad.getCarrerasInvolucradas());
			props.put("duracionPrevista", datosMovilidad.getDuracionPrevista());
			props.put("basesMovilidad", datosMovilidad.getBases());
			props.put("descripcionMovilidad", datosMovilidad.getDescripcion());
			props.put("nombreContactoDGRC", datosMovilidad.getNombreDGRC());
			props.put("mailContactoDGRC", datosMovilidad.getMailDGRC());

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

			logger.log(Level.INFO, body);

			Unirest.post("http://localhost:8080/activiti-rest/service/form/form-data")
					.basicAuth("admin", "test")
					.header("Content-Type", "application/json")
					.header("accept", "application/json")
					.body(body)
					.asJson();

			logger.log(Level.INFO, jsonResponse);

			context.addMessage(null, new FacesMessage("Exito", "La movilidad " + datosMovilidad.getMovilidad() + " se ha creado corectamente."));

		} catch (UnirestException e) {
			logger.log(Level.ERROR, "Error al iniciar instancia enviando post a api-rest", e);
			context.addMessage(null, new FacesMessage("Error", "Ocurrio un error al crear la movilidad."));
			throw e;
		}
		// Como estoy usando sessionScope, reseteo manualmente el bean de datos movilidad, para que no aparezcan nuevamente los datos
		// al entrar por segunda vez a la pagina.
		datosMovilidad = new DatosMovilidadBean();
	}

	public DatosMovilidadBean getDatosMovilidad() {
		return datosMovilidad;
	}

	public void setDatosMovilidad(DatosMovilidadBean datosMovilidad) {
		this.datosMovilidad = datosMovilidad;
	}

}
