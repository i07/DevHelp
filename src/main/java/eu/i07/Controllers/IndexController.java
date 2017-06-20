package eu.i07.Controllers;

import java.util.HashMap;
import java.util.Map;

import eu.i07.Spark.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {

	/**
	 * Serve the page for index page
	 * 
	 * @param request the spark request object
	 * @param response the spark response object
	 */
	public static Route serveIndexPage = (Request request, Response response) -> {
					
//		if (!"true".equals(request.headers("DevHelp"))) {
//			response.status(404);
//    		return null;
//		}
		
		Map<String, Object> attributes = new HashMap<>();
		return ViewUtil.render(request, attributes, "index/index.html");
	};
	
}
