package com.emergentes.controlador;

import com.emergentes.modelo.Libro;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gualbert
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op;
            op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";

            ArrayList<Libro> lista = new ArrayList<Libro>();

            ConexionDB canal = new ConexionDB();

            Connection conn = canal.conectar();
            PreparedStatement ps;
            ResultSet rs;

            if (op.equals("list")) {
                //optener la lista de registros
                //para listar los datos
                String sql = "select * from libros";
                //consulta de seleccion y almacenarlo en una coleccion
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Libro lib = new Libro();
                    lib.setId(rs.getInt("id"));
                    lib.setIsbn(rs.getString("isbn"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setCategoria(rs.getString("categoria"));
                    lista.add(lib);
                    
                }
                request.setAttribute("lista", lista);
                //redireccionar a Index
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            
            if (op.equals("nuevo")) {
                //nuevo registro
                //instanciar un nuevo objeto de la clase Libro
                Libro li = new Libro();
               //System.out.println(li.toString());
                
                request.setAttribute("lib", li);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }
            if (op.equals("eliminar")) {
                //eliminar
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "delete from libros where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                
                response.sendRedirect("MainController");
            }
            if (op.equals("editar")) {
                //editar
                Libro li = new Libro();
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "select * from libros where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs=ps.executeQuery();
                
                while (rs.next()) {
                    
                    li.setId(rs.getInt("id"));
                    li.setIsbn(rs.getString("isbn"));
                    li.setTitulo(rs.getString("titulo"));
                    li.setCategoria(rs.getString("categoria"));
                    lista.add(li);
                    
                }
                
                request.setAttribute("lib", li);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }
            

        } catch (SQLException ex) {
            System.out.println("ERROR AL CONECTAR" + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
        int id = Integer.parseInt(request.getParameter("id"));
        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String categoria = request.getParameter("categoria");
        
        Libro lib = new Libro();
        
        lib.setIsbn(isbn);
        lib.setTitulo(titulo);
        lib.setCategoria(categoria);
        
        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();
        
        PreparedStatement ps;
        
        if(id==0){
            //nuevo registro
            String sql = "insert into libros(isbn,titulo,categoria) values (?,?,?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, lib.getIsbn());
            ps.setString(2, lib.getTitulo());
            ps.setString(3, lib.getCategoria());
            
            ps.executeUpdate();
            response.sendRedirect("MainController");
        }
        else{
            //editar registro
            String sql = "update libros set isbn=?,titulo=?,categoria=? where id=?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, lib.getIsbn());
            ps.setString(2, lib.getTitulo());
            ps.setString(3, lib.getCategoria());
            ps.setInt(4, id);
            
            ps.executeUpdate();
            response.sendRedirect("MainController");
        }
        }catch(SQLException e){
            System.out.println("ERROR de SQL"+e.getMessage());
        }
    }

}
