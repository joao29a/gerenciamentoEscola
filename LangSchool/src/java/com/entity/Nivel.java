package com.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Nivel")
public class Nivel implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "carga_horaria")
    private int cargaHoraria;
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public Nivel() {
        curso = new Curso();
    }
    
    public Nivel(String nome, String descricao, int cargaHoraria, Curso curso) {
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.curso = curso;
    }
    
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return this.nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return ((Nivel)o).getNome().equals(this.getNome());
    }
    
    
}