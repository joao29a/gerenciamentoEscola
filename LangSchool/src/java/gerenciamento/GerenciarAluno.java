package gerenciamento;

import com.entity.Aluno;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

@ManagedBean
public class GerenciarAluno {
    private Aluno aluno = new Aluno();
    private List<Aluno> alunos;
    private EntityPersist ep = new EntityPersist();

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
    
   
    public void cadastrarAluno(ActionEvent ae) {
        System.out.println(aluno.getTelefone());
        System.out.println("Passou por AQUI!");
        try {
            ep.save(aluno);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void consultarAluno(String nome){
        alunos = ep.search(Aluno.class, 
                new CriteriaGroup("like", "nome", nome, null));
    }
}