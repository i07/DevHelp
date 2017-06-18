package eu.i07.Utils;

import eu.i07.DevHelp;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;

public class Globals {

	// FreeMarker variables
	public static FreeMarkerEngine	TEMPLATE_ENGINE		= new FreeMarkerEngine();
		
	public static Configuration		TEMPLATE_CONFIG		= new Configuration();
	
	public static void init() {
		
		// Set template config
		TEMPLATE_CONFIG.setTemplateLoader(new ClassTemplateLoader(DevHelp.class, "/"));
		TEMPLATE_CONFIG.setOutputEncoding("UTF-8");
		TEMPLATE_CONFIG.setNumberFormat("0.######");
        TEMPLATE_CONFIG.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
		TEMPLATE_ENGINE.setConfiguration(TEMPLATE_CONFIG);
	}
}
