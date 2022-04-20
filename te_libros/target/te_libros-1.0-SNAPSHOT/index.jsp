<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : index
    Created on : 19 abr. 2022, 21:57:56
    Author     : Gualbert
--%>
<%@page import="java.util.List"%>
<%@page import="com.emergentes.modelo.Libro"%>
<%
    List<Libro> lista =  (List<Libro>) request.getAttribute("lista");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de Libros</h1>
        <p> <a href="MainController?op=nuevo">Nuevo</a> </p>
        
        <table border="4">
            <tr>
                <th>Id</th>
                <th>Isbn</th>
                <th>Titulo</th>
                <th>Categoria</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="item" items="${lista}">
            
            <tr>
                <td>${item.id}</td>
                <td>${item.isbn}</td>
                <td>${item.titulo}</td>
                <td>${item.categoria}</td>
                <td>
                    <a href="MainController?op=editar&id=${item.id}">Editar</a>
                </td>
                <td>
                    <a href="MainController?op=eliminar&id=${item.id}">Eliminar</a>
                </td>
                
            </tr>
            </c:forEach>
        </table>
    </body>
</html>
