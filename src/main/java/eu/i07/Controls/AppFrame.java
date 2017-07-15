package eu.i07.Controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.LoadURLParams;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.DisposeEvent;
import com.teamdev.jxbrowser.chromium.events.DisposeListener;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import eu.i07.DevHelp.JavaObject;

public class AppFrame extends JFrame implements WindowFocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8688590401062810053L;

	final static Browser browser = new Browser();
    public BrowserView view = new BrowserView(browser);
    
	public AppFrame() {
		
		super();
		
		browser.setPopupHandler(new PopupHandler() {
		    public PopupContainer handlePopup(PopupParams params) {
		    	
		    	
		    	
		        return new PopupContainer() {
		            public void insertBrowser(final Browser browser, final Rectangle initialBounds) {
		            	
		         		            	
		                SwingUtilities.invokeLater(new Runnable() {
		                	
		                    @Override
		                    public void run() {
		                    	
		                        BrowserView browserView = new BrowserView(browser);
		                        
		                        browserView.setPreferredSize(initialBounds.getSize());
		                        
		                        final JFrame frame = new JFrame("DevHelp Builder");
		                        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		                        frame.add(browserView, BorderLayout.CENTER);
		                        frame.setBounds(100, 100, 800, 550);
//		                        frame.pack();
		                        frame.setLocation(initialBounds.getLocation());
		                        frame.setVisible(true);
		                         
		                        frame.addWindowListener(new WindowAdapter() {
		                            @Override
		                            public void windowClosing(WindowEvent e) {
		                                browser.dispose();
		                            }
		                        });
		                        
		                        browser.addDisposeListener(new DisposeListener<Browser>() {
		                            public void onDisposed(DisposeEvent<Browser> event) {
		                                frame.setVisible(false);
		                            }
		                        });
		                         
		                        browser.addConsoleListener(new ConsoleListener() {
		                		    public void onMessage(ConsoleEvent event) {
		                		        System.out.println("Message: " + event.getMessage());
		                		    }
		                		});

		                    }
		                });
		            }
		        };
		    }
		});
		 
		setFocusable(true);
		requestFocusInWindow(true);
		
		startListeners();
	}
	
	private void startListeners() {
			    
		//attach javascript listener, to pass over the JavaObject
		browser.addScriptContextListener(new ScriptContextAdapter() {
		    @Override
		    public void onScriptContextCreated(ScriptContextEvent event) {
		        Browser browser = event.getBrowser();
		        JSValue window = browser.executeJavaScriptAndReturnValue("window");
		        window.asObject().setProperty("java", new JavaObject());
		    }
		});
				
		LoadURLParams urlparams = new LoadURLParams("http://127.0.0.1:1111", "", "DevHelp: true");
		
		browser.addConsoleListener(new ConsoleListener() {
		    public void onMessage(ConsoleEvent event) {
		        System.out.println("Message: " + event.getMessage());
		    }
		});
		
		browser.loadURL(urlparams);
        
        add(view, BorderLayout.CENTER);
                
		addWindowFocusListener(this);

		setVisible(false);
		setAlwaysOnTop(true);
		//setType(Type.POPUP);
		setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#2196f3")));
		
	}
	
	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		setVisible(false);
	}

}
