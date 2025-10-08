package es.daw.jakarta.jdbcapp.Controllers;

import es.daw.jakarta.jdbcapp.Model.Fabricante;
import es.daw.jakarta.jdbcapp.Model.Producto;
import es.daw.jakarta.jdbcapp.Repository.FabricanteDAO;
import es.daw.jakarta.jdbcapp.Repository.GenericDAO;
import es.daw.jakarta.jdbcapp.Repository.ProductoDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet(name = "EditarActualizarProductos", value = {"/productos/actualizar", "/productos/editar"})
public class EditarActualizarProductos extends HttpServlet {
    private static final Logger log = Logger.getLogger(EditarActualizarProductos.class.getName());

private ProductoDAO daoP;
private FabricanteDAO daoF;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try{
            daoP = new ProductoDAO();
            daoF = new FabricanteDAO();

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String codigo = req.getParameter("codigo");

        if(codigo == null || codigo.isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El codigo no puede estar vacio");
            return;
        }

        try{
            Optional<Producto> productoM = daoP.findById(Integer.parseInt(codigo));

            if (productoM.isEmpty()){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "El producto no existe");

            }

            Producto producto = productoM.get();
            req.setAttribute("producto", producto);

            List<Fabricante> fabricantes = daoF.findAll();
            req.setAttribute("fabricantes", fabricantes);

            getServletContext().getRequestDispatcher("/formularioProductos.jsp").forward(req, resp);


        } catch (SQLException e) {
            //en una app multipagina lo ideal es navegar asi que enviar a error jsp y desde ahi poder volver , navegar , etc
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    try{
        int codigo = Integer.parseInt(req.getParameter("codigo"));
        String nombre = req.getParameter("nombre");
        BigDecimal precio;
        precio = new BigDecimal(req.getParameter("precio"));
        int codigo_fabricante = Integer.parseInt(req.getParameter("codigo_fabricante"));

        daoP.update(new Producto(codigo,nombre,precio,codigo_fabricante));

    } catch (NumberFormatException e) {
        throw new RuntimeException(e);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    resp.sendRedirect(req.getContextPath() + "/productos/ver");

    }
}
