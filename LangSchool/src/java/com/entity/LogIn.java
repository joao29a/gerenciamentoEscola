package com.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Login")
public class LogIn implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    
    @Column(name = "password")
    private String password;
    
    @OneToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;
    
    @Column(name = "hierarquia")
    private int hierarquia;
    
    @Column(name = "estado")
    private String estado;

    public int getId() {
        return id;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    
    public String getEstado() {
        return this.estado;
    }
    
    public void alteraEstado() {
        if(this.estado.equals("inativo")) this.estado = "ativo";
        this.estado = "inativo";
    }
    
}
