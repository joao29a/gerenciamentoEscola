/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciamento;

import com.entity.Curso;
import com.entity.Matricula;
import com.entity.Nivel;
import com.entity.Nota;
import com.entity.Turma;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import messages.Gmessages;

/**
 *
 * @author massao
 */
@ViewScoped
@ManagedBean
public class GerenciarNota {

    private Nota nota = new Nota();
    private String busca, param, med;
    private Matricula selecionado, seleciona;
    private Turma turmId;
    private List<Matricula> matriculas, alunosTurm;
    private EntityPersist ep = new EntityPersist();
    private GerenciarMatricula busMat = new GerenciarMatricula();
    boolean resp;

    public Nota getNota(){
        return nota;
    }
    public void setNota(Nota nota){
        this.nota = nota;
    }
    
    public String getBusca(){
        return busca;
    }
    public void setBusca(String busca){
        this.busca = busca;
    }
    
    public String getParam(){
        return param;
    }
    public void setParam(String param){
        this.param = param;
    }

    public Matricula getSelecionado(){
        return selecionado;
    }
    public void setSelecionado(Matricula selecionado){
        this.selecionado = selecionado;
    }

    public Matricula getSeleciona(){
        return seleciona;
    }

    public GerenciarMatricula getBusMat(){
        return busMat;
    }
    public void setBusMat(GerenciarMatricula busMat){
        this.busMat = busMat;
    }

    public String getMed(){
        return med;
    }
    public void setMed(String med){
        this.med = med;
    }

    public List<Matricula> getMatriculas(){
        return matriculas;
    }
    public void setMatriculas(List<Matricula> matriculas){
        this.matriculas = matriculas;
    }

    public List<Matricula> getAlunosTurm() {
        return alunosTurm;
    }

    public void setAlunosTurm(List<Matricula> alunosTurm) {
        this.alunosTurm = alunosTurm;
    }
   
    public void selectMatricula(ActionEvent ae){
        selecionado = (Matricula) ae.getComponent().getAttributes().get("matricula");
    }
    
    public void consultarMatricula(ActionEvent ae){
        if (param.equals("nome")){
            //System.out.println("buscando por nome");
            busMat.setBusca(busca);
            busMat.setParam(param);
            busMat.consultarMatricula(ae);
        } else if (param.equals("curso")){
            //System.out.println("buscando por curso");
            if (busca.trim().equals("")){
                matriculas = ep.search(Matricula.class);
            } else {
                List<Curso> curLis = ep.search(Curso.class, new CriteriaGroup("eq", "nome", busca, null));
                if (curLis.isEmpty()){
                    matriculas = null;
                } else {
                    Curso cur = curLis.get(0);
                    List<Nivel> nivel = ep.search(Nivel.class, new CriteriaGroup("eq", param, cur, null));
                    List<Turma> turm = new ArrayList<Turma>();
                    for (Nivel k : nivel){
                    turm.addAll(ep.search(Turma.class, new CriteriaGroup("eq", "nivel", k, null)));
                    }
                    List<Matricula> matCur = new ArrayList<Matricula>();
                    for (Turma k : turm){
                    matCur.addAll(ep.search(Matricula.class, new CriteriaGroup("eq", "turma", k, null)));
                    }
                    matriculas = matCur;
                }
            }    
            busMat.setMatriculas(matriculas);
        } else if (param.equals("turma")){
            //System.out.println("buscando por turma");
            if (busca.trim().equals("")){
                matriculas = ep.search(Matricula.class);
            } else {
                List<Turma> turLis = ep.search(Turma.class, new CriteriaGroup("eq", "turma", busca, null));
                if (turLis.isEmpty()){
                    matriculas = null;
                } else{
                    Turma tur = turLis.get(0);
                    matriculas = ep.search(Matricula.class, new CriteriaGroup("eq", "turma", tur, ae));
                }  
            }
            busMat.setMatriculas(matriculas);
        }
    }

    public void consultarTurma(ActionEvent ae){
        selectTurma(ae);
        turmId = seleciona.getTurma();
        alunosTurm = ep.search(Matricula.class, new CriteriaGroup("eq", "turma", turmId, null));
    }
    
    public void selectTurma(ActionEvent ae){
        seleciona = (Matricula) ae.getComponent().getAttributes().get("matricula");
    }
    
    public boolean trataNota(Matricula mat) {
        if (((mat.getNotas().getNota1()<0) || (mat.getNotas().getNota2()<0) || (mat.getNotas().getNota3()<0) || 
                (mat.getNotas().getNota4()<0)) || ((mat.getNotas().getNota1()>100) || (mat.getNotas().getNota2()>100) ||
                (mat.getNotas().getNota3()>100) || (mat.getNotas().getNota4()>100))){
            return false;
        } else return true;
    }
     
    public String alterarNota(){
        FacesContext c = FacesContext.getCurrentInstance();
        try {
            resp = trataNota(selecionado);
            if (!resp){
                c.addMessage(null, new FacesMessage("Valor inválido para NOTA!"));
            } else {
                calcularMedia(selecionado.getNotas().getNota1(), selecionado.getNotas().getNota2(), selecionado.getNotas().getNota3(), selecionado.getNotas().getNota4());
                selecionado.getNotas().setMedia(med);
                ep.update(selecionado.getNotas());
                Gmessages notif = new Gmessages();
                notif.alterar(null);
            }
            consultarMatricula(null);
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public void alterarNotaTurm(Matricula mat){
        FacesContext c = FacesContext.getCurrentInstance();
        resp = trataNota(mat);
        if (!resp){
            c.addMessage(null, new FacesMessage("Valor inválido para NOTA!"));
        } else {
            calcularMedia(mat.getNotas().getNota1(), mat.getNotas().getNota2(), mat.getNotas().getNota3(), mat.getNotas().getNota4());
            mat.getNotas().setMedia(med);
            try {  
                ep.update(mat.getNotas());
                Gmessages notif = new Gmessages();
                notif.alterar(null); 
            } catch (Exception ex) {
                Logger.getLogger(GerenciarNota.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void todaTurm(List<Matricula> mat){
        for (Matricula k : mat) {
            alterarNotaTurm(k);
        }
        consultarMatricula(null);
    }
    
    public void calcularMedia(int nota1, int nota2, int nota3, int nota4){
        int media;
        media = Math.round((nota1+nota2+nota3+nota4)/4);
        med = Integer.toString(media);
    }    
}
