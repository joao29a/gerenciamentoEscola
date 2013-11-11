package com.converter;

import com.entity.Turma;
import gerenciamento.GerenciarTurma;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Turma.class ,value = "turma")
public class ConversorTurma implements Converter { 
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        GerenciarTurma turmaMan = new GerenciarTurma();
        if(string != null && !string.trim().equals("")) {
            for(Turma c : turmaMan.getTurmas()) {
                if(c.getTurma().equals(string))
                    return c;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || !(o instanceof Turma)) 
            return null;
        return ((Turma)o).getTurma();
    }
    
}
