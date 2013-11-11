package com.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "Aluno")
public class Aluno implements Serializable {
    
    @Id@GeneratedValue
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "aluno")
    private Set<Matricula> matricula;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "data_nasc")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNasc;
    
    @Column(name = "idade")
    private String idade;
    
    @Column(name = "rg")
    private String rg;
    
    @Column(name = "nome_pai")
    private String nomePai;
    
    @Column(name = "nome_mae")
    private String nomeMae;
    
    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "telefone_pai")
    private String telefonePai;
    
    @Column(name = "telefone_mae")
    private String telefoneMae;
    
    @Column(name = "endereco")
    private String endereco;
    
    @Column(name = "complemento")
    private String complemento;
    
    @Column(name = "uf")
    private String uf;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "cidade")
    private String cidade;
    
    @Column(name = "sexo")
    private char sexo;
    
    public Aluno() {
        estado = "ativo";
    }

    public Aluno(String nome, Date dataNasc, String telefone, String idade, String endereco, String uf, String cidade, char sexo) {
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.idade = idade;
        this.endereco = endereco;
        this.uf = uf;
        this.cidade = cidade;
        this.sexo = sexo;
        this.telefone = telefone;
        this.estado = "ativo";
    }
    
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefonePai() {
        return telefonePai;
    }

    public void setTelefonePai(String telefonePai) {
        this.telefonePai = telefonePai;
    }

    public String getTelefoneMae() {
        return telefoneMae;
    }

    public void setTelefoneMae(String telefoneMae) {
        this.telefoneMae = telefoneMae;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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

    public Set<Matricula> getMatricula() {
        return matricula;
    }

    public void setMatricula(Set<Matricula> matricula) {
        this.matricula = matricula;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
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

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Aluno))
            return false;
        return (((Aluno)o).getNome().equals(this.nome));
    }
    
}