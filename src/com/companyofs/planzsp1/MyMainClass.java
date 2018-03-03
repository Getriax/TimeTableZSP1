package com.companyofs.planzsp1;


import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.system.NativeLookup;
import com.codename1.ui.Toolbar;
import java.io.IOException;

/**
 * @author Nikodem Strawa
 * 
 */
public class MyMainClass {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        NetworkManager.getInstance().addErrorListener(e -> Dialog.show("Coś poszło nie tak...", "Wymagane połączenie z internetem", "Zrozumiałem", null));
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
        JsoupInterfaceIntegration jsoup = (JsoupInterfaceIntegration)NativeLookup.create(JsoupInterfaceIntegration.class);
        Form plan = new MyForm(jsoup);
        plan.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }
    
    public void destroy() {
    }

}
