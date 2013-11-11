/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciamento;

import com.data.DataInDB;
import com.entity.Professor;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GerenciarProfessorTest {
    
    public GerenciarProfessorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        EntityPersist ep = new EntityPersist();
        List<Professor> consult = (List<Professor>) ep.search(Professor.class);
        if (consult.isEmpty()) DataInDB.addProfessores();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        EntityPersist ep = new EntityPersist();
        List<Professor> professores = (List<Professor>) ep.search(Professor.class, 
                new CriteriaGroup("like", "nome", "%" + "test" + "%", null));
        for (Professor prof: professores){
            try {
                ep.delete(prof);
            } catch (Exception ex) {
                Logger.getLogger(GerenciarMatriculaTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<Professor> cadastrar(Professor prof){
        GerenciarProfessor gerenProf = new GerenciarProfessor();
        EntityPersist ep = new EntityPersist();
        gerenProf.setProfessor(prof);
        gerenProf.cadastrarProfessor(null);
        
        int id = prof.getId();
        
        return ep.search(Professor.class, 
                new CriteriaGroup("eq", "id", id, null)); 
    }
    
    @Test
    public void testCadastrarProfessor() {
        Professor professor = new Professor("test", "test", "00.000.000-0", "000000000", "00", 
                "teste", "teste", "teste", 'M');
        assertTrue("Não Cadastrado!", !cadastrar(professor).isEmpty());
    }
    
    @Test
    public void testNaoCadastrarProfessorCampoNull() {
        Professor professor = new Professor("test", "test", null, "000000000", "00", 
                "teste", "teste", "teste", 'M');
        assertTrue("Cadastrado!", cadastrar(professor).isEmpty());
    }
    
    @Test
    public void testNaoCadastrarProfessorIdIgual() {
        Professor professor = new Professor("test", "test", "00.000.000-0", "000000000", "00", 
                "teste", "teste", "teste", 'M');
        assertFalse("Cadastrado!", cadastrar(professor).size() > 1);
    }
    
    @Test
    public void testNaoCadastrarProfessorEmailExistente() {
        Professor professor = new Professor("test", "jose@uem.br", "00.000.000-0", "000000000", "00", 
                "teste", "teste", "teste", 'M');
        assertTrue("Cadastrado!", cadastrar(professor).isEmpty());
    }
}