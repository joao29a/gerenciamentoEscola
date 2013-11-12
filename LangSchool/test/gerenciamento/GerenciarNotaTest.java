/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.entity.Matricula;
import com.entity.Turma;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import gerenciamento.GerenciarMatricula;
import gerenciamento.GerenciarNota;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author massao
 */
public class GerenciarNotaTest {
    
    GerenciarNota nota = new GerenciarNota();
    GerenciarMatricula mat = new GerenciarMatricula();
    List<Matricula> lmat, lmatT;
    EntityPersist ep = new EntityPersist();
    Matricula selecionado;
    
    public GerenciarNotaTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        selecionaMat();
        selecionaTurm();
    }
    
    @After
    public void tearDown() {
    }
    
    public void selecionaMat(){
        lmat = ep.search(Matricula.class);
        selecionado = lmat.get(0);
    }
    
    public void selecionaTurm(){
        Turma turmId = selecionado.getTurma();
        lmatT = ep.search(Matricula.class, new CriteriaGroup("eq", "turma", turmId, null));
    }
    
    @Test
    public void tValorNota(){
        boolean resp;
        
        resp = nota.trataNota(selecionado);
        assertEquals("Nota inválida?", true, resp);
    }
    
    @Test
    public void tCalcMed(){
        int n1, n2, n3, n4;
        String med;
        
        System.out.println("Teste do cálculo de média");
        nota.calcularMedia(40, 50, 60, 50);
        nota.getMed();
        assertEquals("A média para 40+50+60+50 = 50", "50", nota.getMed());
    }
    
    @Test
    public void tAltNota(){
        int novaNota;
        
        System.out.println("Testa se altera nota no banco de dados");
        if (selecionado.getNotas().getNota1() != 100){
            novaNota = 100;
        } else novaNota = 100 - selecionado.getNotas().getNota1();
        selecionado.getNotas().setNota1(novaNota);
        nota.setSelecionado(selecionado);
        nota.alterarNota();
        assertEquals("A nova nota em 1 deveria ser" +novaNota, novaNota, nota.getSelecionado().getNotas().getNota1());
    }
    
    @Test
    public void tAltNotaTurm(){
        int novaNota;
        
        System.out.println("Testa se altera a nota do banco de dados da turma inteira");
        for (Matricula k : lmatT){
            if (k.getNotas().getNota1() != 100){
                novaNota = 100;
            }else novaNota = 100 - k.getNotas().getNota1();
            k.getNotas().setNota1(novaNota);
            nota.setSelecionado(k);
            nota.alterarNotaTurm(k);
            assertEquals("A nova nota de 1 deveria ser" +novaNota, novaNota, k.getNotas().getNota1());
        }
        
        
    }
}