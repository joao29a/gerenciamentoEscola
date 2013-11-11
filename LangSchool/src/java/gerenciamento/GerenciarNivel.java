/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciamento;

import com.entity.Curso;
import com.entity.Nivel;
import com.entity.Turma;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author lucas
 */
@ViewScoped
@ManagedBean
public class GerenciarNivel {

    private Nivel nivel;
    private Nivel nivelEscolhido;
    private List<Nivel> niveis;
    private EntityPersist ep;
    private String searchParam;
    private String searchValue;
    private List<Curso> cursos;

    public GerenciarNivel() {
        nivel = new Nivel();
        nivelEscolhido = new Nivel();
        ep = new EntityPersist();
        searchParam = "nome";
        searchValue = "";
        niveis = ep.searchOrderBy(Nivel.class, searchParam);
        cursos = ep.searchOrderBy(Curso.class, "nome");
    }

    public void escolherNivel(ActionEvent ae) {
        this.nivelEscolhido = (Nivel) ae.getComponent().getAttributes().get("nivel");
    }

    public Nivel getNivelEscolhido() {
        return nivelEscolhido;
    }

    public void setNivelEscolhido(Nivel nivelEscolhido) {
        this.nivelEscolhido = nivelEscolhido;
    }

    public List<Nivel> getNiveis() {
        return niveis;
    }

    public void setNiveis(List<Nivel> niveis) {
        this.niveis = niveis;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public void cadastrarNivel(ActionEvent ae) {

        try {
            FacesContext c = FacesContext.getCurrentInstance();
            if((nivel.getNome()!=null)&&(!nivel.getNome().equals(""))){
                c.addMessage(null, new FacesMessage("Nivel cadastrado com sucesso!"));
                ep.save(nivel);
                nivel = new Nivel();
            }else{
                c.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha os campos obrigatórios!",""));
            }
           
        } catch (Exception e) {
        }
    }

    public void removerNivel(ActionEvent ae) {
        try {
            FacesContext c = FacesContext.getCurrentInstance();
             
            List aux=ep.search(Turma.class, new CriteriaGroup("eq", "nivel", nivelEscolhido,null));
            if(aux.isEmpty()){
                ep.delete(nivelEscolhido);
                consultarNivel(ae);
                c.addMessage(null, new FacesMessage("Nivel removido com Sucesso!"));
            }else{
                c.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na remoção. O Nivel possui turmas.",""));
            }
            
        } catch (Exception e) {
        }
    }

    public void atualizarNivel(ActionEvent ae) {
        try {
            FacesContext c = FacesContext.getCurrentInstance();
            if((nivelEscolhido.getNome()!=null)&&(!nivelEscolhido.getNome().equals(""))){
                ep.update(nivelEscolhido);
                c.addMessage(null, new FacesMessage("Nivel alterado com Sucesso!"));
                RequestContext.getCurrentInstance().execute("dlA.hide()");
                
            }else{    
                c.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha os campos obrigatórios!",""));
            }
            consultarNivel(ae);
        } catch (Exception e) {
        }
    }

    public void consultarNivel(ActionEvent ae) {
        if (searchParam.equals("curso")) {
            niveis = ep.searchInnerJoin(Nivel.class, searchValue);
        } else {
            niveis = ep.searchOrderBy(Nivel.class, searchParam,
                    new CriteriaGroup("like", searchParam, "%" + searchValue + "%", null));
        }

    }
}
