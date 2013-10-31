package gerenciamento;

import com.entity.Mensalidade;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import messages.Gmessages;

@ViewScoped
@ManagedBean
public class GerenciarMensalidade {
    private Mensalidade mensalidade, selecionado;
    private EntityPersist ep;
    private List<Mensalidade> mensalidades;
    private Gmessages msg = new Gmessages();
    //private String busca, param;


    public GerenciarMensalidade() {
        mensalidades = ep.search(Mensalidade.class, new CriteriaGroup("gt", "id", 0, null));
    }
    
    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public Mensalidade getSelecionado() {
        return selecionado;
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
    
    public void visualizar() {
        
    }
    
    public void alterar(ActionEvent ae) {
        try {
            ep.update(selecionado);
            msg.alterar(ae);
        } catch (Exception ex) {
            Logger.getLogger(GerenciarMensalidade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void verTurmaInteira() {
        
    }
    
}
