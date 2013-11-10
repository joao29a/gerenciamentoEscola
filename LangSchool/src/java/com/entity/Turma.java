/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author john
 */
@Entity
@Table(name = "Turma")
public class Turma implements Serializable {
    @Id 
    @GeneratedValue
    @Column (name = "id")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;
   
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;
    
    @Column (name = "turma")
    private String turma;
    
    @Column (name = "descricao")
    private String descricao;
    
    @Column (name = "vagas")
    private int vagas;
    
    @Column (name = "vagas_rest")
    private int vagasRest;
    
    @Column(name = "estado")
    private String estado;
    
    public Turma(){
        this.estado = "ativo";
    }

    public Turma(Professor professor, Curso curso, String turma, String descricao, int vagas, int vagasRest) {
        this.professor = professor;
        this.curso = curso;
        this.turma = turma;
        this.descricao = descricao;
        this.vagas = vagas;
        this.vagasRest = vagasRest;
        this.estado = "ativo";
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    
    public void setCurso(Curso curso){
        this.curso = curso;
    }
    
    public Curso getCurso(){
        return this.curso;
    }
    
    public void setVagasRest(int rest){
        this.vagasRest = rest;
    }
    
    public int getVagasRest(){
        return this.vagasRest;
    }
    
    public void setProfessor(Professor professor){
        this.professor = professor;
    }
    
    public Professor getProfessor(){
        return this.professor;
    }
    
    public void setTurma(String turma){
        this.turma = turma;
    }
    
    public String getTurma(){
        return this.turma;
    }
    
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    
    public String getDescricao(){
        return this.descricao;
    }
    
    public void setVagas(int vagas){
        this.vagas = vagas;
    }
    
    public int getVagas(){
        return this.vagas;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public String getEstado(){
        return this.estado;
    }
    
    public void setEstadoAtivo() {
        this.estado = "ativo";
    }
    
    public void setEstadoInativo() {
        this.estado = "inativo";
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Turma))
            return false;
        return (((Turma)o).getTurma().equals(this.turma));
    }
    
}
