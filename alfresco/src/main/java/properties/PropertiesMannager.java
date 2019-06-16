package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesMannager {
	/**
	 * Lee el archivo properties de la aplicación y retorna un objeto properties.
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Properties getProperties() throws IOException {
		String propertiesPath = System.getProperty("catalina.base") + File.separator + "lib" + File.separator + "tbpm-config.properties";
		Properties prop = new Properties();
		InputStream input = new FileInputStream(propertiesPath);
		prop.load(input);
		return prop;
	}
}
