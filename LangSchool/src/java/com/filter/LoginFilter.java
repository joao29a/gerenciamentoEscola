
package com.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        
        String url = req.getRequestURL().toString();
        String hierarchy = (String)session.getAttribute("authenticated");
        //chain.doFilter(request, response);
        if(hierarchy == null && 
                !url.contains("login.xhtml")) {
            resp.sendRedirect(req.getContextPath()+ "/faces/login.xhtml");
        } else if (hierarchy != null && hierarchy.equals("professor")) {
            if (url.matches(".*(aluno|caixa|curso|documentos|imagens|matricula|"
                + "mensalidade|nivel|professor|turma).*"))
                resp.sendRedirect(req.getContextPath()+ "/faces/index.xhtml");
            else
                chain.doFilter(request, response);
        } else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}
