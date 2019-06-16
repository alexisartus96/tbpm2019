package alfrescoConection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class FileUploader{
	private static final Logger logger = Logger.getLogger(FileUploader.class);
	private static final long serialVersionUID = 1L;
	static String user = "admin";
	static String password = "test";

	public static String cargaArchivo(DelegateExecution execution, String taskName) {
		try {
			String processInstanceId = execution.getProcessInstanceId();
			
			
			JSONObject jsonResponseTaskID = Unirest.get("http://localhost:8080/activiti-rest/service/history/historic-task-instances")
					.basicAuth(user, password)
					.queryString("processInstanceId", processInstanceId)
					.queryString("taskName", taskName)
					.asJson().getBody().getObject();

			JSONArray data = jsonResponseTaskID.getJSONArray("data");
			String taskId = data.getJSONObject(data.length()-1).getString("id");
			// Obtengo la cookie de la sesión
			HttpResponse<InputStream> response = Unirest.post("http://localhost:8080/activiti-app/app/authentication")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.body("j_username=" + user + "&j_password=" + password + "&_spring_security_remember_me=true&submit=Login")
					.asBinary();

			String coockie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
			System.out.println(coockie);

			// Obtengo atachmentId del archivo que se adjunto
			JSONObject jsonResponse = Unirest.get("http://localhost:8080/activiti-app/app/rest/tasks/{taskId}/content")
					.routeParam("taskId", taskId)
					.header("cookie", coockie)
					.asJson().getBody().getObject();

			JSONObject attachmentData = null;
			try {
				attachmentData = jsonResponse.getJSONArray("data").getJSONObject(0);
			} catch (JSONException e) {
				logger.log(Level.INFO, "No se adjunto ningun archivo.");
				throw new InvalidParameterException("No se adjunto ningun archivo.");
			}

			Integer attachmentId = attachmentData.getInt("id");
			String attachmentName = processInstanceId +"_"+ taskId +"_"+ attachmentId + "_" + attachmentData.getString("name");
			String mimeType = attachmentData.getString("mimeType");

			
			// Obtengo el archivo
			response = Unirest.get("http://localhost:8080/activiti-app/app/rest/content/{attachmentId}/raw")
					.routeParam("attachmentId", attachmentId.toString())
					.header("cookie", coockie)
					.asBinary();

			String prefix = attachmentName.split("\\.")[0];
			String suffix = attachmentName.split("\\.")[1];
			File attachmentFile = stream2file(response.getBody(), prefix, "." + suffix);
			

			ConectorAlfresco alfresco = ConectorAlfresco.getInstance();
			String respSubirArchivo = alfresco.subirArchivo(attachmentFile, attachmentName, mimeType);
			
			return respSubirArchivo;
//		     execution.setVariable(""+"XX", attachmentName);
//		     execution.setVariable("UrlArchivo"+"_"+ "XX", respSubirArchivo);
		} catch (IOException | UnirestException e) {
			logger.log(Level.INFO, "No se adjunto ningun archivo.");
			throw new InvalidParameterException("Se produjo un error al subir el archivo.");
		}

	}


public static File stream2file(InputStream in, String prefix, String suffix) throws IOException {
	final File tempFile = File.createTempFile(prefix, suffix);
	tempFile.deleteOnExit();
	try (FileOutputStream out = new FileOutputStream(tempFile)) {
		IOUtils.copy(in, out);
	}
	return tempFile;
}

}
