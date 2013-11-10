
package gerenciamento;

import com.entity.Turma;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class GerenciarTurma {
    private Turma turma;
    private List<Turma> turmas;
    private EntityPersist ep;
    private GerenciarProfessor gerenProf;
    private GerenciarNivel gerenNivel;
    
    public GerenciarTurma(){
        turma = new Turma();
        ep = new EntityPersist();
        gerenProf = new GerenciarProfessor();
        gerenNivel = new GerenciarNivel();
        turmas = ep.search(Turma.class, 
                new CriteriaGroup("eq","estado","ativo",null));
    }
    
    public void setTurma(Turma turma){
        this.turma = turma;
    }
    
    public Turma getTurma(){
        return this.turma;
    }
    
    public List<Turma> getTurmas(){
        return this.turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }
    
    public GerenciarProfessor getGerenProf(){
        return this.gerenProf;
    }
    
    public void setGerenProf(GerenciarProfessor gerenProf){
        this.gerenProf = gerenProf;
    }
    
    public GerenciarNivel getGerenNivel(){
        return this.gerenNivel;
    }
    
    public void setGerenNivel(GerenciarNivel gerenNivel){
        this.gerenNivel = gerenNivel;
    }
    
    public void adicionarTurma(){
        try {
            ep.save(turma);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void alterarTurma(){
        
    }
    
    public void alterarVagas(){
        
    }
    
    public void consultarTurma(String nome){
        turmas = ep.search(Turma.class, 
                new CriteriaGroup("like", "nome", nome, null));
    }
    
    public void excluirTurma(int id){}
}