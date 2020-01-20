package com.tennoayden.app;
import com.tennoayden.kotlin.app.gui.*;
import com.tennoayden.app.gui.controllers.HomeController;
import com.tennoayden.app.gui.views.HomeView;

/**
 * Hello world!
 */
public class App
{
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main( String[] args )
    {
        kotlin.jvm.JvmClassMappingKt.getKotlinClass(LoginApp .class);
        LoginApp test =new LoginApp();

        //HomeController home = new HomeController("Gestion de ma Bibiliotheque");
    }
}
