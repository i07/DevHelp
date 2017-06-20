package eu.i07.Controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.LoadURLParams;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import eu.i07.DevHelp.JavaObject;

public class AppFrame extends JFrame implements FocusListener, WindowFocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8688590401062810053L;

	final static Browser browser = new Browser();
    public BrowserView view = new BrowserView(browser);
    
	public AppFrame() {
		super();
		
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

//		view.setVisible(true);
		//view.setEnabled(true);
		//view.setRequestFocusEnabled(true);
        //view.addFocusListener(this);
        
        add(view, BorderLayout.CENTER);
                
//		addWindowListener(this);
		addWindowFocusListener(this);
//		addWindowStateListener(this);
		
		setVisible(false);
		setAlwaysOnTop(true);
		//setType(Type.POPUP);
		setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#2196f3")));
		
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		System.out.println("ehh");
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		System.out.println("ehh2");
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("1ehh");
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		//System.out.println("2ehh");
		setVisible(false);
	}

}
