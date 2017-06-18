package eu.i07;

import java.awt.*;
import java.awt.event.*;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import eu.i07.Utils.Screen;


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
			// If SystemTray is not supported the app cannot run
			// TODO: throw an error, so the user understands why it cannot run this application.
            System.out.println("SystemTray is not supported");
            return;
        }
		
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		
		// set the tray icon
        final TrayIcon trayIcon = new TrayIcon(createImage("images/devhelp-logo.png", "tray icon"));
        // add it to system tray
        final SystemTray tray = SystemTray.getSystemTray();
       
        // When we click on the icon, this will open our frame
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              Rectangle bounds = Screen.getSafeBounds(e.getPoint());

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
	

}
