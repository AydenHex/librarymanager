package com.tennoayden.endtoend;

import com.tennoayden.app.gui.controllers.HomeController;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class CloseTest extends AssertJSwingTestCaseTemplate {private FrameFixture frame;

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
    public void testFermer() {
        this.frame.menuItemWithPath("Fichier", "Fermer").click();
    }

    @Test
    public void testFermerModification() {

    }

    @After
    public void tearDown() {
        this.frame.cleanUp();
    }

}
