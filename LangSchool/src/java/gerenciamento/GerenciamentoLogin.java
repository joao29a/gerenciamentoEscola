package gerenciamento;

import com.entity.LogIn;
import com.entity.Professor;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class GerenciamentoLogin {
    private LogIn login;
    private EntityPersist ep;

    public GerenciamentoLogin() {
        login = new LogIn();
        ep = new EntityPersist();
    }

    public LogIn getLogin() {
        return login;
    }

    public void setLogin(LogIn login) {
        this.login = login;
    }
    
    public String autenticar() {
        String email = login.getProfessor().getEmail();
        String password = login.getPassword();
        
        if (email.equals("") || password.equals("")) {
            return "";
        }
        Professor professor = (Professor)ep.search(Professor.class, 
                new CriteriaGroup("eq", "email", email, null)).get(0);
        LogIn logged;
        if ((logged = (LogIn)ep.search(LogIn.class, new CriteriaGroup("eq", "professor", professor, null),
                new CriteriaGroup("eq", "password", password, null)).get(0)) == null)
            return "";
        
        String hierarchy = "secretario";
        if (logged.getHierarquia() != 1) hierarchy = "professor";
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("authenticated", hierarchy);
        return "/faces/index.xhtml";
    }
    
    public String getLoggedName() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authenticated") == null)
            return "Desconhecido";
        else
            return (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authenticated");
    }
    
    public boolean isLogged() {
        String nome = getLoggedName();
        if (nome.equals("Desconhecido"))
            return false;
        return true;
    }
    
    public boolean isNotLogged() {
        return !isLogged();
    }
    
    public String goLogIn() {
        return "/faces/login.xhtml";
    }
    
    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("authenticated");
        return goLogIn();
    }
}
