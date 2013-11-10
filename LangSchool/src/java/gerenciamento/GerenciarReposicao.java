package gerenciamento;

import com.entity.Matricula;
import com.entity.Professor;
import com.entity.ReposicaoAula;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import messages.Gmessages;
import org.primefaces.context.RequestContext;

@ViewScoped
@ManagedBean
public class GerenciarReposicao {

    private ReposicaoAula reposicao, selecionado;
    private EntityPersist ep;
    private Matricula selectMat = new Matricula();
    private String busca, param;
    private List<ReposicaoAula> reposicoes;
    private boolean isAtivo;
    private GerenciarProfessor professorMan = new GerenciarProfessor();
    private GerenciarMatricula matriculaMan = new GerenciarMatricula();
    private Gmessages msg = new Gmessages();

    public GerenciarReposicao() {
        System.out.println("Ativado");
        selecionado = new ReposicaoAula();
        reposicao = new ReposicaoAula();
        ep = new EntityPersist();
        reposicoes = ep.search(ReposicaoAula.class);
    }

    public ReposicaoAula getReposicao() {
        return reposicao;
    }

    public void setReposicao(ReposicaoAula reposicao) {
        this.reposicao = reposicao;
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

    public List<ReposicaoAula> getReposicoes() {
        return reposicoes;
    }

    public void setReposicoes(List<ReposicaoAula> reposicoes) {
        this.reposicoes = reposicoes;
    }

    public ReposicaoAula getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(ReposicaoAula selecionado) {
        this.selecionado = selecionado;
    }

    public boolean isIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public GerenciarProfessor getProfessorMan() {
        return professorMan;
    }

    public void setProfessorMan(GerenciarProfessor professorMan) {
        this.professorMan = professorMan;
    }

    public GerenciarMatricula getMatriculaMan() {
        return matriculaMan;
    }

    public Matricula getSelectMat() {
        return selectMat;
    }

    public void setSelectMat(Matricula selectMat) {
        this.selectMat = selectMat;
    }

    public void setMatriculaMan(GerenciarMatricula matriculaMan) {
        this.matriculaMan = matriculaMan;
    }

    public void cadastrarReposicaoAula(ActionEvent ae) {
        System.out.println("Cadastrando");
        System.out.println(reposicao.getDataReposicao());
        System.out.println(reposicao.getProfessor().getNome());
        this.reposicao.setMatricula(selectMat);
        try {
            ep.save(reposicao);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarReposicao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String cadastrarReposicaoAulaAction(Matricula selectMat) {
        System.out.println("Cadastrando");
        System.out.println(reposicao.getDataReposicao());
        System.out.println(reposicao.getProfessor().getNome());
        this.reposicao.setMatricula(selectMat);
        try {
            ep.save(reposicao);
            RequestContext.getCurrentInstance().execute("confirmation.show()");
            return "";
        } catch (Exception ex) {
            Logger.getLogger(GerenciarReposicao.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public void consultarReposicaoAula(ActionEvent ae) {
        if (busca.trim().equals("")) {
            reposicoes = ep.search(ReposicaoAula.class);
        } else if (param.equals("nome")) {
            matriculaMan.setBusca(busca);
            matriculaMan.setParam(param);
            matriculaMan.consultarMatricula(ae);
            if (matriculaMan.getMatriculas().isEmpty()) {
                reposicoes.clear();
            } else {
                reposicoes = ep.search(ReposicaoAula.class,
                        new CriteriaGroup("eq", "matricula", matriculaMan.getMatriculas().get(0), null));
            }
        } else {
            reposicoes = ep.search(ReposicaoAula.class, new CriteriaGroup("eq", "professor",
                    (Professor) ep.search(Professor.class, new CriteriaGroup("eq", "nome", busca, null)).get(0), null));
        }
    }

    public void selectMatricula(ActionEvent ae) {
        System.out.println("DEU!");
        selectMat = (Matricula) ae.getComponent().getAttributes().get("mat");
    }

    public void selectReposicaoAula(ActionEvent ae) {
        System.out.println("DEU!");
        selecionado = (ReposicaoAula) ae.getComponent().getAttributes().get("reposicao");
    }

    public void alterarReposicaoAula(ActionEvent ae) {
        System.out.println("Alterando");
        try {
            ep.update(selecionado);
            msg.alterar(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarReposicao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerReposicaoAula(ActionEvent ae) {
        System.out.println("Removendo");
        try {
            ep.delete(selecionado);
            msg.remover(ae);
            busca = "";
            param = "Nome";
            consultarReposicaoAula(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarReposicao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void change(ValueChangeEvent vc) {
        System.out.println("DEU");
    }

    public void consultarMatricula(ActionEvent ae) {
        matriculaMan.setBusca(busca);
        matriculaMan.setParam(param);
        matriculaMan.consultarMatricula(ae);
    }
}
