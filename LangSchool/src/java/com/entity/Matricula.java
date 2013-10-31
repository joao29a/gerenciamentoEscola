package com.entity;

import java.io.Serializable;
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
    
    @OneToOne
    @JoinColumn(name = "id_mensalidade")
    private Mensalidade mensalidade;
    
    @OneToMany(mappedBy = "matricula")
    private Set<ReposicaoAula> reposicoes;

    public Matricula() {}
    
    public Matricula(Aluno aluno, Turma turma, Date dataMatricula) {
        this.aluno = aluno;
        this.turma = turma;
        this.dataMatricula = dataMatricula;
    }    
    
    public int getId() {
        return id;
    }

    public Nota getNotas() {
        return notas;
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

    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Mensalidade mensalidade) {
        this.mensalidade = mensalidade;
    }   
}
