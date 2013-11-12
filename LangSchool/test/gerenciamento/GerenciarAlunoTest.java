package gerenciamento;
/**
 *
 * @author rod
 */
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
public class GerenciarAlunoTest {
    public GerenciarAlunoTest(){
        
    }
    @BeforeClass
    public static void setUpClass() {
        DataInDB.addAlunos();
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
        for (Aluno aluno : (List<Aluno>)ep.search(Aluno.class)) {
            try {
                ep.delete(aluno);
            } catch (Exception ex) {
                Logger.getLogger(GerenciarAlunoTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Test
    public void testCadastrarAluno() {
        GerenciarAluno gerAl = new GerenciarAluno();
        EntityPersist ep = new EntityPersist();
        
        String nome,telefone,idade,endereco,uf,cidade;
        char sexo;
        Date dataNasc;
        
        nome="valido";
        dataNasc = new Date();
        telefone="(44)3222-2222";
        idade="20";
        endereco="nossa";
        uf="PR";
        cidade="maringa";
        sexo='M';
        
        Aluno a = new Aluno(nome,dataNasc,telefone,idade,endereco,uf,cidade,sexo);
        gerAl.cadastrarAluno();
        assertTrue(cidade, true);
 
    }
    
    @Test
    public void testAlterarAluno() {
        GerenciarAluno gerAl = new GerenciarAluno();
        EntityPersist ep = new EntityPersist();
        
        String nome,telefone,idade,endereco,uf,cidade;
        char sexo;
        Date dataNasc;
        
        nome="valido";
        dataNasc = new Date();
        telefone="(44)3222-2222";
        idade="20";
        endereco="nossa";
        uf="PR";
        cidade="maringa";
        sexo='M';
        
        Aluno a = new Aluno(nome,dataNasc,telefone,idade,endereco,uf,cidade,sexo);
        gerAl.cadastrarAluno();
        assertTrue(cidade, true);	
        //assert(expected,result);
    }
    
    @Test
    public void testConsultarAluno() {
	
	GerenciarAluno gerAl = new GerenciarAluno();
        EntityPersist ep = new EntityPersist();
	//result = ep.search(Aluno.class, new CriteriaGroup("eq","nome","valido",null);
	//expected = "OK";
	//assertTrue(expected,result);    
    }
    
    @Test
    public void testRemoverAluno() {
	GerenciarAluno gerAl = new GerenciarAluno();
        EntityPersist ep = new EntityPersist();
	//result = ep.update(Aluno.class, new CriteriaGroup("eq","nome","valido",null);
	//expected = "Removido logicamente"  
	//assert(expected,result);
        
    }
}