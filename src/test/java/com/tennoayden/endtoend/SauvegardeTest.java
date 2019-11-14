package com.tennoayden.endtoend;

import com.tennoayden.app.business.models.ObjectFactory;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.business.services.ConfigService;
import com.tennoayden.app.gui.controllers.HomeController;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class SauvegardeTest extends AssertJSwingTestCaseTemplate {
    private FrameFixture frame;

    @BeforeClass
    public static void setUpOnce(){
        FailOnThreadViolationRepaintManager.install();
        BibliothequeService.getInstance().bibliotheque = new ObjectFactory().createBibliotheque();
    }

    @Before
    public void setUp() {
        this.setUpRobot();

        HomeController window = GuiActionRunner.execute(() -> new HomeController("Gestion de ma bibliotheque"));
        this.frame = new FrameFixture(this.robot(), window.getView());
        this.frame.show();
    }

    @Test
    public void sauvegardeNominal() {
        ConfigService.getInstance().path = "test.xml";
        ConfigService.getInstance().modification = true;

        this.frame.menuItemWithPath("Edition", "Sauvegarder...").click();
        this.frame.optionPane().requireInformationMessage();
    }

    /**@Test
    public void sauvegarderWithoutPath() {
        ConfigService.getInstance().path = "";
        ConfigService.getInstance().modification = true;

        this.frame.menuItemWithPath("Edition", "Sauvegarder...").click();
        this.frame.fileChooser().selectFile(new File("/home/ayden/test4.xml"));
        this.frame.fileChooser().approve();
        this.frame.optionPane().requireInformationMessage();
    }*/

    @After
    public void tearDown() {
        this.frame.cleanUp();
    }
}
