package gerenciamento;

import com.entity.FluxoCaixa;
import com.persist.EntityPersist;
import com.util.CriteriaGroup;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import messages.Gmessages;

@ViewScoped
@ManagedBean
public class GerenciarCaixa {
    private FluxoCaixa fluxo = new FluxoCaixa();
    private List<FluxoCaixa> fluxos;
    private float buscavalor;
    private String nomecriterio;
    private Date data1,data2;
    private String param;
    private EntityPersist ep = new EntityPersist();
    
    public GerenciarCaixa() {
        fluxos = ep.search(FluxoCaixa.class);
    }

    public FluxoCaixa getFluxo() {
        return fluxo;
    }

    public void setFluxo(FluxoCaixa fluxo) {
        this.fluxo = fluxo;
    }

    public List<FluxoCaixa> getFluxos() {
        return fluxos;
    }

    public void setFluxos(List<FluxoCaixa> fluxos) {
        this.fluxos = fluxos;
    }

    public float getBuscavalor() {
        return buscavalor;
    }

    public void setBuscavalor(float buscavalor) {
        this.buscavalor = buscavalor;
    }

    public String getNomecriterio() {
        return nomecriterio;
    }

    public void setNomecriterio(String nomecriterio) {
        this.nomecriterio = nomecriterio;
    }

    public Date getData1() {
        return data1;
    }

    public void setData1(Date data1) {
        this.data1 = data1;
    }

    public Date getData2() {
        return data2;
    }

    public void setData2(Date data2) {
        this.data2 = data2;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    
    public void cadastrarFluxo() {
        try {
            ep.save(fluxo);
        } catch(Exception ex) {
            Logger.getLogger(GerenciarCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void consultar() {
        if (param.equals("valor")) {
            this.fluxos = ep.search(FluxoCaixa.class, new CriteriaGroup(nomecriterio, param, buscavalor, null));
        } else if (param.equals("data")) {
            nomecriterio="bt";
            this.fluxos = ep.search(FluxoCaixa.class, new CriteriaGroup(nomecriterio, param, data1, data2));    
        }
        System.out.println(fluxos);
    }
    
}
