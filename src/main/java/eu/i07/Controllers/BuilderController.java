package eu.i07.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.i07.Spark.ViewUtil;
import eu.i07.models.Module;
import spark.Request;
import spark.Response;
import spark.Route;

public class BuilderController {
	
	/**
	 * Serve the page for index page
	 * 
	 * @param request the spark request object
	 * @param response the spark response object
	 */
	public static Route serveIndexPage = (Request request, Response response) -> {
							
		Map<String, Object> attributes = new HashMap<>();
		
		List<Module> modules = new ArrayList<Module>();
		
		modules.add(new Module("DateTime","datetime"));
		modules.add(new Module("Base64", "base64"));
				
		attributes.put("modules", modules);
		
		return ViewUtil.render(request, attributes, "builder/index.html");
	};
}
