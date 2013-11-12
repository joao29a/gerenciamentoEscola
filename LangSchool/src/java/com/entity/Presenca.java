/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author massao
 */
@Entity
@Table(name = "Presenca")
public class Presenca implements Serializable {
    
    @Id
    @GeneratedValue
    private int id;
    
    @Column(name = "dia")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dia;
    
    @ManyToOne
    @JoinColumn(name = "id_matricula")
    private Matricula matricula;
    
    @Column(name = "estado")
    private boolean estado;

    public Presenca() {
    }
    
    public Presenca(Date data, Boolean pres){
        this.dia = data;
        this.estado = pres;
    }
    
    public int getId(){
        return id;
    }
    
    public Date getDia(){
        return dia;
    }
    
    public String getDiaFormated(){
        DateFormat dt_format = new SimpleDateFormat("dd/MM");
        return dt_format.format(dia);
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
    
    public void setDia(Date dia){
        this.dia = dia;
    }
    
    public boolean getEstado(){
        return estado;
    }
    
    public void setEstado(Boolean estado){
        this.estado = estado;
    }
        
}
