package web;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.json.JSONObject;
import org.primefaces.model.UploadedFile;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import alfrescoConection.ConectorAlfresco;
import alfrescoConection.FileUploader;
import beans.SolicitudDT;
import constantes.Constantes;

@Named
@SessionScoped
@ManagedBean
public class SolicitarBeneficio implements Serializable {
	private static final long serialVersionUID = -9118252853052412615L;

	private String user = "admin";
	private String password = "test";
	private String processInstanceId;

	private SolicitudDT datosSolicitud;


	@PostConstruct
	public void init() {
		setDatosSolicitud(new SolicitudDT());
	}

	public void redirect(String processId) throws IOException {
		processInstanceId = processId;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		FacesContext.getCurrentInstance().getExternalContext().redirect(servletContext.getContextPath() + "/view/solicitarBeneficio.xhtml");
	}

	public void handleFileUpload() {
		System.out.println("test");
	}

	public void actionConfirm() throws IOException {
		try {
			UploadedFile uploadedfile = datosSolicitud.getFile();
			String fileName = uploadedfile.getFileName();
			String prefix = fileName.split("\\.")[0];
			String suffix = fileName.split("\\.")[1];
			String mimeType = uploadedfile.getContentType();
			File file = FileUploader.stream2file(uploadedfile.getInputstream(), prefix, suffix);
			String url = ConectorAlfresco.getInstance().subirArchivo(file, fileName, mimeType);

			// Obtengo la task id
			JSONObject jsonResponse = Unirest.get(Constantes.host+"/activiti-rest/service/runtime/tasks")
					.basicAuth(Constantes.user, Constantes.password)
					.queryString("processInstanceId", processInstanceId)
					.asJson().getBody().getObject();

			String taskId = jsonResponse.getJSONArray("data").getJSONObject(0).getString("id");

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
			props.put("ci", datosSolicitud.getIdentificacion());
			props.put("apellido", datosSolicitud.getApellido());
			props.put("nombre", datosSolicitud.getNombre());
			props.put("informacion_familiar", datosSolicitud.getInformacion_familiar());
			props.put("ingresos", datosSolicitud.getIngresos());
			props.put("mail_solicitante", datosSolicitud.getMail_solicitante());
			props.put("nivel_educativo", datosSolicitud.getNivel_educativo());
			props.put("integrantes_familia", datosSolicitud.getIntegrantes_familia());
			props.put("otros_ingresos", datosSolicitud.getOtros_ingresos());
			props.put("url_datos_solicitante", "127.0.0.1:8080/share/proxy/alfresco/slingshot/node/content/workspace/SpacesStore/"+
						Constantes.alfrescofolderID + fileName);

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

			Unirest.post("http://localhost:8082/activiti-rest/service/form/form-data")
					.basicAuth("admin", "test")
					.header("Content-Type", "application/json")
					.header("accept", "application/json")
					.body(body)
					.asJson();


			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Exito", "La postulación se ha registrado con exito."));
			datosSolicitud = new SolicitudDT();
		} catch (UnirestException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocurrio un error al postularse"));
			e.printStackTrace();
		}
	}

	public SolicitudDT getDatosSolicitud() {
		return datosSolicitud;
	}

	public void setDatosSolicitud(SolicitudDT datosSolicitud) {
		this.datosSolicitud = datosSolicitud;
	}
}
