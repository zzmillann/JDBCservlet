package es.daw.jakarta.jdbcapp.Controllers;

import es.daw.jakarta.jdbcapp.Model.Fabricante;
import es.daw.jakarta.jdbcapp.Repository.FabricanteDAO;
import es.daw.jakarta.jdbcapp.Repository.GenericDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(value = "/fabricantes/ver")
public class ListarFabricanteServlet extends HttpServlet {

   public static final Logger logger = Logger.getLogger(ListarFabricanteServlet.class.getName());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       try{
           List<Fabricante> fabricantes = new ArrayList<>();
           GenericDAO<Fabricante,Integer> gdao = new FabricanteDAO();

           fabricantes = gdao.findAll();


           req.setAttribute("fabricantes", fabricantes);

           getServletContext().getRequestDispatcher("/fabricantes.jsp").forward(req, resp);






       } catch (SQLException e) {
           logger.severe(e.getMessage());
           req.setAttribute("error", e.getMessage());
           getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
       }
    }
}
