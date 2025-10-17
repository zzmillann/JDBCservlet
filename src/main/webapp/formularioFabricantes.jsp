<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.daw.jakarta.jdbcapp.Model.Fabricante" %>
<%@ page import="java.util.List" %>
<%@ page import="es.daw.jakarta.jdbcapp.Model.Producto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nuevo Fabricante</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 600px;
            margin: 3rem auto;
            padding: 2rem;
            border-radius: 1rem;
            background: #fff;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<%

    Fabricante fabricante = (Fabricante) request.getAttribute("fabricante");
    boolean esEdicion = fabricante != null;
    String titulo = esEdicion ? "Editar" : "Insertar nuevo fabricante";
    String accion = esEdicion?"editar":"crear";
%>
<div class="container">
    <div class="form-container">
        <h2 class="text-center text-primary mb-4"><%= titulo %></h2>

        <form action="<%=request.getContextPath()%>/fabricantes/<%=accion%>" method="post">
            <!-- CÓDIGO DEL PRODUCTO -->
            <div class="mb-3">
                <label for="codigo" class="form-label">Código del producto</label>
                <input type="text" id="codigo" name="codigo" class="form-control"
                       required placeholder="Ej: 101"
                       value="<%= esEdicion ? fabricante.getCodigo() : "" %>"
                    <%= esEdicion ? "readonly" : "" %>>
                <div class="form-text text-muted">
                    El código identifica de forma única al producto en la base de datos.
                    <% if (esEdicion) { %>
                    (No se puede modificar)
                    <% } %>
                </div>
            </div>
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre del fabricante</label>
                <input type="text" id="nombre" name="nombre"
                       value="<%=esEdicion? fabricante.getNombre() : ""%>"
                       class="form-control"
                       required placeholder="Ej: Portátil Lenovo">
            </div>

            <div class="d-flex justify-content-between">
                <a href="<%= request.getContextPath() %>/fabricantes/ver" class="btn btn-secondary">⬅️ Cancelar</a>
                <button type="submit" class="btn btn-success">💾 Guardar producto</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>