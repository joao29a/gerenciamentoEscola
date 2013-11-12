package gerenciamento;

import com.entity.LogIn;
import com.entity.Professor;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import messages.Gmessages;

@ManagedBean
public class GerenciamentoLogin {
    private LogIn login;
    private EntityPersist ep;
    private Gmessages msg = new Gmessages();

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
        String email = login.getLogin();
        String password = login.getPassword();
        
        if (email.equals("") || password.equals("")) {
            msg.falhaLogin(null);
            return "";
        }
        List<LogIn> logged;
        if ((logged = (List<LogIn>)ep.search(LogIn.class, new CriteriaGroup("eq", "login", email, null),
                             new CriteriaGroup("eq", "password", password, null))) == null) {
            msg.falhaLogin(null);
            return "";
        }
        
        String hierarchy = "secretario";
        System.out.println(logged.get(0).getHierarquia() + " Hierarquia");
        if (logged.get(0).getHierarquia() != 1) hierarchy = "professor";
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("authenticated", hierarchy);
        msg.sucessoLogin(null);
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
