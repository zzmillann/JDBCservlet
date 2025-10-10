package es.daw.jakarta.jdbcapp.Controllers;

import es.daw.jakarta.jdbcapp.Model.Producto;
import es.daw.jakarta.jdbcapp.Repository.FabricanteDAO;
import es.daw.jakarta.jdbcapp.Repository.ProductoDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet (name = "BorrarProductosServlet", value = "/productos/borrar")
public class BorrarProductosServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditarActualizarProductos.class.getName());

    private ProductoDAO daoP;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            daoP = new ProductoDAO();

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String codigo = req.getParameter("codigo");

        if (codigo == null || codigo.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El codigo no puede estar vacio");
            return;
        }

        try {
            Optional<Producto> productoM = daoP.findById(Integer.parseInt(codigo));

            if (productoM.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "El producto no existe");

            }

            Producto producto = productoM.get();
            daoP.delete(producto.getCodigo());
            resp.sendRedirect(req.getContextPath() + "/productos/ver");



        }  catch (SQLException e) {
        //en una app multipagina lo ideal es navegar asi que enviar a error jsp y desde ahi poder volver , navegar , etc
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());

    }
    }
}