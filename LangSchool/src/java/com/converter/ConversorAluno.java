package com.converter;

import com.entity.Aluno;
import gerenciamento.GerenciarAluno;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Aluno.class ,value = "aluno")
public class ConversorAluno implements Converter {
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        GerenciarAluno alunoMan = new GerenciarAluno();
        if(string != null && !string.trim().equals("")) {
            for(Aluno c : alunoMan.getAlunos()) {
                if(c.getNome().equals(string))
                    return c;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || !(o instanceof Aluno)) 
            return null;
        return ((Aluno)o).getNome();
    }
    
}
