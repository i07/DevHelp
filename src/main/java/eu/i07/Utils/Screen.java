package eu.i07.Utils;

import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Screen {

	public static Rectangle getSafeBounds(Point pos) {

	    Rectangle bounds = getBoundsAt(pos);
	    Insets insets = getInsetsAt(pos);

	    bounds.x += insets.left;
	    bounds.y += insets.top;
	    bounds.width -= (insets.left + insets.right);
	    bounds.height -= (insets.top + insets.bottom);

	    return bounds;

	}

	public static Insets getInsetsAt(Point pos) {
		
	    GraphicsDevice gd = Graphics.getDeviceAt(pos);
	    Insets insets = null;
	    
	    if (gd != null) {
	    	insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
	    }
	    
	    return insets;
	}

	public static Rectangle getBoundsAt(Point pos) {
		
	    GraphicsDevice gd = Graphics.getDeviceAt(pos);
	    Rectangle bounds = null;
	    
	    if (gd != null) {
	    	bounds = gd.getDefaultConfiguration().getBounds();
	    }
	    return bounds;
    }
	  
}
