package messages;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
public class Gmessages {
    public void alterar(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Alterado com sucesso!", "Pois é"));
    }
    public void remover(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Removido com sucesso!", "Pois é"));
    }
    public void rematricular(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Rematriculado com sucesso!", "Pois é"));
    }
    public void pago(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Pagamento efetuado!", "Pois é"));
    }
    public void estornar(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Estorno efetuado!", "Pois é"));
    }
    public void emitir(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Emitindo...", "Pois é"));
    }
    public void ativado(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Ativado com Sucesso!", "Pois é"));
    }
    public void falhaCadastro(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro no Cadastro!", "Pois é"));
    }
    public void dadosObrig(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Preencha os dados obrigatórios!", "Pois é"));
    }
    public void falhaLogin(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Login inválido. Combinaçao invalida!", "Pois é"));
    }
    public void sucessoLogin(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Logged In!", "Pois é"));
    }
    
}
