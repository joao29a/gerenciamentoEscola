/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciamento;

import com.entity.Curso;
import com.entity.Nivel;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author lucas
 */
@ViewScoped
@ManagedBean
public class GerenciarCurso {
    
    private Curso curso;
    private Curso cursoEscolhido;
    private List<Curso> cursos;
    private EntityPersist ep;
    private String searchParam;
    private String searchValue;
    
    public GerenciarCurso() {
        curso = new Curso();
        cursoEscolhido = new Curso();
        ep = new EntityPersist();
        searchParam = "nome";
        searchValue = "";
        cursos = ep.searchOrderBy(Curso.class, searchParam);
    }

    public Curso getCursoEscolhido() {
        return cursoEscolhido;
    }

    public void setCursoEscolhido(Curso cursoEscolhido) {
        this.cursoEscolhido = cursoEscolhido;
    }
    
    public void escolherCurso(ActionEvent ae) {
        this.cursoEscolhido = (Curso) ae.getComponent().getAttributes().get("curso");
    }
    
    public void atualizarForm(ActionEvent ae){
        consultarCurso(ae);
    }
    
    public String getSearchValue() {
        return searchValue;
    }
    
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    
    public String getSearchParam() {
        return searchParam;
    }
    
    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }
    
    public Curso getCurso() {
        return curso;
    }
    
    public List<Curso> getCursos() {
        return cursos;
    }
    
    public void cadastrarCurso(ActionEvent ae) throws Exception {
        try {
            FacesContext c = FacesContext.getCurrentInstance();
            if((curso.getNome()!=null)&&(!curso.getNome().equals(""))){
                c.addMessage(null, new FacesMessage("Curso cadastrado com sucesso!"));
                ep.save(curso);
                curso = new Curso();
            }else{
                c.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha os campos obrigatórios!",""));
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void removerCurso(ActionEvent ae) {
        FacesContext c = FacesContext.getCurrentInstance();
        try {
            List aux=ep.search(Nivel.class, new CriteriaGroup("eq", "curso", cursoEscolhido,null));
            if(aux.isEmpty()){
                ep.delete(cursoEscolhido);
                consultarCurso(ae);
                c.addMessage(null, new FacesMessage("Curso removido com Sucesso!"));
            }else{
                c.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na remoção. O curso possui niveis.",""));
            }
            
            
           
        }  catch (Exception ex) {
           
        } 
    }
    
    public void atualizarCurso(ActionEvent ae) throws Exception {
        try {
            FacesContext c = FacesContext.getCurrentInstance();
            if((cursoEscolhido.getNome()!=null)&&(!cursoEscolhido.getNome().equals(""))){
                ep.update(cursoEscolhido);
                c.addMessage(null, new FacesMessage("Curso alterado com Sucesso!"));
                RequestContext.getCurrentInstance().execute("dlA.hide()");
                
            }else{    
                c.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha os campos obrigatórios!",""));
            }
            consultarCurso(ae);
            
        } catch (Exception e) {
        }
    }
    
    public void consultarCurso(ActionEvent ae) {
      
        cursos = ep.searchOrderBy(Curso.class, searchParam,
                new CriteriaGroup("like", searchParam, "%" + searchValue + "%", null));
            
    }
}
