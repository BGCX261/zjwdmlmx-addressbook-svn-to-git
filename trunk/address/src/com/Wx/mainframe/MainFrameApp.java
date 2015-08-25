/*
 * MainFrameApp.java
 */

package com.Wx.mainframe;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import com.Wx.control.Icontroler;
import com.Wx.control.controler;
import com.Wx.model.Imodel;
import com.Wx.model.Model;


/**
 * The main class of the application.
 */
public class MainFrameApp extends SingleFrameApplication {

    private static final Imodel modeler = new Model();
    private static Icontroler control = new controler(modeler);
    
    public static Icontroler getControl()
    {
        return control;
    }
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new LogonDlg(null,true));
        
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     * @param root 
     */
    @Override protected void configureWindow(java.awt.Window root) {
        
    }
    @Override protected void shutdown()
    {
        
        super.shutdown();
        MainFrameApp.control.Shutdown();
    }
    /**
     * A convenient static getter for the application instance.
     * @return the instance of MainFrameApp
     */
    public static MainFrameApp getApplication() {
        return Application.getInstance(MainFrameApp.class);
    }
    protected void finalize() throws Throwable
    {
        super.finalize();
        this.control.Shutdown();
    }
    /**
     * Main method launching the application.
     * @param args 
     */
    public static void main(String[] args) {
        launch(MainFrameApp.class, args);
        
    }
}
