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
public class Mensalidade implements Serializable, Comparable<Mensalidade> {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_matricula")
    private Matricula matricula;
    @Column(name = "mes")
    private String mes;
    @Column(name = "situacao")
    private String situMensalidade;

    public Mensalidade() {
    }

    public int getId() {
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

    public Mensalidade(Matricula m, String mes) {
        this.matricula = m;
        this.mes = mes;
        this.situMensalidade = "PEN";
    }

    @Override
    public int compareTo(Mensalidade t) {
        if((Mes.valueOf(this.getMes())).numMes < (Mes.valueOf(t.getMes())).numMes) {
            return -1;
        } else {
            return 1;
        }
    }



    

    public enum Mes {

        jan(1),
        fev(2),
        mar(3),
        abr(4),
        mai(5),
        jun(6),
        jul(7),
        ago(8),
        set(9),
        out(10),
        nov(11),
        dez(12);
        public int numMes;

        Mes(int n) {
            numMes = n;
        }
    }

}