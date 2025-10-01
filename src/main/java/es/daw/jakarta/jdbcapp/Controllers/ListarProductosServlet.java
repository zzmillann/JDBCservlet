package es.daw.jakarta.jdbcapp.Controllers;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.daw.jakarta.jdbcapp.Model.Producto;
import es.daw.jakarta.jdbcapp.Repository.DBConnection;
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
        try{
            GenericDAO<Producto,Integer> dawP = new ProductoDAO();
            productos = dawP.findAll();

        }catch(SQLException e){
            LOG.severe(e.getMessage());
            request.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }

        request.setAttribute("productos", productos);
        getServletContext().getRequestDispatcher("/informe.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


    }

    @Override
    public void destroy() {
        try{

            DBConnection.closeConnection();


        }catch(SQLException e){

        }
    }
}


