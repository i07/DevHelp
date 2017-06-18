package eu.i07.Spark;

import java.util.HashMap;
import java.util.Map;

import eu.i07.Utils.Globals;
import spark.ModelAndView;
import spark.Request;

public class ViewUtil {

	public static String render(Request request, Map<String, Object> model, String templatePath) {
				
		
		Map<String, Object> sessionAttr = new HashMap<>();
		
		for (String a: request.session().attributes()) {
			sessionAttr.put(a, request.session().attribute(a));
		}
		
		model.put("path",  templatePath);
		
		return Globals.TEMPLATE_ENGINE.render(new ModelAndView(model, "freemarker/" + templatePath));
		
	}
}
