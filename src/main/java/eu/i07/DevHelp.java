package eu.i07;

import java.awt.*;
import java.awt.Window.Type;
import java.awt.event.*;
import java.net.URL;

import javax.management.monitor.Monitor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Popup;
import javax.swing.UIManager;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.LoadURLParams;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import eu.i07.Spark.Routes;
import eu.i07.Utils.Globals;
import eu.i07.Utils.Screen;

import static spark.Spark.*;

public class DevHelp {
	
	static {
		
	    //System.out.println(java.awt.GraphicsEnvironment.isHeadless());
	}
	
	private JFrame frame;
	private PopupMenu popup;
	
	private int FrameWidth = 550;
	private int FrameHeight = 800;
	
	final static Browser browser = new Browser();
    BrowserView view = new BrowserView(browser);
       
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		Globals.init();
			
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
		} catch(Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
								
		System.out.println(Globals.OS);
		//attach javascript listener, to pass over the JavaObject
		browser.addScriptContextListener(new ScriptContextAdapter() {
		    @Override
		    public void onScriptContextCreated(ScriptContextEvent event) {
		        Browser browser = event.getBrowser();
		        JSValue window = browser.executeJavaScriptAndReturnValue("window");
		        window.asObject().setProperty("java", new JavaObject());
		    }
		});
			
		// Set static files location
        if (1==2) {
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
					DevHelp window = new DevHelp();
					window.frame.setVisible(false);
					window.frame.setAlwaysOnTop(true);
					window.frame.setType(Type.POPUP);
					window.frame.setUndecorated(true);
					window.frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#2196f3")));
																			
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
        
//        final Frame Invframe = new Frame();
//		Invframe.setUndecorated(true);
//		Invframe.setVisible(true);
//        
		frame = new JFrame();
		frame.setBounds(100, 100, FrameWidth, FrameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(view, BorderLayout.CENTER);

//		popup = new PopupMenu();
//		
//		//1t menuitem for popupmenu
//	    MenuItem action = new MenuItem("Action");
//	    action.addActionListener(new ActionListener() {
//	        @Override
//	        public void actionPerformed(ActionEvent e) {
//	            JOptionPane.showMessageDialog(null, "Action Clicked");
//	            trayIcon.setPopupMenu(null);
//	            //Invframe.remove(popup);
//	        }
//	    });
//	    popup.add(action);
//		
//	    //2nd menuitem of popupmenu
//	    MenuItem close = new MenuItem("Close");
//	    close.addActionListener(new ActionListener() {
//	        @Override
//	        public void actionPerformed(ActionEvent e) {
//	            System.exit(0);             
//	        }
//	    });
//	    popup.add(close);
		LoadURLParams urlparams = new LoadURLParams("http://127.0.0.1:1111", "", "DevHelp: true");
		
		browser.addConsoleListener(new ConsoleListener() {
		    public void onMessage(ConsoleEvent event) {
		        System.out.println("Message: " + event.getMessage());
		    }
		});
		
		browser.loadURL(urlparams);
				
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	
            	//TODO: currently hardcoded taskbar height in case 0
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
            		
            		System.out.println(frameTop);
            		
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
        				frame.setVisible(true);
        			}
            	}
                if (e.getButton() == MouseEvent.BUTTON3) {
                	
                	//Invframe.add(popup);
                	
                	//popup.show(Invframe, loc.x, e.getYOnScreen());
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
