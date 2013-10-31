package gerenciamento;

import com.entity.LogIn;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class GerenciamentoLogin {
    private LogIn login;

    public LogIn getLogin() {
        return login;
    }

    public void setLogin(LogIn login) {
        this.login = login;
    }
    
    public String autenticar() {
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("authenticated", true);
        return "/faces/index.xhtml";
    }
}
