package alfrescoConection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.auth.NTLMScheme;
import org.apache.http.impl.auth.RFC2617Scheme;

public class ConsoleAuthPrompter implements CredentialsProvider {
	private BufferedReader in = null;

	public ConsoleAuthPrompter() {
		super();
		this.in = new BufferedReader(new InputStreamReader(System.in));
	}

	private String readConsole() throws IOException {
		return this.in.readLine();
	}

	public Credentials getCredentials(final AuthScheme authscheme, final String host, int port, boolean proxy) {
		if (authscheme == null) {
			return null;
		}
		try {
			if (authscheme instanceof NTLMScheme) {
				System.out.println(host + ":" + port + " requires Windows authentication");
				System.out.print("Enter domain: ");
				String domain = readConsole();
				System.out.print("Enter username: ");
				String user = readConsole();
				System.out.print("Enter password: ");
				String password = readConsole();
				return new NTCredentials(user, password, host, domain);
			} else if (authscheme instanceof RFC2617Scheme) {
				System.out.println(
						host + ":" + port + " requires authentication with the realm '" + authscheme.getRealm() + "'");
				System.out.print("Enter username: ");
				String user = readConsole();
				System.out.print("Enter password: ");
				String password = readConsole();
				return new UsernamePasswordCredentials(user, password);
			} else {
				throw new RuntimeException("qe se yo");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Credentials getCredentials(AuthScope arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCredentials(AuthScope arg0, Credentials arg1) {
		// TODO Auto-generated method stub

	}
}
