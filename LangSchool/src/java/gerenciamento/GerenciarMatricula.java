package gerenciamento;

import com.entity.*;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import messages.Gmessages;
import org.primefaces.context.RequestContext;

@ViewScoped
@ManagedBean
public final class GerenciarMatricula {

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

    public String cadastrarMatricula() {
        if (turma == null || matricula.getAluno() == null) {
            System.out.println("Aqui!");
            msg.dadosObrig(null);
            return "";
        }
        matricula.setTurma(turma);
        if (matricula.getTurma().getVagasRest() == 0) {
            msg.falhaCadastro(null);
            return "";
        }
        cadastrar(matricula, turma);
        RequestContext.getCurrentInstance().execute("confirmation.show()");
        return "";
    }

    public void cadastrar(Matricula matricula, Turma turma) {
        for (Turma t : (List<Turma>) ep.search(Turma.class, new CriteriaGroup("eq", "nivel", turma.getNivel(), null))) {
            for (Matricula m : (List<Matricula>) ep.search(Matricula.class, new CriteriaGroup("eq", "turma", t, null))) {
                if (m.getAluno().getId() == matricula.getAluno().getId()) {
                    return;
                }
            }
        }
        try {
            matricula.setDataMatricula(new Date());
            matricula.setNotas(new Nota());
            ep.save(matricula.getNotas());
            ep.save(matricula);
            turma.setVagasRest(turma.getVagasRest() - 1);
            ep.update(turma);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarMatricula(ActionEvent ae) {
        consultar(param, busca);
    }
    
    public void consultar(String param, String busca) {
        List<Matricula> mAux = new ArrayList<Matricula>();
        if (busca.trim().equals("")) {
            getAllActive();
        } else if (param.equals("nome")) {
            List<Aluno> aluno = ep.search(Aluno.class, new CriteriaGroup("eq", "estado", "ativo", null),
                    new CriteriaGroup("like", param, "%" + busca + "%", null));
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
                if (m.getAluno().getEstado().equals("ativo") && m.getEstado().equals("ativo")) {
                    matriculas.add(m);
                }
            }
        } else if (param.equals("turma")) {
            matriculas.clear();
            for (Turma t : (List<Turma>) ep.search(Turma.class, new CriteriaGroup("like", "turma", "%" + busca + "%", null))) {
                matriculas.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "turma", t, null)));
            }
            for (Matricula m : mAux) {
                if (m.getAluno().getEstado().equals("ativo") && m.getEstado().equals("ativo")) {
                    matriculas.add(m);
                }
            }
        } else if (param.equals("nivel")) {
            matriculas.clear();
            for (Nivel n : (List<Nivel>) ep.search(Nivel.class, new CriteriaGroup("like", "nome", "%" + busca + "%", null))) {
                for (Turma t : (List<Turma>) ep.search(Turma.class, new CriteriaGroup("eq", "nivel", n, null))) {
                    mAux.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "turma", t, null)));
                }
            }
            for (Matricula m : mAux) {
                if (m.getAluno().getEstado().equals("ativo") && m.getEstado().equals("ativo")) {
                    matriculas.add(m);
                }
            }
        } else {
            matriculas = ep.search(Matricula.class, new CriteriaGroup("eq", param, busca, null));
//            matriculas.clear();
//            for (Aluno a : (List<Aluno>) ep.search(Aluno.class, new CriteriaGroup("eq", "estado", busca, null))) {
//                matriculas.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "aluno", a, null)));
//            }
        }
    }

    public void selectMatricula(ActionEvent ae) {
        System.out.println("DEU!");
        selecionado = (Matricula) ae.getComponent().getAttributes().get("matricula");
    }

    public void alterarMatricula(ActionEvent ae) {
        System.out.println("Alterando");
        alterar(selecionado);
        msg.alterar(ae);
    }

    public void alterar(Matricula matricula) {
        try {
            ep.update(matricula);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerMatricula(ActionEvent ae) {
        System.out.println("Removendo");
        msg.remover(ae);
        remover(selecionado);
        busca = "";
        param = "Nome";
        consultarMatricula(ae);
    }

    public void remover(Matricula matricula) {
        try {
            matricula.setEstado("inativo");
            matricula.getTurma().setVagasRest(matricula.getTurma().getVagasRest() + 1);
            ep.update(matricula);
            List list;

            matricula = (Matricula) ep.mergeObject(matricula);
            list = new ArrayList<Presenca>(matricula.getPresenca());
            ep.endMerge();
            for (Presenca p : (List<Presenca>) list) {
                ep.delete(p);
            }

            matricula = (Matricula) ep.mergeObject(matricula);
            list = new ArrayList<ReposicaoAula>(matricula.getReposicoes());
            ep.endMerge();
            for (ReposicaoAula rep : (List<ReposicaoAula>) list) {
                ep.delete(rep);
            }

            matricula = (Matricula) ep.mergeObject(matricula);
            list = matricula.getMensalidade();
            ep.endMerge();
            for (Mensalidade m : (List<Mensalidade>) list) {
                ep.delete(m);
            }
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String selCurso() {
        System.out.println("YOooooo");
        nivelMan.setNiveis(ep.search(Nivel.class, new CriteriaGroup("eq", "curso", curso, null)));
        if (turmaMan.getTurmas() == null) {
            turmaMan.setTurmas(new ArrayList<Turma>());
        } else {
            turmaMan.getTurmas().clear();
        }
        for (Nivel n : nivelMan.getNiveis()) {
            turmaMan.getTurmas().addAll(ep.search(Turma.class, new CriteriaGroup("eq", "nivel", n, null)));
        }
        System.out.println("sizeN: " + nivelMan.getNiveis().size());
        return "";
    }

    public void getAllActive() {
        matriculas.clear();
        for (Aluno a : (List<Aluno>) ep.search(Aluno.class, new CriteriaGroup("eq", "estado", "ativo", null))) {
            matriculas.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "aluno", a, null),
                    new CriteriaGroup("eq", "estado", "ativo", null)));
        }
    }

    public ArrayList<Matricula> getActiveFromList(ArrayList<Matricula> list) {
        ArrayList<Matricula> newL = new ArrayList<Matricula>();
        for (Matricula m : list) {
            if (m.getEstado().equals("ativo") && m.getAluno().getEstado().equals("ativo")) {
                newL.add(m);
            }
        }
        return newL;
    }
}
