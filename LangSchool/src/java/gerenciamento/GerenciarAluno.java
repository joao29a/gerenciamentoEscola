package gerenciamento;

import com.entity.Aluno;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.sound.midi.Soundbank;
import messages.Gmessages;

@ViewScoped
@ManagedBean
public class GerenciarAluno {

    private Aluno aluno, selecionado;
    private EntityPersist ep;
    private String busca, param;
    private List<Aluno> alunos;
    private boolean isAtivo;
    private Gmessages msg = new Gmessages();

    public GerenciarAluno() {
        System.out.println("Ativado");
        selecionado = new Aluno();
        aluno = new Aluno();
        aluno.setSexo('x');
        ep = new EntityPersist();
        alunos = ep.search(Aluno.class, new CriteriaGroup("eq", "estado", "ativo", aluno));
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Aluno getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Aluno selecionado) {
        this.selecionado = selecionado;
    }

    public boolean isIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public List<Aluno> listar() {
        return ep.search(Aluno.class);
    }

    public void cadastrarAluno() {
        System.out.println("alal");
        if (aluno.getNome().equals("") || aluno.getDataNasc() == null
                || aluno.getSexo() == 'x' || aluno.getIdade().equals("")
                || aluno.getTelefone().equals("") || aluno.getCidade().equals("")) {
            System.out.println("alala1");
            msg.dadosObrig(null);
        } else {
            try {
                ep.save(aluno);
            } catch (Exception ex) {
                Logger.getLogger(GerenciarAluno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void consultarAluno(ActionEvent ae) {
        if (busca.trim().equals("")) {
            alunos = ep.search(Aluno.class, new CriteriaGroup("eq", "estado", "ativo", null));
        } else if (!param.equals("estado")) {
            alunos = ep.search(Aluno.class, new CriteriaGroup("eq", param, busca, null),
                    new CriteriaGroup("eq", "estado", "ativo", null));
        } else {
            alunos = ep.search(Aluno.class, new CriteriaGroup("eq", param, busca, null));
        }
    }

    public void selectAluno(ActionEvent ae) {
        System.out.println("DEU!");
        selecionado = (Aluno) ae.getComponent().getAttributes().get("aluno");
        setIsAtivo(!"inativo".equals(selecionado.getEstado()));
    }

    public void alterarAluno(ActionEvent ae) {
        System.out.println("Alterando");
        try {
            ep.update(selecionado);
            msg.alterar(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerAluno(ActionEvent ae) {
        System.out.println("Removendo");
        selecionado.setEstadoInativo();
        try {
            ep.update(selecionado);
            msg.remover(ae);
            busca = "";
            param = "Nome";
            consultarAluno(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ativar(ActionEvent ae) {
        selecionado.setEstadoAtivo();
        msg.ativado(ae);
        setIsAtivo(false);
    }
}
