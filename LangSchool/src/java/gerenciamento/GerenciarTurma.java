package gerenciamento;

import com.entity.Curso;
import com.entity.Nivel;
import com.entity.Turma;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import messages.Gmessages;

@ViewScoped
@ManagedBean
public class GerenciarTurma {

    private String busca, param;
    private boolean isAtivo;
    private Gmessages msg = new Gmessages();
    private Turma turma, selecionado;
    private List<Turma> turmas;
    private EntityPersist ep;
    private GerenciarProfessor gerenProf;
    private GerenciarCurso gerenCurso;

    public GerenciarTurma() {
        System.out.println("Ativado");
        selecionado = new Turma();
        turma = new Turma();
        ep = new EntityPersist();
        gerenProf = new GerenciarProfessor();
        gerenCurso = new GerenciarCurso();
        turmas = ep.search(Turma.class,
                new CriteriaGroup("eq", "estado", "ativo", turma));
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
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

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public List<Turma> getTurmas() {
        return this.turmas;
    }

    public GerenciarProfessor getGerenProf() {
        return this.gerenProf;
    }

    public void setGerenProf(GerenciarProfessor gerenProf) {
        this.gerenProf = gerenProf;
    }

    public GerenciarCurso getGerenCurso() {
        return this.gerenCurso;
    }

    public void setGerenCurso(GerenciarCurso gerenNivel) {
        this.gerenCurso = gerenNivel;
    }

    public Turma getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Turma selecionado) {
        this.selecionado = selecionado;
    }

    public boolean isIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public List<Turma> listar() {
        return ep.search(Turma.class);
    }

    public void cadastrarTurma(ActionEvent ae) {
        try {
            turma.setVagasRest(turma.getVagas());
            ep.save(turma);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarTurma(ActionEvent ae) {
        if (busca.trim().equals("")) {
            turmas = ep.search(Turma.class, new CriteriaGroup("eq", "estado", "ativo", null));
        } else if (param.equals("curso")) {
            turmas.clear();
            for (Curso c : (List<Curso>) ep.search(Curso.class, new CriteriaGroup("like", "nome", "%" + busca + "%", null))) {
                for (Nivel n : (List<Nivel>) ep.search(Nivel.class, new CriteriaGroup("eq", "curso", c, null))) {
                    turmas.addAll(ep.search(Turma.class, new CriteriaGroup("eq", "nivel", n, null)));
                }
            }
        } else if (param.equals("nivel")) {
            turmas.clear();
            for (Nivel n : (List<Nivel>) ep.search(Nivel.class, new CriteriaGroup("like", "nome", "%" + busca + "%", null))) {
                turmas.addAll(ep.search(Turma.class, new CriteriaGroup("eq", "nivel", n, null)));
            }
        } else if (!param.equals("estado")) {
            turmas = ep.search(Turma.class, new CriteriaGroup("eq", param, busca, null),
                    new CriteriaGroup("eq", "estado", "ativo", null));
        } else {
            turmas = ep.search(Turma.class, new CriteriaGroup("eq", param, busca, null));
        }
    }

    public void selectTurma(ActionEvent ae) {
        selecionado = (Turma) ae.getComponent().getAttributes().get("turma");
        setIsAtivo(!"inativo".equals(selecionado.getEstado()));
    }

    public void alterarTurma(ActionEvent ae) {
        System.out.println("Alterando");
        try {
            ep.update(selecionado);
            msg.alterar(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerTurma(ActionEvent ae) {
        System.out.println("Removendo");
        selecionado.setEstadoInativo();
        try {
            ep.update(selecionado);
            msg.remover(ae);
            busca = "";
            param = "Nome";
            consultarTurma(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ativar(ActionEvent ae) {
        selecionado.setEstadoAtivo();
        msg.ativado(ae);
        setIsAtivo(false);
    }
}
