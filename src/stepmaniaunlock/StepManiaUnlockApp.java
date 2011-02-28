/*
 * StepManiaUnlockApp.java
 */

package stepmaniaunlock;

import javax.swing.UIManager;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class StepManiaUnlockApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (Exception e) {
//            System.err.println (e.getMessage ());
//        }
        show(new StepManiaUnlockView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of StepManiaUnlockApp
     */
    public static StepManiaUnlockApp getApplication() {
        return Application.getInstance(StepManiaUnlockApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(StepManiaUnlockApp.class, args);
    }
}
