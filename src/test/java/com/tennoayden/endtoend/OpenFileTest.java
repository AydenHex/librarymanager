package com.tennoayden.endtoend;

import com.tennoayden.app.gui.controllers.HomeController;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.assertj.swing.timing.Pause;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class OpenFileTest extends AssertJSwingTestCaseTemplate {
    private FrameFixture frame;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {
        this.setUpRobot();

        HomeController window = GuiActionRunner.execute(() -> new HomeController("Gestion de ma bibliotheque"));
        this.frame = new FrameFixture(this.robot(), window.getView());
        this.frame.show();
    }

    @Test
    public void testOuvrirXML() {
        this.frame.menuItemWithPath("Fichier", "Ouvrir").click();
        this.frame.fileChooser().selectFile(new File("/home/ayden/projets/libmanager-v3/Biblio.xml"));
        this.frame.fileChooser().approve();
        this.frame.table().requireRowCount(3);
    }

    @Test
    public void testOuvrirMauvaisFichier() {
        this.frame.menuItemWithPath("Fichier", "Ouvrir").click();
        this.frame.fileChooser().selectFile(new File("/home/ayden/projets/libmanager-v3/bad_biblio.tal"));
        this.frame.fileChooser().approve();
        this.frame.table().requireRowCount(0);
        this.frame.optionPane().requireErrorMessage();
    }

    @After
    public void tearDown() {
        this.frame.cleanUp();
    }
}