package eu.i07.Utils;

import java.util.prefs.Preferences;

import eu.i07.DevHelp;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;

public class Globals {

	// FreeMarker variables
	public static FreeMarkerEngine	TEMPLATE_ENGINE		= new FreeMarkerEngine();	
	public static Configuration		TEMPLATE_CONFIG		= new Configuration();
	public static String OS = null;
	
	Preferences prefs = null;
	
	private static final String APPTRAYICON = "app_tray_icon";
	
	public static String AppTrayIcon = null;
	
	public static void init() {
	
		// set global operating system name
		if (System.getProperty("os.name").contains("Mac")) {
	        OS = "MAC";
		} else if (System.getProperty("os.name").contains("Windows")) {
			OS = "WIN";
		}
		
		Preferences prefs = Preferences.userNodeForPackage(DevHelp.class);
		
		AppTrayIcon = prefs.get(APPTRAYICON, "devhelp-logo");
		
		// Set template config
		TEMPLATE_CONFIG.setTemplateLoader(new ClassTemplateLoader(DevHelp.class, "/"));
		TEMPLATE_CONFIG.setOutputEncoding("UTF-8");
		TEMPLATE_CONFIG.setNumberFormat("0.######");
        TEMPLATE_CONFIG.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
		TEMPLATE_ENGINE.setConfiguration(TEMPLATE_CONFIG);
	}
}
