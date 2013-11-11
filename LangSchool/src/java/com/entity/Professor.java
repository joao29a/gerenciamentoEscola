
package com.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Professor")
public class Professor implements Serializable {
    @Id 
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "rg")
    private String rg;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "uf")
    private String uf;
    
    @Column(name = "cidade")
    private String cidade;
    
    @Column(name = "sexo")
    private char sexo;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "endereco")
    private String endereco;
    
    public Professor(){
        this.estado = "ativo";
    }

    public Professor(String nome, String email, String rg, String telefone, String idade, 
            String endereco, String uf, String cidade, char sexo) {
        this.nome = nome;
        this.email = email;
        this.rg = rg;
        this.endereco = endereco;
        this.uf = uf;
        this.cidade = cidade;
        this.sexo = sexo;
        this.telefone = telefone;
        this.estado = "ativo";
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }
    
     public void setEstadoAtivo() {
        this.estado = "ativo";
    }
    
    public void setEstadoInativo() {
        this.estado = "inativo";
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    
    public String getTelefone(){
        return this.telefone;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public String getEstado(){
        return this.estado;
    }
    
    
    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
    
    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Professor))
            return false;
        return (((Professor)o).getNome().equals(this.nome));
    }
}