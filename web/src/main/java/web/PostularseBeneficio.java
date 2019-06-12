package web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//import alfrescoConection.ConectorAlfresco;
//import alfrescoConection.FileUploader;
import web.beans.DatosPostulacionBean;

@Named
@SessionScoped
public class PostularseBeneficio implements Serializable {
	private static final long serialVersionUID = -9118252853052412615L;

	private String user = "admin";
	private String password = "test";
	private String processInstanceId;

	private DatosPostulacionBean datosPostulacion;


	@PostConstruct
	public void init() {
		datosPostulacion = new DatosPostulacionBean();
	}

	public void redirect(String processId) throws IOException {
		processInstanceId = processId;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		FacesContext.getCurrentInstance().getExternalContext().redirect(servletContext.getContextPath() + "/view/postularse-convocatoria.xhtml");
//		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//		externalContext.redirect(externalContext.getApplicationContextPath() + "/view/postularse-convocatoria.xhtml");

	}

	public void handleFileUpload() {
		System.out.println("test");
	}

	public void actionConfirm() throws IOException {
		try {
			UploadedFile uploadedfile = datosPostulacion.getCv();
			String fileName = uploadedfile.getFileName();
			String prefix = fileName.split("\\.")[0];
			String suffix = fileName.split("\\.")[1];
			String mimeType = uploadedfile.getContentType();
//			File file = FileUploader.stream2file(datosPostulacion.getCv().getInputstream(), prefix, suffix);
//			String url = ConectorAlfresco.getInstance().subirArchivo(file, fileName, mimeType);

			// Obtengo la task id
			JSONObject jsonResponse = Unirest.get("http://localhost:8080/activiti-rest/service/runtime/tasks")
					.basicAuth(user, password)
					.queryString("processInstanceId", processInstanceId)
					.asJson().getBody().getObject();

			String taskId = jsonResponse.getJSONArray("data").getJSONObject(0).getString("id");

			// Hago el submit del form
			Map<String, Object> props = new HashMap();
			props.put("t4_nombrePostulante", datosPostulacion.getNombre());
			props.put("t4_apellidoPostulante", datosPostulacion.getApellido());
			props.put("t4_ciPostulante", datosPostulacion.getCi());
			props.put("t4_carreraPostulante", datosPostulacion.getCarrera());
			props.put("t4_cantCreditosCarreraPostulante", datosPostulacion.getCantCreditos());
			props.put("t4_universidadDestinoPostulante", datosPostulacion.getUniversidad());
//			props.put("urlCV", url);

			List<JSONObject> properties = new ArrayList();
			for (Map.Entry<String, Object> e : props.entrySet()) {
				String key = e.getKey();
				Object value = e.getValue();
				properties.add(new JSONObject()
						.put("id", key)
						.put("value", value));
			}

			JSONObject body = new JSONObject()
					.put("taskId", taskId)
					.put("properties", properties);


			Unirest.post("http://localhost:8080/activiti-rest/service/form/form-data")
					.basicAuth("admin", "test")
					.header("Content-Type", "application/json")
					.header("accept", "application/json")
					.body(body)
					.asJson();


			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Exito", "La postulación se ha registrado con exito."));
			datosPostulacion = new DatosPostulacionBean();
		} catch (UnirestException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocurrio un error al postularse"));
			e.printStackTrace();
		}
	}

	public DatosPostulacionBean getDatosPostulacion() {
		return datosPostulacion;
	}

	public void setDatosPostulacion(DatosPostulacionBean datosPostulacion) {
		this.datosPostulacion = datosPostulacion;
	}
}
