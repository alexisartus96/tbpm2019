package alfrescoConection;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.httpclient.methods.GetMethod;

public class InteractiveAuthentication {

	public InteractiveAuthentication() {
		super();
	}

	public String getTicket(String url) throws IOException {

		HttpClient client = new HttpClient();
		client.getParams().setParameter(CredentialsProvider.PROVIDER, new ConsoleAuthPrompter());
		GetMethod httpget = new GetMethod(url);
		httpget.setDoAuthentication(true);

		// aritz
		String response = null;
		String ticketURLResponse = null;

		try {
			// execute the GET
			int status = client.executeMethod(httpget);

			// aritz
			if (status != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + httpget.getStatusLine());
			}

			// print the status and response
			// System.out.println(httpget.getStatusLine().toString());
			// System.out.println(httpget.getResponseBodyAsString());

			response = new String(httpget.getResponseBodyAsString());
			System.out.println("response = " + response);

			int startTag = response.indexOf("<ticket>");
			int endTag = response.indexOf("</ticket>");
			ticketURLResponse = response.substring(startTag + ("<ticket>".length()), endTag);

			System.out.println("ticket = " + ticketURLResponse);

		} finally {

			// release any connection resources used by the method
			httpget.releaseConnection();
		}
		return ticketURLResponse;
	}

	public InputStream getFile(String url) throws IOException {

		HttpClient client = new HttpClient();
		client.getParams().setParameter(CredentialsProvider.PROVIDER, new ConsoleAuthPrompter());
		GetMethod httpget = new GetMethod(url);
		httpget.setDoAuthentication(true);

		// aritz
		InputStream response = null;

		try {
			// execute the GET
			int status = client.executeMethod(httpget);

			// aritz
			if (status != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + httpget.getStatusLine());
			}

			// print the status and response
			// System.out.println(httpget.getStatusLine().toString());
			// System.out.println(httpget.getResponseBodyAsString());

			response = httpget.getResponseBodyAsStream();

			System.out.println("response = " + response);

		} finally {

			// release any connection resources used by the method
			httpget.releaseConnection();
		}
		return response;
	}

}