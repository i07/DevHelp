package eu.i07.Utils;

import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

public class Screen {

	public static int TaskbarHeight = 0;
	
	static {
		
		TaskbarHeight = getTaskbarHeight();
	}
	
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
	 
	public static Point getSystrayIconPosition(MouseEvent me) {
		
		Rectangle bounds = Screen.getSafeBounds(me.getPoint());

		Point point = me.getPoint();

		int x = point.x;
		int y = point.y;
		
		if (y < bounds.y) {
			y = bounds.y;
		} else if (y > bounds.y + bounds.height) {
			y = bounds.y + bounds.height;
		}
		
		if (x < bounds.x) {
			x = bounds.x;
		} else if (x > bounds.x + bounds.width) {
			x = bounds.x + bounds.width;
		}
			
		return new Point(x,y);
		
	}
	
	public static int getTaskbarHeight() {
		
		GraphicsDevice gd = Graphics.getDeviceAt(new Point(0,0));
		//height of the task bar
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
		return scnMax.bottom;
	}
}
