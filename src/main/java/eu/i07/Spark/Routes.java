package eu.i07.Spark;

import static spark.Spark.*;

import eu.i07.Controllers.IndexController;

public class Routes {

	public Routes() {
		
		get("/", IndexController.serveIndexPage);
		
	}
	
}
