package com.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Mensalidade")
public class Mensalidade implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    
    @Column(name = "mes")
    private String[] mes = new String[12];
 
    @Column(name = "situacao")
    private boolean situMensalidade;
    
    public int getId(){
        return id;
    }
    
    public boolean getSituMensalidade(){
        return situMensalidade;
    }
    
    public void setSituMensalidade(boolean situMensalidade) {
        this.situMensalidade = situMensalidade;
    }

    public String[] getMes() {
        return mes;
    }

    public void setMes(String[] mes) {
        this.mes = mes;
    }
   
}