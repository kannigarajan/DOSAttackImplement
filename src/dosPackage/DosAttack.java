package dosPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 *  This class implements DOS Attack in the server, it will shutdown TOMCAT
 *  @author KannigaNatarajan
 *  @version 3.0
 */
@WebServlet("/DOSAttack")
public class DosAttack extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(WelcomeServlet.class);
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//Djava.net.preferIPv4Stack=true
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			logger.info("taking tomcat path from properties file");
			InputStream inputStream;

			Properties prop = new Properties();
			String propFileName = "config.properties";
            
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				logger.error("Properties file not found-exception thrown");
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
  
			String filePath=prop.getProperty("tomcatShutPath");
			logger.info("Calling script to shutdown tomcat");
			String filename = filePath+"\\catalina.sh stop";
			Process child = Runtime.getRuntime().exec(filename);
			logger.error("TOMCAT SHUTDOWN"+child);
		}catch(IOException e) {
			logger.error("IO Exception-exception thrown");
			System.out.println("IO exception occured"+e);
		}
	}

	/**
	 * Do post will call doGet method internally to run stand alone
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
