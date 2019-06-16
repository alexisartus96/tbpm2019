package alfrescoConection;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import properties.PropertiesMannager;

public class ConectorAlfresco {
	private static ConectorAlfresco instance = null;
	private HttpURLConnection connection = null;
	private String user = "admin";
	private String password = "admin";
	private static final Logger logger = Logger.getLogger(ConectorAlfresco.class);
	private static String folderID; // CORRESPONDE AL ID DE LA CARPETA EN ALFRESCO
	private static String ipAlfresco; // CORRESPONDE A LA IP QUE SE ENCUENTRA ALFRESCO

	public static ConectorAlfresco getInstance() throws IOException {
		if (instance == null) {
			instance = new ConectorAlfresco();
		}
		return instance;
	}

	private ConectorAlfresco() throws IOException {
		Properties prop = PropertiesMannager.getProperties();
		ipAlfresco = prop.getProperty("alfresco.url");
		folderID = prop.getProperty("alfresco.folderID");
	}

	public String subirArchivo(File f, String name, String mimeType) throws IOException {

		// Se obtiene ticket de autenticación de login
		String alfrescoTiccketURL = "http://" + ipAlfresco + "/alfresco/service/api/login?u=" + user + "&pw="
				+ password;
		InteractiveAuthentication ticket = new InteractiveAuthentication();
		String ticketURLResponse = ticket.getTicket(alfrescoTiccketURL);

		// Se sube el archivo a alfresco y se obtiene el mensaje de respuesta.
		String uploadResponse = uploadDocument(ticketURLResponse, f, name, mimeType, "", null);

		// Se parsea la respuesta de alfresco para generar la URL de acceso
		String urlArchivo = "http://" + ipAlfresco + "/share/page/document-details?nodeRef="
				+ obtenerUrlFromBody(uploadResponse);

		logger.log(Level.INFO, urlArchivo);
		System.out.println(urlArchivo);
		return urlArchivo;

	}

	public static String uploadDocument(String authTicket, File fileobj, String filename, String filetype,
			String description, String destination) {
		try {

			String urlString = "http://" + ipAlfresco + "/alfresco/service/api/upload?alf_ticket=" + authTicket;
			System.out.println("The upload url:::" + urlString);
			HttpClient client = new HttpClient();

			PostMethod mPost = new PostMethod(urlString);
			// File f1 =fileobj;
			Part[] parts = { new FilePart("filedata", filename, fileobj, filetype, null),
					new StringPart("filename", filename), new StringPart("description", description),

					// modify this according to where you wanna put your content
					new StringPart("destination", "workspace://SpacesStore/" + folderID)
					// new StringPart("uploaddirectory", "/Company Home")
			};
			mPost.setRequestEntity(new MultipartRequestEntity(parts, mPost.getParams()));
			int statusCode1 = client.executeMethod(mPost);

			System.out.println("statusLine>>>" + statusCode1 + "......" + "\n status line \n" + mPost.getStatusLine()
					+ "\nbody \n" + mPost.getResponseBodyAsString());

			mPost.releaseConnection();
			return mPost.getResponseBodyAsString();

		} catch (Exception e) {
			System.out.println(e);
			return e.getMessage();
		}
	}

	private String obtenerUrlFromBody(String body) {
		int startTag = body.indexOf("workspace://SpacesStore/");
		int endTag = body.indexOf("fileName");
		return body.substring(startTag, endTag - 8);
	}

}