<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelos.Colaborador"%>

<%
    String vNomecolaborador = request.getParameter("nomecolaborador");

    Colaborador col = new Colaborador();
    col.setNomecolaborador(vNomecolaborador);
    
    if (col.IncluirColaborador()) {
        response.sendRedirect("cadastracolaborador.jsp?pmensagem=Colaborador cadastrado com sucesso");
    } else {
        response.sendRedirect("cadastracolaborador.jsp?pmensagem=Problemas ao cadastrar Colaborador");
    }
%>