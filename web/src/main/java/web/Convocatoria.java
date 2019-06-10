package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import beans.ActividadBean;
import constantes.Constantes;

@Named
@SessionScoped
public class Convocatoria implements Serializable {
	private static final Logger logger = Logger.getLogger(Convocatoria.class);

	private String user = "admin";
	private String password = "test";
	private String processInstanceId;
	List<ActividadBean> actividadesList = null;

	public void loadActividades() {
		actividadesList = new ArrayList<ActividadBean>();

		if (processInstanceId == null) {
			return;
		}
		
		try {
			JSONObject jsonResponse = Unirest.get(Constantes.host+"/activiti-rest/service/history/historic-task-instances")
					.basicAuth(user, password)
					.queryString("processInstanceId", processInstanceId)
					.asJson().getBody().getObject();

			JSONArray actividades = jsonResponse.getJSONArray("data");

			for (int i = 0; i < actividades.length(); i++) {
				JSONObject actividad = actividades.getJSONObject(i);
				ActividadBean actividadBean = new ActividadBean();

				actividadBean.setNombre(actividad.get("name"));
				actividadBean.setFechaComienzo(actividad.get("startTime"));
				actividadBean.setFechaFin(actividad.get("endTime"));
				actividadBean.setResponsable(actividad.get("assignee"));

				if (actividadBean.getFechaFin().equals(JSONObject.NULL)) {
					actividadBean.setFechaFin("En curso");
				}
				if (actividadBean.getResponsable().equals(JSONObject.NULL)) {
					actividadBean.setResponsable("La actividad aún no ha sido tomada.");
				}

				actividadesList.add(actividadBean);
			}

		} catch (UnirestException e) {
			logger.log(Level.ERROR, "Error al obtener actividades de la convocatoria", e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Ocurrio un error al crear la movilidad."));
			e.printStackTrace();
		}
		if (actividadesList.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Error", "No se encontro ninguna convocatoria vigente con el número ." + processInstanceId));
		}
		
	}


	public List<ActividadBean> getActividadesList() {
		return actividadesList;
	}

	public void setActividadesList(List<ActividadBean> actividadesList) {
		this.actividadesList = actividadesList;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

}
