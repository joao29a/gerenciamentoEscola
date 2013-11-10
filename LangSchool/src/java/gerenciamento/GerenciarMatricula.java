package gerenciamento;

import com.entity.*;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import messages.Gmessages;

@ViewScoped
@ManagedBean
public class GerenciarMatricula {

    private Matricula matricula, selecionado;
    private EntityPersist ep;
    private String busca, param;
    private List<Matricula> matriculas;
    private boolean isAtivo;
    private Curso curso = new Curso();
    private Turma turma = new Turma();
    private Nivel nivel = new Nivel();
    private GerenciarAluno alunoMan = new GerenciarAluno();
    private GerenciarTurma turmaMan = new GerenciarTurma();
    private GerenciarCurso cursoMan = new GerenciarCurso();
    private GerenciarNivel nivelMan = new GerenciarNivel();
    private Gmessages msg = new Gmessages();

    public GerenciarMatricula() {
        System.out.println("Ativado");
        selecionado = new Matricula();
        matricula = new Matricula();
        ep = new EntityPersist();

        nivelMan.setNiveis(null);
        turmaMan.setTurmas(null);

        matriculas = new ArrayList<Matricula>();
        getAllActive();
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
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

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Matricula getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Matricula selecionado) {
        this.selecionado = selecionado;
    }

    public boolean isIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public GerenciarAluno getAlunoMan() {
        return alunoMan;
    }

    public void setAlunoMan(GerenciarAluno alunoMan) {
        this.alunoMan = alunoMan;
    }

    public GerenciarTurma getTurmaMan() {
        return turmaMan;
    }

    public void setTurmaMan(GerenciarTurma turmaMan) {
        this.turmaMan = turmaMan;
    }

    public GerenciarCurso getCursoMan() {
        return cursoMan;
    }

    public void setCursoMan(GerenciarCurso cursoMan) {
        this.cursoMan = cursoMan;
    }

    public GerenciarNivel getNivelMan() {
        return nivelMan;
    }

    public void setNivelMan(GerenciarNivel nivelMan) {
        this.nivelMan = nivelMan;
    }

    public void cadastrarMatricula(ActionEvent ae) {
        matricula.setTurma(turma);
        if (matricula.getTurma().getVagas() == 0) {
            msg.falhaCadastro(ae);
            return;
        }
        matricula.setDataMatricula(new Date());
        matricula.setNotas(new Nota());
        try {
            ep.save(matricula.getNotas());
            ep.save(matricula);
            turma.setVagasRest(turma.getVagasRest()-1);
            ep.update(turma);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarMatricula(ActionEvent ae) {
        List<Matricula> mAux = new ArrayList<Matricula>();
        if (busca.trim().equals("")) {
            getAllActive();
        } else if (param.equals("nome")) {
            List<Aluno> aluno = ep.search(Aluno.class, new CriteriaGroup("eq", "estado", "ativo", null),
                    new CriteriaGroup("eq", param, busca, null));
            System.out.println("param: " + param + " busca: " + busca);
            if (aluno.isEmpty()) {
                matriculas.clear();
            } else {
                matriculas = ep.search(Matricula.class,
                        new CriteriaGroup("eq", "aluno", aluno.get(0), null));
            }
            System.out.println("ponto4");
        } else if (param.equals("curso")) {
            matriculas.clear();
            for (Nivel n : (List<Nivel>) ep.search(Nivel.class, new CriteriaGroup("eq", "curso",
                    (Curso) ep.search(Curso.class, new CriteriaGroup("eq", "nome", busca, null)).get(0), null))) {
                for (Turma t : (List<Turma>) ep.search(Turma.class, new CriteriaGroup("eq", "nivel", n, null))) {
                    mAux.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "turma", t, null)));
                }
            }
            for (Matricula m : mAux) {
                if (m.getAluno().getEstado().equals("ativo")) {
                    matriculas.add(m);
                }
            }
        } else if (param.equals("turma")) {
            matriculas.clear();
            for (Turma t : (List<Turma>) ep.search(Turma.class, new CriteriaGroup("like", "turma", busca, null))) {
                matriculas.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "turma", t, null)));
            }
            for (Matricula m : mAux) {
                if (m.getAluno().getEstado().equals("ativo")) {
                    matriculas.add(m);
                }
            }
        } else if (param.equals("estado")) {
            matriculas.clear();
            for (Aluno a : (List<Aluno>) ep.search(Aluno.class, new CriteriaGroup("eq", "estado", busca, null))) {
                matriculas.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "aluno", a, null)));
            }
        }
    }

    public void selectMatricula(ActionEvent ae) {
        System.out.println("DEU!");
        selecionado = (Matricula) ae.getComponent().getAttributes().get("matricula");
    }

    public void alterarMatricula(ActionEvent ae) {
        System.out.println("Alterando");
        try {
            ep.update(selecionado);
            msg.alterar(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerMatricula(ActionEvent ae) {
        System.out.println("Removendo");
        try {
            ep.delete(selecionado);
            msg.remover(ae);
            busca = "";
            param = "Nome";
            consultarMatricula(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selCurso(AjaxBehaviorEvent vc) {
        nivelMan.setNiveis(ep.search(Nivel.class, new CriteriaGroup("eq", "curso", curso, null)));
        for (Nivel n : nivelMan.getNiveis()) {
            turmaMan.setTurmas(ep.search(Turma.class, new CriteriaGroup("eq", "nivel", n, null)));
        }
        System.out.println("sizeN: " + nivelMan.getNiveis().size());
    }

    public void getAllActive() {
        matriculas.clear();
        for (Aluno a : (List<Aluno>) ep.search(Aluno.class, new CriteriaGroup("eq", "estado", "ativo", null))) {
            matriculas.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "aluno", a, null)));
        }
    }
}
    