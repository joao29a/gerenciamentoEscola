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
    
    @Column(name = "login")
    private String login;
    
    @OneToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;
    
    @Column(name = "hierarquia")
    private int hierarquia;

    public LogIn(String login, String password, Professor professor, int hierarquia) {
        this.login = login;
        this.password = password;
        this.professor = professor;
        this.hierarquia = hierarquia;
    }
    
    public LogIn() {
        this.professor = new Professor();
    }
    
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public int getHierarquia() {
        return hierarquia;
    }

    public void setHierarquia(int hierarquia) {
        this.hierarquia = hierarquia;
    }
}
