package com.converter;

import com.entity.Professor;
import gerenciamento.GerenciarProfessor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Professor.class ,value = "professor")
public class ConversorProfessor implements Converter {
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        GerenciarProfessor gerenProf = new GerenciarProfessor();
        if(string != null && !string.trim().equals("")) {
            for(Professor prof : gerenProf.getProfessores()) {
                if(prof.getNome().equals(string))
                    return prof;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        if(obj == null || !(obj instanceof Professor)) 
            return null;
        return ((Professor)obj).getNome();
    }
}