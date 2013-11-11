/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciamento;

import com.data.DataInDB;
import com.entity.Aluno;
import com.entity.Matricula;
import com.entity.Turma;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yarchs
 */
public class GerenciarMatriculaTest {
    
    public GerenciarMatriculaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DataInDB.addAlunos();
        DataInDB.addProfessores();
        DataInDB.addCursos();
        DataInDB.addNiveis();
        DataInDB.addTurmas();
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
        for (Matricula m : (List<Matricula>)ep.search(Matricula.class)) {
            try {
                ep.delete(m);
            } catch (Exception ex) {
                Logger.getLogger(GerenciarMatriculaTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Test
    public void testCadastrarMatricula() {
        GerenciarMatricula matrMan = new GerenciarMatricula();
        EntityPersist ep = new EntityPersist();
        
        Turma turma = (Turma)ep.search(Turma.class, new CriteriaGroup("eq", "id", 1, null)).get(0);
        Aluno aluno = (Aluno)ep.search(Aluno.class, new CriteriaGroup("eq", "id", 1, null)).get(0);
        Matricula matricula = new Matricula(aluno, turma, new Date());
        
        assertTrue("Do not have an ID", 0 == matricula.getId());
        matrMan.cadastrar(matricula, turma); // Cadastrado
        assertFalse("Got an ID", 0 == matricula.getId());
        
        int newId = matricula.getId();
        matricula = null;
        
        List<Matricula> matriculas = ep.search(Matricula.class, new CriteriaGroup("eq", "id", newId, null));
        
        assertNotNull("Lista não nula", matriculas);
        
        matricula = matriculas.get(0);
        assertNotNull("Matricula encontrada!", matricula);
    }
    
    @Test
    public void testAlterarMatricula() {
        GerenciarMatricula matrMan = new GerenciarMatricula();
        EntityPersist ep = new EntityPersist();
        
        Matricula matricula = new Matricula((Aluno)ep.search(Aluno.class, new CriteriaGroup("eq", "id", 1, null)).get(0),
                (Turma)ep.search(Turma.class, new CriteriaGroup("eq", "id", 1, null)).get(0), new Date());
        matrMan.cadastrar(matricula, matricula.getTurma()); // Cadastrado
        
        int turmaId = matricula.getTurma().getId();
        int matId = matricula.getId();
        
        assertTrue("Id's iguais!", turmaId == matricula.getTurma().getId());
        matricula.setTurma((Turma)ep.search(Turma.class, new CriteriaGroup("eq", "id", 2, null)).get(0));
        matrMan.alterar(matricula); // Alterado
        
        matricula = null;
        matricula = (Matricula)ep.search(Matricula.class, new CriteriaGroup("eq", "id", matId, null)).get(0); // Pega do bd novamente
        assertFalse("Antigo id diferente do novo", turmaId == matricula.getId());
    }
    
    @Test
    public void testRemoverMatricula() {
        GerenciarMatricula matrMan = new GerenciarMatricula();
        EntityPersist ep = new EntityPersist();
        
        Matricula matricula = new Matricula((Aluno)ep.search(Aluno.class, new CriteriaGroup("eq", "id", 1, null)).get(0),
                (Turma)ep.search(Turma.class, new CriteriaGroup("eq", "id", 1, null)).get(0), new Date());
        matrMan.cadastrar(matricula, matricula.getTurma()); // Cadastrado
        System.out.println("M: " + matricula.getId());
        DataInDB.addMensalidades(1, matricula.getId()); // adiciona mensalidades
        
        int matId = matricula.getId();
        
        matricula = null;
        matricula = (Matricula)ep.search(Matricula.class, new CriteriaGroup("eq", "id", matId, null)).get(0); // Pega do bd novamente
        matrMan.remover(matricula); // deve remover mensalidades e setar matricula.estado para inativo
        matricula = null;
        
        matricula = null;
        matricula = (Matricula)ep.search(Matricula.class, new CriteriaGroup("eq", "id", matId, null)).get(0); // Pega do bd novamente
        assertEquals("Esta inativo", "inativo", matricula.getEstado());
        
        matricula = (Matricula) ep.mergeObject(matricula);
        assertTrue("Nao tem mensalidades.", matricula.getMensalidade().isEmpty());
        ep.endMerge();
    }
    
    @Test
    public void testConsultarMatricula() {
        GerenciarMatricula matrMan = new GerenciarMatricula();
        EntityPersist ep = new EntityPersist();
        
        Matricula matricula = new Matricula((Aluno)ep.search(Aluno.class, new CriteriaGroup("eq", "id", 1, null)).get(0),
                (Turma)ep.search(Turma.class, new CriteriaGroup("eq", "id", 1, null)).get(0), new Date());
        Matricula matricula2 = new Matricula((Aluno)ep.search(Aluno.class, new CriteriaGroup("eq", "id", 2, null)).get(0),
                (Turma)ep.search(Turma.class, new CriteriaGroup("eq", "id", 1, null)).get(0), new Date());
        Matricula matricula3 = new Matricula((Aluno)ep.search(Aluno.class, new CriteriaGroup("eq", "id", 3, null)).get(0),
                (Turma)ep.search(Turma.class, new CriteriaGroup("eq", "id", 4, null)).get(0), new Date());
        matrMan.cadastrar(matricula, matricula.getTurma()); // Cadastrado
        matrMan.cadastrar(matricula2, matricula2.getTurma()); // Cadastrado
        matrMan.cadastrar(matricula3, matricula3.getTurma()); // Cadastrado
        
        matrMan.remover(matricula2);
        
        matrMan.consultar("nome", "");
        assertTrue("Found everybody! Empty", matrMan.getMatriculas().size() == 2);
        matrMan.consultar("nome", "marcos");
        assertFalse("Marcos Found! Nome", matrMan.getMatriculas().isEmpty());
        matrMan.consultar("curso", "Ingles");
        assertTrue("Found everybody! Curso", matrMan.getMatriculas().size() == 2);
        matrMan.consultar("turma", "Turma 1");
        assertTrue("Found everybody! Turma", matrMan.getMatriculas().size() == 2);
        matrMan.consultar("nivel", "Ingles Int");
        assertTrue("Found everybody! Nivel", matrMan.getMatriculas().size() == 2);
        matrMan.consultar("estado", "inativo");
        assertTrue("Found inactive!", matrMan.getMatriculas().size() == 1);
        matrMan.consultar("nome", "Cachorro loko");
        assertTrue("NotFound -> OK!", matrMan.getMatriculas().isEmpty());
    }
    
}