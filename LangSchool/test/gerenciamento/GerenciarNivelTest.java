/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciamento;

import com.data.DataInDB;
import com.entity.Curso;
import com.entity.Nivel;
import com.persist.EntityPersist;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIForm;
import javax.faces.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lucas
 */
public class GerenciarNivelTest {

    public GerenciarNivelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        EntityPersist ep = new EntityPersist();
        List l;
        l = ep.search(Curso.class);
        if (l.isEmpty()) {
            DataInDB.addCursos();
        }
        l = ep.search(Nivel.class);
        if (l == null || l.isEmpty()) {
            DataInDB.addNiveisforTest();
        } else {
            for (Nivel n : (List<Nivel>) l) {
                try {
                    ep.delete(n);
                } catch (Exception ex) {
                    Logger.getLogger(GerenciarNivelTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                DataInDB.addNiveisforTest();
            }
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConsultarNivelNome() {
        System.out.println("consultarNivelNome");
        ActionEvent ae = new ActionEvent(new UIForm());
        GerenciarNivel instance = new GerenciarNivel();
        instance.setSearchParam("nome");

        instance.setSearchValue("japo");
        instance.consultarNivel(ae);
        assertFalse(instance.getNiveis().isEmpty());

        instance.setSearchValue("nothingheretest");
        instance.consultarNivel(ae);
        assertTrue(instance.getNiveis().isEmpty());

    }

    @Test
    public void testConsultarNivelDescricao() {
        System.out.println("consultarNivelDescricao");
        ActionEvent ae = new ActionEvent(new UIForm());
        GerenciarNivel instance = new GerenciarNivel();
        instance.setSearchParam("descricao");

        instance.setSearchValue("empreendedores");
        instance.consultarNivel(ae);
        assertFalse(instance.getNiveis().isEmpty());

        instance.setSearchValue("hiragana");
        instance.consultarNivel(ae);
        assertFalse(instance.getNiveis().isEmpty());

    }

    @Test
    public void testConsultarNivelCurso() {
        System.out.println("consultarNivelCurso");
        ActionEvent ae = new ActionEvent(new UIForm());
        GerenciarNivel instance = new GerenciarNivel();
        instance.setSearchParam("curso");

        instance.setSearchValue("Fracês");
        instance.consultarNivel(ae);
        assertTrue(instance.getNiveis().isEmpty());

        instance.setSearchValue("Esp");
        instance.consultarNivel(ae);
        assertFalse(instance.getNiveis().isEmpty());

    }
}