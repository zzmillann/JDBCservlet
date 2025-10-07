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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/productos/crear")
public class CrearProductoServlet extends HttpServlet {

    private GenericDAO<Fabricante, Integer> genericDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            genericDAO = new FabricanteDAO();
        } catch (SQLException e) {
            throw new ServletException("Error al inicializar ", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Fabricante> fabricantes = new ArrayList<>();
        try {
            fabricantes = genericDAO.findAll();
            req.setAttribute("fabricantes", fabricantes);
            getServletContext().getRequestDispatcher("/formularioProducto.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String codigo = req.getParameter("codigo") == null ? "" : req.getParameter("codigo").trim();
        String nombre = req.getParameter("nombre") == null ? "" : req.getParameter("nombre").trim();
        String codigo_fabricante = req.getParameter("codigo_fabricante") == null ? "" : req.getParameter("codigo_fabricante").trim();
        String precio = req.getParameter("precio") == null ? "" : req.getParameter("precio").trim();

        String operacion = req.getParameter("operacion");
        String codigoborrar = req.getParameter("codigoBorrar") == null ? "" : req.getParameter("codigoBorrar").trim();

        // Validación básica
        if (codigo.isEmpty() || codigo_fabricante.isEmpty()) {
            req.setAttribute("error", "El código y el código del fabricante no pueden estar vacíos.");
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        BigDecimal precio1;
        try {
            if (precio.contains(",")) {
                throw new NumberFormatException("coma no permitida");
            }
            precio1 = new BigDecimal(precio);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Has escrito mal el precio. Debe contener números y decimales con punto!!! ej: 1500.80");
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        // Desmontanto para añadir las clases de la interfaz productodao
        try {
            GenericDAO<Producto, Integer> daop = new ProductoDAO();
            GenericDAO<Fabricante, Integer> dafo = new FabricanteDAO();

            Producto newproducto = new Producto(
                    Integer.parseInt(codigo),
                    nombre,
                    precio1,
                    Integer.parseInt(codigo_fabricante)
            );

            daop.save(newproducto);

        } catch (SQLException e) {
            req.setAttribute("error", e.getMessage());
        }
    resp.sendRedirect(req.getContextPath() + "/productos/ver");

    }
}