package es.daw.jakarta.jdbcapp.Controllers;

import es.daw.jakarta.jdbcapp.Model.Fabricante;
import es.daw.jakarta.jdbcapp.Repository.FabricanteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet (value = "/fabricantes/editar")
public class EditarActualizarFabricante extends HttpServlet {
private static final Logger LOG = Logger.getLogger(EditarActualizarFabricante.class.getName());
private FabricanteDAO fabricanteDAO;
    @Override
    public void init() throws ServletException {
        super.init();
        try{
            fabricanteDAO = new FabricanteDAO();

        }catch(Exception e){
            throw new ServletException(e.getMessage());

        }

    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
try{
    String codigo = req.getParameter("codigo");

    Optional<Fabricante> fabric = fabricanteDAO.findById(Integer.parseInt(codigo));
    Fabricante fabricante = fabric.get();
    req.setAttribute("fabricante", fabricante);

    getServletContext().getRequestDispatcher("/formularioFabricantes.jsp").forward(req, resp);

}catch(Exception e){
    throw new ServletException(e.getMessage());
}

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{

           String codigo =  req.getParameter("codigo");
            String nombre = req.getParameter("nombre");

            if(nombre == null || nombre.isEmpty()){
                LOG.severe("El nombre es obligatorio");
                req.setAttribute("error", "El nombre es obligatorio");
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                return;
            }

            if(codigo == null || codigo.isEmpty()) {
                LOG.severe("El codigo es obligatorio");
                req.setAttribute("error", "El codigo es obligatorio");
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                return;

            }
          fabricanteDAO.update(new Fabricante(Integer.parseInt(codigo),nombre));

            resp.sendRedirect(req.getContextPath()+"/fabricantes/ver");



        }catch (SQLException e){

            throw new ServletException(e.getMessage());
        }
    }
}
