package com.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "Curso")
public class Curso implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy="curso")
    private Set<Nivel> niveis;
    
    public Curso() {
        this.estado = "ativo";
    }

    public Curso(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.estado = "ativo";
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

    public String getEstado() {
        return estado;
    }

    public void setEstadoAtivo() {
        this.estado = "ativo";
    }

    public void setEstadoInativo() {
        this.estado = "inativo";
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Curso))
            return false;
        return (((Curso)o).getNome().equals(this.nome));
    }
    
    public String toString(){
        return this.nome;
    }
    
}
