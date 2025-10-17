package es.daw.jakarta.jdbcapp.Controllers;

import es.daw.jakarta.jdbcapp.Model.Fabricante;
import es.daw.jakarta.jdbcapp.Model.Producto;
import es.daw.jakarta.jdbcapp.Repository.FabricanteDAO;
import es.daw.jakarta.jdbcapp.Repository.GenericDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet(value = "/fabricantes/borrar")
public class BorrarFabricanteServlet extends HttpServlet {
public static final Logger LOGGER = Logger.getLogger(BorrarFabricanteServlet.class.getName());



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

           String codigo =  req.getParameter("codigo");

            if(codigo == null){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            GenericDAO<Fabricante,Integer> faod = new FabricanteDAO();
            Optional<Fabricante> optional = faod.findById(Integer.parseInt(codigo));

            if(optional.isPresent()){
                faod.delete(optional.get().getCodigo());

                resp.sendRedirect(req.getContextPath()+"/fabricantes/ver");
            }else{
                resp.sendError(HttpServletResponse.SC_NOT_FOUND , "el producto no se ha encontrado");
            }



        }catch (Exception e){
            LOGGER.severe(e.getMessage());
            req.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);

        }
    }
}
