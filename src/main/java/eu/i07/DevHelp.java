package eu.i07;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import eu.i07.Controls.AppFrame;
import eu.i07.Spark.Routes;
import eu.i07.Utils.Globals;
import eu.i07.Utils.Screen;

import static spark.Spark.*;

public class DevHelp {
	
	private static int AppPID = 0;
	
	private AppFrame frame;
	
	private int FrameWidth = 550;
	private int FrameHeight = 800;
	       
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			
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
		
		awaitInitialization();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					DevHelp window = new DevHelp();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static class JavaObject {
	    
		public void exit_application() {
	    	System.exit(0);
	    }
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
		
		AppPID = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
				
		System.out.println(AppPID);
		String trayIconPath = "";
		
		switch(Globals.OS) {
			case "WIN":
				trayIconPath = "images/devhelp-logo-color.png";
				break;
			default:
				trayIconPath = "images/" + Globals.AppTrayIcon + ".png";
				break;
		}
		
		// set the tray icon
        final TrayIcon trayIcon = new TrayIcon(createImage(trayIconPath, "tray icon"), "DevHelp");
        // add it to system tray
        final SystemTray tray = SystemTray.getSystemTray();
          
		frame = new AppFrame();
		frame.setBounds(100, 100, FrameWidth, FrameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
							
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	
            	//TODO: currently hard-coded task bar height in case 0
            	if (Screen.TaskbarHeight == 0) {
            		Screen.TaskbarHeight = 80;
            	};
            	
            	int frameTop = 0;
            	int frameLeft = 0;
            	
            	Point loc = Screen.getSystrayIconPosition(e);
            	            	
            	if (loc.y > (Screen.Height-100)) {
            		//taskbar/systray is at the bottom of the screen
            		frameTop = Screen.Height-(Screen.TaskbarHeight+FrameHeight);
            	} else {
            		//taskbar/systray is at the top of the screen
            		frameTop = loc.y;
            	}
            	
            	if (e.getButton() == MouseEvent.BUTTON1) {
            		
            		System.out.println("here:" + frameTop);
            		
            		frameLeft = loc.x-(FrameWidth/2);
            		
            		//TODO: taskbar detection still needed, taskbar height is taken from
            		//primary monitor, which is 0 when taskbar is on secondary monitor
            		if ((frameLeft+FrameWidth) > Screen.Width*2) {
            			frameLeft = Screen.Width*2-FrameWidth;
            		} else if ((frameLeft+FrameWidth) > Screen.Width) {
            			frameLeft = Screen.Width-FrameWidth;
            		}
            		
            		frame.setLocation(frameLeft, frameTop);
              
        			if (frame.isVisible()) {
        				frame.setVisible(false);
        			} else {
//        				
        				frame.setVisible(true);
        				if ("MAC".equals(Globals.OS)) {
        					MAC_BringSelfToFocus();
        				}        				
        			}
            	}
               
            	
            }
        });
               
        try {
        	
            tray.add(trayIcon);
        
        } catch (AWTException e) {
        
        	System.out.println("TrayIcon could not be added.");
        
        }
		
        if (Screen.MonitorCount > 1) {
        	//Point ownerLocationOnScreen = frame.getLocationOnScreen();
        	//System.out.println(ownerLocationOnScreen);
		}
		
	}
	

	private static void MAC_BringSelfToFocus()
	{
		
		String cmd = "tell application \"System Events\" to set frontmost of the first process whose unix id is " + AppPID + " to true";
		Runtime runtime = Runtime.getRuntime();
		String[] args = { "osascript", "-e", cmd };
		
		try {
			runtime.exec(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static Image createImage(String path, String description) {
        URL imageURL = DevHelp.class.getClassLoader().getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
        	// when on windows we need to return the icon 16x16
        	if ("WIN".equals(Globals.OS)) {
        		return (new ImageIcon(imageURL, description)).getImage().getScaledInstance(16, 16, 8);
        	} else {
        		return (new ImageIcon(imageURL, description)).getImage();
        	}
        }
    }

}
