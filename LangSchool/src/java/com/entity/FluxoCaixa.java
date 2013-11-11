
package com.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "FluxoCaixa")
public class FluxoCaixa {
    @Id@GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "valor")
    private float valor;
    
    @Column(name = "data_movimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    
    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "situacao")
    private String situacao = "OK";
    

    public FluxoCaixa(String descricao, float valor, Date data, String tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }
    public FluxoCaixa(){}

    
    public int getId() {
        return id;
    }

    public void setCodFluxo(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
}