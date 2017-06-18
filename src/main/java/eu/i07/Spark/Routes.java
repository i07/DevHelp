package eu.i07.Spark;

import static spark.Spark.*;

import eu.i07.Controllers.IndexController;
import eu.i07.Controllers.PagesController;

public class Routes {

	public Routes() {
		
		get("/", IndexController.serveIndexPage);
		
		get("/pages/index", PagesController.serveIndexPage);
	}
	
}
