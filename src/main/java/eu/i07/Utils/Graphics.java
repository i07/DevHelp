package eu.i07.Utils;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Graphics {

	public static GraphicsDevice getDeviceAt(Point pos) {

	    GraphicsDevice device = null;

	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice lstGDs[] = ge.getScreenDevices();

	    ArrayList<GraphicsDevice> lstDevices = new ArrayList<GraphicsDevice>(lstGDs.length);

	    for (GraphicsDevice gd : lstGDs) {
	    	GraphicsConfiguration gc = gd.getDefaultConfiguration();
	    	Rectangle screenBounds = gc.getBounds();

	    	if (screenBounds.contains(pos)) {
	    		lstDevices.add(gd);
	    	}
	    }

	    if (lstDevices.size() > 0) {
	    	device = lstDevices.get(0);
	    } else {
	    	device = ge.getDefaultScreenDevice();
	    }
	    
	    return device;
	    
    }	
}
