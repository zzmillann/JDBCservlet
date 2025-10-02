package es.daw.jakarta.jdbcapp.Controllers;

import es.daw.jakarta.jdbcapp.Model.Producto;
import es.daw.jakarta.jdbcapp.Repository.GenericDAO;
import es.daw.jakarta.jdbcapp.Repository.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(name = "ModificarProductos", value = "/productos/modificar")
public class ModificarProductos extends HttpServlet {
    private static final Logger log = Logger.getLogger(ModificarProductos.class.getName());

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

        try {
            GenericDAO<Producto, Integer> daop = new ProductoDAO();

            Producto newproducto = new Producto(
                    Integer.parseInt(codigo),
                    nombre,
                    precio1,
                    Integer.parseInt(codigo_fabricante)
            );

            switch (operacion) {
                case "insert" -> daop.save(newproducto);
                case "update" -> daop.update(newproducto);
                case "delete" -> {
                    if (codigoborrar.isEmpty()) {
                        req.setAttribute("error", "Código para borrar no puede estar vacío.");
                        getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                        return;
                    }
                    daop.delete(Integer.parseInt(codigoborrar));
                }
                default -> {
                    req.setAttribute("error", "Operación no válida.");
                    getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                    return;
                }
            }

            // Si llega aquí, la operación fue exitosa → Respuesta HTML simple
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<html>");
                out.println("<head><title>Producto modificado</title></head>");
                out.println("<body>");
                out.println("<h1>✅ Operación realizada correctamente</h1>");
                out.println("<p><a href=\"/JDBCapp_war_exploded/\">Volver al inicio</a></p>");
                out.println("</body></html>");
            }

        } catch (SQLException ex) {
            log.severe(ex.getMessage());
            req.setAttribute("error", ex.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
