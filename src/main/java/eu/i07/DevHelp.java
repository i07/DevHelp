package eu.i07;

import java.awt.*;
import java.awt.event.*;

//import java.awt.AWTException;
//import java.awt.EventQueue;
//import java.awt.GraphicsConfiguration;
//import java.awt.GraphicsDevice;
//import java.awt.GraphicsEnvironment;
//import java.awt.Image;
//import java.awt.Insets;
//import java.awt.Point;
//import java.awt.Rectangle;
//import java.awt.SystemTray;
//import java.awt.Toolkit;
//import java.awt.TrayIcon;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class DevHelp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DevHelp window = new DevHelp();
					window.frame.setVisible(false);
					window.frame.setAlwaysOnTop(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DevHelp() {
		if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		
		//final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage("images/devhelp-logo.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
       
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              Rectangle bounds = getSafeScreenBounds(e.getPoint());

              Point point = e.getPoint();

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

              if (x + frame.getPreferredSize().width > bounds.x + bounds.width) {
                x = (bounds.x + bounds.width) - frame.getPreferredSize().width;
              }
              if (y + frame.getPreferredSize().height > bounds.y + bounds.height) {
                y = (bounds.y + bounds.height) - frame.getPreferredSize().height;
              }
              frame.setLocation(x-(frame.getWidth()/2), y);
              if (frame.isVisible()) {
            	  frame.setVisible(false);
              } else {
            	  frame.setVisible(true);
              }
              //popup.setLocation(x, y);
              //popup.setVisible(true);
            }
          });
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
		
	}
	
	protected static Image createImage(String path, String description) {
        URL imageURL = DevHelp.class.getClassLoader().getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
	
	public static Rectangle getSafeScreenBounds(Point pos) {

	    Rectangle bounds = getScreenBoundsAt(pos);
	    Insets insets = getScreenInsetsAt(pos);

	    bounds.x += insets.left;
	    bounds.y += insets.top;
	    bounds.width -= (insets.left + insets.right);
	    bounds.height -= (insets.top + insets.bottom);

	    return bounds;

	  }

	  public static Insets getScreenInsetsAt(Point pos) {
	    GraphicsDevice gd = getGraphicsDeviceAt(pos);
	    Insets insets = null;
	    if (gd != null) {
	      insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
	    }
	    return insets;
	  }

	  public static Rectangle getScreenBoundsAt(Point pos) {
	    GraphicsDevice gd = getGraphicsDeviceAt(pos);
	    Rectangle bounds = null;
	    if (gd != null) {
	      bounds = gd.getDefaultConfiguration().getBounds();
	    }
	    return bounds;
	  }

	  public static GraphicsDevice getGraphicsDeviceAt(Point pos) {

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
