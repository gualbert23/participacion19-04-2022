<%-- 
    Document   : editar
    Created on : 19 abr. 2022, 22:05:16
    Author     : Gualbert
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.emergentes.modelo.Libro"%>
<%
    Libro lib = (Libro)request.getAttribute("lib");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>
            <c:if test="${lib.id==0}" var="res" scope="request">
                Registro de un nuevo
            </c:if>

            <c:if test="${lib.id>0}" var="res" scope="request">
                Modificar el 
            </c:if>

            Libro
        </h1>
        <form action="MainController" method="POST">
            <input type="hidden" name="id" value="${lib.id}" />
            <table>
                <tr>
                    <td>Isbn</td>
                    <td><input type="text" name="isbn" value="${lib.isbn}" /></td>
                </tr>
                <tr>
                    <td>Titulo</td>
                    <td><input type="text" name="titulo" value="${lib.titulo}" /></td>
                </tr>
                <tr>
                    <td>Categoria</td>
                    <td><input type="text" name="categoria" value="${lib.categoria}" /></td>
                </tr>
                <tr>
                    <td></td>
                    <td> <input type="submit" /> </td>
                </tr>
            </table>
        </form>
    </body>
</html>
