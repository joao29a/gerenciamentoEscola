package gerenciamento;

import com.data.DataInDB;
import com.entity.Aluno;
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

    GerenciarAluno gerAl = new GerenciarAluno();
    String nome, telefone, endereco, uf, cidade, nomemae, nomepai;
    char sexo;
    int idade;
    Date dataNasc;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        nome = "rogerio";
        dataNasc = new Date(1999, 4, 20);
        telefone = "(44)3222-2222";
        idade = 20;
        endereco = "nossa";
        uf = "PR";
        cidade = "maringa";
        sexo = 'M';
        nomemae = "";
        nomepai = "";
    }

    @After
    public void tearDown() {
        EntityPersist ep = new EntityPersist();
        for (Aluno aluno : gerAl.getAlunos()) {
            try {
                ep.delete(aluno);
            } catch (Exception ex) {
                Logger.getLogger(GerenciarAlunoTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void testCadastrarAlunoSucesso() {
        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.getAluno().setNomeMae(nomemae);
        gerAl.getAluno().setNomePai(nomepai);
        gerAl.cadastrarAluno();
        gerAl.setAlunos(gerAl.getEp().search(Aluno.class,
                new CriteriaGroup("eq", "nome", "rogerio", null)));
        String resultado = "invalido";
        for (Aluno a : gerAl.getAlunos()) {
            if (a.getNome().equals("rogerio")) {
                resultado = "valido";
            }
        }
        assertEquals("valido", resultado);
    }

    @Test(expected = Exception.class)
    public void testCadastrarAlunoFalhaNome() {
        nome = "la";

        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.cadastrarAluno();}

    @Test(expected = Exception.class)
    public void testCadastrarAlunoFalhaData() {
        dataNasc = null;

        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.cadastrarAluno();}
    
    @Test(expected = Exception.class)
    public void testCadastrarAlunoFalhaTel() {
        telefone = "";

        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.cadastrarAluno();}
    
    @Test(expected = Exception.class)
    public void testCadastrarAlunoFalhaIdade() {
        idade = -4;

        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.cadastrarAluno();}
    
    @Test(expected = Exception.class)
    public void testCadastrarAlunoFalhaEndereco() {
        endereco = "";

        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.cadastrarAluno();}
    
    @Test(expected = Exception.class)
    public void testCadastrarAlunoFalhaUf() {
        endereco = "";

        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.cadastrarAluno();}    
    
    @Test(expected = Exception.class)
    public void testCadastrarAlunoFalhaCidade() {
        cidade = "";

        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.cadastrarAluno();}
    
    @Test(expected = Exception.class)
    public void testCadastrarAlunoFalhaSexo() {
        sexo = 'x';

        gerAl.setAluno(new Aluno(nome, dataNasc, telefone, idade, endereco, uf, cidade, sexo));
        gerAl.cadastrarAluno();}
    
    
    
    
    @Test
    public void testConsultarNomeSucesso() {
        DataInDB.addAlunos();
        String resultado = "invalido";
        List<Aluno> result = gerAl.getEp().search(Aluno.class, new CriteriaGroup("eq", "nome", "Claudio Santos", null));
        for (Aluno a : result) {
            if (a.getNome().equals("Claudio Santos")) {
                resultado = "valido";
            }
        }
        assertEquals("valido", resultado);}
    
    @Test
    public void testConsultarNomeFalha() {
        DataInDB.addAlunos();
        String resultado = "notFound";
        List<Aluno> result = gerAl.getEp().search(Aluno.class, new CriteriaGroup("eq", "nome", "naoexiste", null));
        for (Aluno a : result) {
            if (a.getNome().equals("Claudio Santos")) {
                resultado = "found";
            }
        }
        assertEquals("notFound", resultado);}

    
    
    
    @Test
    public void testAlterarAlunoSucesso() {
        DataInDB.addAlunos();
        List<Aluno> lista = gerAl.getEp().search(Aluno.class, new CriteriaGroup("eq", "nome", "Claudio Santos", null));
        int idCapturado = -1;
        for (Aluno a : lista) {
            if (a.getNome().equals("Claudio Santos")) {
                gerAl.setSelecionado(a);
                gerAl.getSelecionado().setNome("Claudio Altera");
                idCapturado = gerAl.getSelecionado().getId();
            }
        }
        gerAl.alterarAluno(null);

        int idCapturadoFinal = -1;
        List<Aluno> listaFinal = gerAl.getEp().search(Aluno.class, new CriteriaGroup("eq", "nome", "Claudio Altera", null));
        for (Aluno a : listaFinal) {
            if (a.getNome().equals("Claudio Altera")) {
                gerAl.setSelecionado(a);
                idCapturadoFinal = gerAl.getSelecionado().getId();
            }
        }
        assertEquals(idCapturadoFinal, idCapturado);
        assertEquals("Claudio Altera", gerAl.getSelecionado().getNome());}
    
    @Test(expected = Exception.class)
    public void testAlterarAlunoFalha() {
        DataInDB.addAlunos();
        List<Aluno> lista = gerAl.getEp().search(Aluno.class, new CriteriaGroup("eq", "nome", "Claudio Santos", null));
        for (Aluno a : lista) {
            if (a.getNome().equals("Claudio Santos")) {
                gerAl.setSelecionado(a);
                gerAl.getSelecionado().setNome("la");
            }
        }
        gerAl.alterarAluno(null);}

    
    
    @Test
    public void testRemoverAlunoSucesso() {
        DataInDB.addAlunos();
        List<Aluno> lista = gerAl.getEp().search(Aluno.class, new CriteriaGroup("eq", "nome", "Claudio Santos", null));
        for (Aluno a : lista) {
            if (a.getNome().equals("Claudio Santos")) {
                gerAl.setSelecionado(a);
                gerAl.removerAluno(null);
            }
        }
        assertEquals("inativo", gerAl.getSelecionado().getEstado());}
}