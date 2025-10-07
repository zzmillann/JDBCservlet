package es.daw.jakarta.jdbcapp.Controllers;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.daw.jakarta.jdbcapp.Model.Fabricante;
import es.daw.jakarta.jdbcapp.Model.Producto;
import es.daw.jakarta.jdbcapp.Repository.DBConnection;
import es.daw.jakarta.jdbcapp.Repository.FabricanteDAO;
import es.daw.jakarta.jdbcapp.Repository.GenericDAO;
import es.daw.jakarta.jdbcapp.Repository.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ListarProductosServlet", value = "/productos/ver")
public class ListarProductosServlet extends HttpServlet {
    private String message;
private static final Logger LOG = Logger.getLogger(ListarProductosServlet.class.getName());
//listar productos
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException , ServletException {

        List<Producto> productos = new ArrayList<>();
        List<Fabricante> fabricantes = new ArrayList<>();
        try{
            GenericDAO<Producto,Integer> dawP = new ProductoDAO();
            GenericDAO<Fabricante,Integer> dawF = new FabricanteDAO();

            productos = dawP.findAll();
            fabricantes = dawF.findAll();
        }catch(SQLException e){
            LOG.severe(e.getMessage());
            request.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }

        request.setAttribute("productos", productos);
        request.setAttribute("fabricantes", fabricantes);
        getServletContext().getRequestDispatcher("/productos.jsp").forward(request, response);

    }





    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {

        Optional<Producto> productos = Optional.empty();
        String codigo = request.getParameter("codigoid") == null ? "" : request.getParameter("codigoid").trim();

        if (codigo.isEmpty() ) {
            request.setAttribute("error", "El código  no puede estar vacíos.");
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }


        try{

            GenericDAO<Producto,Integer> dawP = new ProductoDAO();
            productos = dawP.findById(Integer.parseInt(codigo));

        }catch (SQLException e){
            LOG.severe(e.getMessage());
            request.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("productosId", productos);
        getServletContext().getRequestDispatcher("/informeid.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        try{

            DBConnection.closeConnection();


        }catch(SQLException e){

        }
    }
}


