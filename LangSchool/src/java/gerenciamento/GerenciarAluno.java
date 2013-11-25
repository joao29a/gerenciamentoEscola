package gerenciamento;

import com.entity.Aluno;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import messages.Gmessages;
import org.primefaces.context.RequestContext;

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

    public void setEp(EntityPersist ep) {
        this.ep = ep;
    }

    public EntityPersist getEp() {
        return ep;
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
        boolean valido = true;
        Date d = new Date();
        if (aluno.getNome().equals("") || aluno.getDataNasc() == null
                || aluno.getSexo() == 'x' || aluno.getIdade() == 0
                || aluno.getTelefone().equals("") || aluno.getCidade().equals("")
                || aluno.getEndereco().equals("")) {
            msg.falhaCadastro(null);
            msg.dadosObrig(null);
            valido = false;
        }

        boolean jaexiste = false;
        alunos = ep.search(Aluno.class, new CriteriaGroup("eq", "rg", aluno.getRg(), null));
        if (!(alunos.isEmpty())) {
            jaexiste = true;
        }
        if (aluno.getRg() != null && jaexiste == true) {
            if (valido == true) {
                msg.falhaCadastro(null);
            }
            msg.rgExiste(null);
            valido = false;
        }
        if ((aluno.getNome().length() < 4) || (aluno.getEndereco().length() < 4)
                || (aluno.getNomeMae().length() != 0 && aluno.getNomeMae().length() < 4)
                || (aluno.getNomePai().length() != 0 && aluno.getNomePai().length() < 4)) {
            if (valido == true) {
                msg.falhaCadastro(null);
            }
            msg.tamanhoInsuf(null);
            valido = false;

        }
        if (aluno.getIdade() < 0) {
            if (valido == true) {
                msg.falhaCadastro(null);
            }
            msg.idadeInvalida(null);
            valido = false;

        }//System.out.println(d);System.out.println(aluno.getDataNasc());
        if (aluno.getDataNasc() == null) { //||aluno.getDataNasc().after(d)
            if (valido == true) {
                msg.falhaCadastro(null);
            }
            msg.dataInvalida(null);
            valido = false;
        }
        if (valido) {
            try {
                ep.save(aluno);
                RequestContext.getCurrentInstance().execute("confirmation.show()");
            } catch (Exception ex) {
                Logger.getLogger(GerenciarAluno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void consultarAluno(ActionEvent ae) {
        if (busca.trim().equals("")) {
            alunos = ep.search(Aluno.class, new CriteriaGroup("eq", "estado", "ativo", null));
        } else if (param.equals("nome")) {
            alunos = ep.search(Aluno.class, new CriteriaGroup("eq", param, busca, null),
                    new CriteriaGroup("eq", "estado", "ativo", null));
        } else if (param.equals("estado")) {
            alunos = ep.search(Aluno.class, new CriteriaGroup("eq", "nome", busca, null),
                    new CriteriaGroup("eq", "estado", "inativo", null));
        }
    }

    public void selectAluno(ActionEvent ae) {
        selecionado = (Aluno) ae.getComponent().getAttributes().get("aluno");
        setIsAtivo(!"inativo".equals(selecionado.getEstado()));
    }

    public void alterarAluno(ActionEvent ae) {
        System.out.println(selecionado.getNome());

        try {
            ep.update(selecionado);
            msg.alterar(ae);

        } catch (Exception ex) {
            Logger.getLogger(GerenciarAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerAluno(ActionEvent ae) {
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
        try {
            ep.update(selecionado);
            msg.ativado(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}