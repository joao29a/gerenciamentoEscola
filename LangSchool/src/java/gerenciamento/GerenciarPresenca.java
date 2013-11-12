/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciamento;

import com.entity.Aluno;
import com.entity.Curso;
import com.entity.Matricula;
import com.entity.Nivel;
import com.entity.Presenca;
import com.entity.Turma;
import com.hibernate.HibernateUtil;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import messages.Gmessages;
import org.hibernate.Hibernate;
import org.hibernate.Session;

/**
 *
 * @author massao
 */
@ViewScoped
@ManagedBean
public class GerenciarPresenca {

    private Presenca presenca;
    private String busca, param;
    private Matricula selecionado, seleciona;
    private Turma turmId;
    private List<Matricula> matriculas, alunosTurm;
    private EntityPersist ep = new EntityPersist();
    private GerenciarMatricula busMat = new GerenciarMatricula();
    private List<Presenca> Lpres, Lpresturm;
    private boolean estado, presente;
    private Date data, dPadrao;
    private DateFormat dt_format = new SimpleDateFormat("dd/MM");
    private ArrayList<ArrayList<Presenca>> matArr = new ArrayList<ArrayList<Presenca>>();
    private Session ses;
    private List<Integer> ind = new ArrayList<Integer>();
    private List<Integer> qtur = new ArrayList<Integer>();


    public Presenca getPresenca() {
        return presenca;
    }

    public void setPresenca(Presenca presenca) {
        this.presenca = presenca;
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

    public GerenciarMatricula getBusMat() {
        return busMat;
    }

    public void setBusMat(GerenciarMatricula busMat) {
        this.busMat = busMat;
    }

    public Matricula getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Matricula selecionado) {
        this.selecionado = selecionado;
    }

    public Matricula getSeleciona() {
        return seleciona;
    }

    public void setSeleciona(Matricula seleciona) {
        this.seleciona = seleciona;
    }

    public Turma getTurmId() {
        return turmId;
    }

    public void setTurmId(Turma turmId) {
        this.turmId = turmId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Matricula> getAlunosTurm() {
        return alunosTurm;
    }

    public void setAlunosTurm(List<Matricula> alunosTurm) {
        this.alunosTurm = alunosTurm;
    }

    public List<Presenca> getLpres() {
        return Lpres;
    }

    public void setLpres(List<Presenca> Lpres) {
        this.Lpres = Lpres;
    }

    public Date getdPadrao() {
        return dPadrao;
    }

    public void setdPadrao(Date dPadrao) {
        this.dPadrao = dPadrao;
    }

    public DateFormat getDt_format() {
        return dt_format;
    }

    public void setDt_format(DateFormat dt_format) {
        this.dt_format = dt_format;
    }

    public List<Presenca> getLpresturm() {
        return Lpresturm;
    }

    public void setLpresturm(List<Presenca> Lpresturm) {
        this.Lpresturm = Lpresturm;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Integer> getInd() {
        return ind;
    }

    public void setInd(List<Integer> ind) {
        this.ind = ind;
    }

    public List<Integer> getQtur() {
        return qtur;
    }

    public void setQtur(List<Integer> qtur) {
        this.qtur = qtur;
    }

    public Session getSes() {
        return ses;
    }

    public void setSes(Session ses) {
        this.ses = ses;
    }
    
    

    public void consultarMatricula(ActionEvent ae) {
        if (param.equals("nome")) {
            busMat.setBusca(busca);
            busMat.setParam(param);
            busMat.consultarMatricula(ae);
        } else if (param.equals("curso")) {
            System.out.println("buscando por curso");
            if (busca.trim().equals("")) {
                matriculas = ep.search(Matricula.class);
            } else {
                List<Curso> curLis = ep.search(Curso.class, new CriteriaGroup("eq", "nome", busca, null));
                if (curLis.isEmpty()) {
                    matriculas = null;
                } else {
                    Curso cur = curLis.get(0);
                    List<Nivel> nivel = ep.search(Nivel.class, new CriteriaGroup("eq", param, cur, null));
                    List<Turma> turm = new ArrayList<Turma>();
                    for (Nivel k : nivel) {
                        turm.addAll(ep.search(Turma.class, new CriteriaGroup("eq", "nivel", k, null)));
                    }
                    List<Matricula> matCur = new ArrayList<Matricula>();
                    for (Turma k : turm) {
                        matCur.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "turma", k, null)));
                    }
                    matriculas = matCur;
                }
            }
            busMat.setMatriculas(matriculas);
        } else if (param.equals("turma")) {
            System.out.println("buscando por turma");
            if (busca.trim().equals("")) {
                matriculas = ep.search(Matricula.class);
            } else {
                List<Turma> turLis = ep.search(Turma.class, new CriteriaGroup("eq", "turma", busca, null));
                if (turLis.isEmpty()) {
                    matriculas = null;
                } else {
                    Turma tur = turLis.get(0);
                    matriculas = ep.search(Matricula.class, new CriteriaGroup("eq", "turma", tur, ae));
                }
            }
            busMat.setMatriculas(matriculas);
        }
    }

    public void selectMatricula(ActionEvent ae) {
        ind.clear();
        selecionado = (Matricula) ae.getComponent().getAttributes().get("matricula");
        System.out.println("SELECIONADO ==" + selecionado.getAluno().getNome());
        System.out.println("listando presença");
        selecionado = (Matricula) ep.mergeObject(selecionado);
        System.out.println("presenca:" + selecionado.getPresenca());
        Lpres = new ArrayList<Presenca>(selecionado.getPresenca());
        ind.add(1);
        ep.endMerge();
    }

    public String consultarTurma(Matricula mat) {
        ind.clear();
        seleciona = mat;
        Matricula aux;
        turmId = seleciona.getTurma();
        alunosTurm = ep.search(Matricula.class, new CriteriaGroup("eq", "turma", turmId, null));
        for (Matricula k : alunosTurm) {
            aux = (Matricula) ep.mergeObject(k);
            matArr.add(aux.getPresencaList());
            System.out.println("TAMANHOA MATARR " +aux.getPresencaList());
            ep.endMerge();
        }
        System.out.println("TAMANHOA MATARRaaaaaa " +matArr.size());
        ind.add(1);
        return "";
    }

    public ArrayList<Presenca> getArrTurma(Matricula mat) {
        System.out.println("Passando");
        ArrayList<Presenca> nullP = null;
        for (ArrayList<Presenca> p : matArr) {
            if (p != null && !p.isEmpty() && p.get(0).getMatricula().getId() == mat.getId()) {
                System.out.println("PPPP" +p);
                return p;
            }
            if (p.isEmpty()) {
                nullP = p;
            }
        }
        System.out.println("axo q siu nullP");
        return nullP;
    }

    public String adicionarPresencaTurm(Matricula mat){
        turmId = mat.getTurma();
        List<Matricula> lmat = ep.search(Matricula.class, new CriteriaGroup("eq", "turma", turmId, null));
        for (Matricula k: lmat){
            Presenca presen = new Presenca(data, false);
            presen.setMatricula(k);
            try {
                ep.update(k);
                ep.save(presen);
            } catch (Exception ex) {
                Logger.getLogger(GerenciarPresenca.class.getName()).log(Level.SEVERE, null, ex);
            
            }
        }
        return "";
    }
    
//    public String adicionarPresencaTurm() {
//        System.out.println("HAHAHAHAHAHAH");
//        for (Matricula k : alunosTurm) {
//            Presenca nPres = new Presenca(data, estado);
//            System.out.println("Dia: " + nPres.getDia() + " " + data);
//            nPres.setMatricula(k);
//            try {
//                ep.update(k);
//                ep.save(nPres);
//            } catch (Exception ex) {
//                Logger.getLogger(GerenciarPresenca.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return "";
//    }

    public void msgEstado() {
        String mess = presente ? "Presente" : "Ausente";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mess));
    }

    public void alterarPresTurm(Matricula mat) {
        System.out.println("SALVANDO");
        for (Presenca p : mat.getPresenca()) {
            try {
                ep.update(p);
            } catch (Exception ex) {
                Logger.getLogger(GerenciarPresenca.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ses.close();
    }

    public String todaTurm2() {
        System.out.println("Passando...");
        for (ArrayList<Presenca> ar1 : matArr) {
            for (Presenca ar2 : ar1) {
                try {
                    ep.update(ar2);
                } catch (Exception ex) {
                    Logger.getLogger(GerenciarPresenca.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "";
    }

    public String todaTurm(List<Matricula> mat) {
        System.out.println("COMEÇANDO A SALVAAR");
        for (Matricula k : mat) {
            alterarPresTurm(k);
        }
        consultarMatricula(null);
        Gmessages notif = new Gmessages();
        notif.alterar(null);
        return "";
    }
    
}
