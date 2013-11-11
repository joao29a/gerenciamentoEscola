package com.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Mensalidade")
public class Mensalidade implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    
    @ManyToOne
    @JoinColumn(name="id_matricula")
    private Matricula matricula;
    
    @Column(name = "mes")
    private String mes;
 
    @Column(name = "situacao")
    private String situMensalidade;
    
    public Mensalidade() {
    }
    
    public int getId(){
        return id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getSituMensalidade() {
        return situMensalidade;
    }
    

    public void setSituMensalidade(String situMensalidade) {
        this.situMensalidade = situMensalidade;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
    
    public Mensalidade(Matricula m,String mes) {
        this.matricula = m;
        this.mes = mes;
        this.situMensalidade = "PEN";
    }
   
}