package gerenciamento;

import com.entity.Aluno;
import com.entity.Curso;
import com.entity.Matricula;
import com.entity.Mensalidade;
import com.entity.Nivel;
import com.entity.Turma;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
public class GerenciarMensalidade {
    private GerenciarMatricula gerMat,turmaMat;
    private List<Mensalidade> dados, mensalidades;
    private Mensalidade mensalidade;
    private List<Aluno> aluno;
    private List<Curso> curso;
    private List<Turma> turma;
    private List<Nivel> nivel;
    private Matricula selecionado;
    private EntityPersist ep;
    private List<Matricula> lMat;
    private Gmessages msg = new Gmessages();
    private String busca, param;
    private Object matriculas;

    public GerenciarMensalidade() {
        selecionado = new Matricula();
        ep = new EntityPersist();
        mensalidades = ep.search(Mensalidade.class);
        gerMat = new GerenciarMatricula();
        turmaMat = new GerenciarMatricula();
    }

    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public Matricula getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Matricula selecionado) {
        this.selecionado = selecionado;
    }

    public void setMensalidade(Mensalidade mensalidade) {
        this.mensalidade = mensalidade;
    }

    public EntityPersist getEp() {
        return ep;
    }

    public void setEp(EntityPersist ep) {
        this.ep = ep;
    }

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }

    public Gmessages getMsg() {
        return msg;
    }

    public void setMsg(Gmessages msg) {
        this.msg = msg;
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

    public GerenciarMatricula getGerMat() {
        return gerMat;
    }

    public void setGerMat(GerenciarMatricula gerMat) {
        this.gerMat = gerMat;
    }

    public List<Mensalidade> getDados() {
        return dados;
    }

    public void setDados(List<Mensalidade> dados) {
        this.dados = dados;
    }

    public List<Matricula> getLMat() {
        return lMat;
    }

    public void setLMat(List<Matricula> lMat) {
        this.lMat = lMat;
    }

    public GerenciarMatricula getTurmaMat() {
        return turmaMat;
    }

    public void setTurmaMat(GerenciarMatricula turmaMat) {
        this.turmaMat = turmaMat;
    }

    public enum Mes {

        jan(1),
        fev(2),
        mar(3),
        abr(4),
        mai(5),
        jun(6),
        jul(7),
        ago(8),
        set(9),
        out(10),
        nov(11),
        dez(12);
        public int numMes;

        Mes(int n) {
            numMes = n;
        }
    }

    public void selectMensalidade(ActionEvent ae) throws Exception {
        selecionado = (Matricula) ae.getComponent().getAttributes().get("matricula");

        //Atribuicao extra para linkagem do mês
        retomarSessao();
        lMat = new ArrayList<Matricula>();
        lMat.add(selecionado);
        int mes;
        Calendar hoje = Calendar.getInstance();
        mes = hoje.get(Calendar.MONTH);
        for (Mensalidade m : dados) {
            if ((Mes.valueOf(m.getMes())).numMes <= mes && m.getSituMensalidade().equals("PEN")) {
                m.setSituMensalidade("DEVE");
            }
        }
        Collections.sort(dados);
    }

    public String defineCor(Mensalidade m) {
        if (m.getSituMensalidade().equals("PEN")) {
            return "font-size: 60%";
        }
        if (m.getSituMensalidade().equals("DEVE")) {
            return "color: white;font-size: 60%; background-color: red";
        }
        if (m.getSituMensalidade().equals("OK")) {
            return "color: white;font-size: 60%; background-color: green";
        }
        return "font-size: 60%; background-color: white";
    }

    public String conferePgto(Mensalidade m) {
        if (m.getSituMensalidade().equals("OK")) {
            return "true";
        }
        return "false";
    }

    public void pagamento(ActionEvent ae) {
        Mensalidade m = (Mensalidade) ae.getComponent().getAttributes().get("mes");
        m.setSituMensalidade("OK");
        try {
            ep.update(m);
            retomarSessao();
            msg.pago(null);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMensalidade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultar() {
        if (busca.trim().equals("")) {
            gerMat.setMatriculas(null);
            //mensalidades = ep.search(Mensalidade.class, new CriteriaGroup("ge", "id", 1, null));     
        } else if (param.equals("nome")) {
            List<Aluno> aluno = ep.search(Aluno.class, new CriteriaGroup("eq", "estado", "ativo", null), new CriteriaGroup("eq", param, busca, null));
            if (aluno.isEmpty()) {
                gerMat.setMatriculas(null);
            } else {
                gerMat.setMatriculas(ep.search(Matricula.class, new CriteriaGroup("eq", "aluno", aluno.get(0), null)));
            }
        } else if (param.equals("curso")) {
            List<Curso> curso = ep.search(Curso.class, new CriteriaGroup("eq", "nome", busca, null));
            List<Nivel> nivel = ep.search(Nivel.class, new CriteriaGroup("eq", "curso", curso.get(0), null));
            List<Turma> turma = ep.search(Turma.class, new CriteriaGroup("eq", "nivel", nivel.get(0), null));
            if (turma.isEmpty()) {
                gerMat.setMatricula(null);
            } else {
                gerMat.setMatriculas(ep.search(Matricula.class, new CriteriaGroup("eq", "turma", turma.get(0), null)));
            }
        } else if (param.equals("matricula")) {
            gerMat.setMatriculas(ep.search(Matricula.class, new CriteriaGroup("eq", "id", Integer.parseInt(busca), null)));
        } else if (param.equals("turma")) {
            List<Turma> turma = ep.search(Turma.class, new CriteriaGroup("eq", "turma", busca, null));
            if (turma.isEmpty()) {
                gerMat.setMatricula(null);
            } else {
                gerMat.setMatriculas(ep.search(Matricula.class, new CriteriaGroup("eq", "turma", turma.get(0), null)));
            }
        } else {
            gerMat.setMatricula(null);
        }

    }

    public void retomarSessao() {
        Matricula aux;

        Matricula selecionado2 = (Matricula) ep.mergeObject(selecionado);

        dados = selecionado2.getMensalidade();

        ep.endMerge();
    }

    public void alterar(ActionEvent ae) {
        try {
            ep.update(selecionado);
            msg.alterar(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMensalidade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verTurmaInteira(ActionEvent ae) {
        selecionado = (Matricula) ae.getComponent().getAttributes().get("matricula");
        param = "turma";
        busca = selecionado.getTurma().getTurma();
        consultar();
        
        
    }
}