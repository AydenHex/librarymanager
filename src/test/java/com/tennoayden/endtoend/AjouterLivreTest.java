package com.tennoayden.endtoend;

import com.tennoayden.app.business.models.ObjectFactory;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.gui.controllers.HomeController;
import de.svenjacobs.loremipsum.LoremIpsum;
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

public class AjouterLivreTest extends AssertJSwingTestCaseTemplate {
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
    public void testAjouterLivre() {
        this.frame.menuItemWithPath("Edition", "Ajouter un livre").click();
        String[][] content = new String[1][7];
        content[0][0] = "Test";
        content[0][1] = "Louis Rigaud";
        content[0][2] = "2018";
        content[0][3] = "TestTestTestResume";
        content[0][4] = "ACQUIS";
        content[0][5] = "3";
        content[0][6] = "2";
        DialogFixture ajoutLivre = this.frame.dialog();
        ajoutLivre.textBox("titre").enterText("Test");
        ajoutLivre.textBox("auteurprenom").enterText("Louis");
        ajoutLivre.textBox("auteurnom").enterText("Rigaud");
        ajoutLivre.comboBox("status").selectItem("ACQUIS");
        ajoutLivre.textBox("resume").enterText("TestTestTestResume");
        ajoutLivre.textBox("parution").enterText("2018");
        ajoutLivre.textBox("colonne").enterText("2");
        ajoutLivre.textBox("rangee").enterText("3");
        ajoutLivre.textBox("image").enterText("image.png");

        ajoutLivre.button("appliquer").click();
        this.frame.table().requireRowCount(1);
        this.frame.table().requireContents(content);
    }

    @Test
    public void testAjouterLivreMauvaiseValeurs() {
        this.frame.menuItemWithPath("Edition", "Ajouter un livre").click();

        DialogFixture ajoutLivre = this.frame.dialog();
        ajoutLivre.textBox("titre").enterText("Test");
        ajoutLivre.textBox("auteurprenom").enterText("Louis");
        ajoutLivre.textBox("auteurnom").enterText("Rigaud");
        ajoutLivre.comboBox("status").selectItem("ACQUIS");
        ajoutLivre.textBox("resume").enterText("TestTestTestResume");
        ajoutLivre.textBox("parution").enterText("2050");
        ajoutLivre.textBox("colonne").enterText("10");
        ajoutLivre.textBox("rangee").enterText("10");
        ajoutLivre.textBox("image").enterText("image.png");

        ajoutLivre.button("appliquer").click();
        this.frame.optionPane().requireErrorMessage();
    }

    @After
    public void tearDown() {
        this.frame.cleanUp();
    }
}
