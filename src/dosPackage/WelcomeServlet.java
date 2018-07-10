package dosPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import java.util.Properties;

import java.util.ArrayList;  

/**
 * The Class WelcomeServlet get a product name from HTML page and shows the result 
 * 
 * @author KannigaNatarajan
 * @version 3.0
 */
public class WelcomeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bufreader for reading file initializing to null
	 */
	BufferedReader bufReader = null;
	final static Logger logger = Logger.getLogger(WelcomeServlet.class);

	/*
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Getting product name from user");
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String productName = " ";
		request.getParameter("productname");
		ArrayList<String> list = new ArrayList<String>();
		InputStream inputStream;
		logger.info("taking tomcat path from properties file");
		Properties prop = new Properties();
		String propFileName = "config.properties";

		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			logger.error("properties file not found");
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}

		String filePath= " ";
		filePath = prop.getProperty("productFilePath");
		logger.info("Product name and file path passed for search");
		list = this.ProductSearch(productName,filePath);
		logger.info("Displaying products");
		out.println(
				"<html>\n" +
						"<head><title>products </title></head>\n" +
						"<style>"+
						"form {"+
						"width: 300px;"+
						"margin: 300 auto;"+
						"}"+
						"</style>" +
						"<body bgcolor = \"#f0f0f0\">\n" +
						"<form>"+
						"<h3> Available Products are :</h3>\n"
				);
		for(int n=0;n<list.size();n++) {
			out.println(
					"<li> "+ list.get(n)+ "</li>"
					);}
		out.print(
				"<input id=\"back\" type=\"button\" value=\"Logout\" onclick=\"location.href='index.html';\" />" +
						"</form>"+
						"</body>" +
						"</html>"
				);

		out.close();  
	}

	/**
	 * Product search.
	 *
	 * @param productName the product name
	 * @return the array list
	 */
	ArrayList<String> ProductSearch(String productName,String filePath){
		ArrayList<String> list = new ArrayList<String>();
		String fileName = "";
		logger.info("Product search initiated");
		
		if(productName.equals("Dairy Products")) {
			fileName = filePath+"\\dairy.txt";
		}else if(productName.equals("Stationary Items")) {
			fileName = filePath+"\\stationary.txt";
		}else if(productName.equals("Grocery Items")) {
			fileName = filePath+"\\grocery.txt";
		}else if(productName.equals("Kitchen Items")) {
			fileName = filePath+"\\kitchen.txt";
		}else if(productName.equals("Plastic Items")) {
			fileName = filePath+"\\plastic.txt";
		}else {
			list.add("not found");
		}
		/*LAMBDA EXPRESSIONS*/
		DosLambda myexp=(String x,ArrayList<String> y)->{
			try {
				logger.info("Reading files line by line");
				bufReader = new BufferedReader(new FileReader(x));
				String line = bufReader.readLine(); 
				while (line != null && !(line.isEmpty())) { 
					y.add(line);
					line = bufReader.readLine(); }
			} catch (IOException i) {
				logger.error("IOExcetion thrown");
				System.out.println("IOException" +i);
			}
			return y;
		};
		list = myexp.readBuffer(fileName, list);
		return list;
	}
	/* FUNCTIONAL INTERFACE*/
	interface DosLambda{
        ArrayList<String> readBuffer(String x,ArrayList<String> a);
	}

	/**
	 * Do post will call doPost method internally to run stand alone
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}
