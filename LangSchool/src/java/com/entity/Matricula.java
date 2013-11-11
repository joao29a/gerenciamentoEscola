package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
        
@Entity
@Table(name = "Matricula")
public class Matricula implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;
    
    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;
    
    @OneToOne
    @JoinColumn(name = "id_nota")
    private Nota notas;
    
    @Column(name = "data_matricula")
    @Temporal(TemporalType.DATE)
    private Date dataMatricula;
    
    @OneToMany(mappedBy = "matricula")
    private Set<Presenca> presenca;
    
    @OneToMany(mappedBy = "matricula")
    private Set<Mensalidade> mensalidade;
    
    @OneToMany(mappedBy = "matricula")
    private Set<ReposicaoAula> reposicoes;
    
    @Column(name="estado")
    private String estado;

    public Matricula() {
        this.estado = "ativo";
    }
    
    public Matricula(Aluno aluno, Turma turma, Date dataMatricula) {
        this.aluno = aluno;
        this.turma = turma;
        this.dataMatricula = dataMatricula;
        this.estado = "ativo";
    }    
    
    public int getId() {
        return id;
    }

    public Nota getNotas() {
        return notas;
    }

    public void setNotas(Nota n) {
        this.notas = n;
    }
    
    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public ArrayList<Mensalidade> getMensalidade() {
        return new ArrayList<Mensalidade>(mensalidade);
    }

    public void setMensalidade(Set<Mensalidade> mensalidade) {
        this.mensalidade = mensalidade;
    }   

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<Presenca> getPresenca() {
        return presenca;
    }

    public Set<ReposicaoAula> getReposicoes() {
        return reposicoes;
    }
}
