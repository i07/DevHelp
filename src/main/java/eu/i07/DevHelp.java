package eu.i07;

import java.awt.*;
import java.awt.Window.Type;
import java.awt.event.*;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import eu.i07.Spark.Routes;
import eu.i07.Utils.Globals;
import eu.i07.Utils.Screen;

import static spark.Spark.*;

public class DevHelp {

	private JFrame frame;

	final Browser browser = new Browser();
    BrowserView view = new BrowserView(browser);
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		if (System.getProperty("os.name").contains("Mac")) {
	        // Required to remove dock icon in Mac OSX.
	        System.setProperty("apple.awt.UIElement", "true");
		}
		
		Globals.init();
		
		// Set static files location
        if (1==1) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/static");
        }
        
		port(1111);
		
		new Routes();
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DevHelp window = new DevHelp();
					window.frame.setVisible(false);
					window.frame.setAlwaysOnTop(true);
					window.frame.setType(Type.POPUP);
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
		
		frame.add(view, BorderLayout.CENTER);
		
		browser.loadURL("http://127.0.0.1:1111");
		
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
