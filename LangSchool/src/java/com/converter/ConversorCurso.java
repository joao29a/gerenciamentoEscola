package com.converter;

import com.entity.Curso;
import gerenciamento.GerenciarCurso;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Curso.class ,value = "curso")
public class ConversorCurso implements Converter {
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        GerenciarCurso cursoMan = new GerenciarCurso();
        if(string != null && !string.trim().equals("")) {
            for(Curso c : cursoMan.getCursos()) {
                if(c.getNome().equals(string))
                    return c;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || !(o instanceof Curso)) 
            return null;
        return ((Curso)o).getNome();
    }
    
}
