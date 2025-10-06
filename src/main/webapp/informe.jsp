<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.daw.jakarta.jdbcapp.Model.Producto" %>
<%@ page import="java.util.List" %>
<%@ page import="jakarta.enterprise.inject.spi.configurator.ProducerConfigurator" %>
<%@ page import="java.util.Optional" %>
<%@ page import="es.daw.jakarta.jdbcapp.Model.Fabricante" %>
<%@ page import="es.daw.jakarta.jdbcapp.utils.Utils" %>
<html>
<head>
    <title>Lista de Productos</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Colores personalizados para las filas */
        .table-custom tbody tr:nth-child(odd) {
            background-color: #f8f9fa; /* gris muy claro */
        }
        .table-custom tbody tr:nth-child(even) {
            background-color: #e9f2fa; /* azul grisáceo suave */
        }
        .table-custom tbody tr:hover {
            background-color: #d0e7f9; /* azul un poco más marcado al pasar el ratón */
        }
    </style>
</head>
<body class="bg-light">
<%
    List<Producto> productos = (List<Producto>)request.getAttribute("productos");
    List<Fabricante> fabricantes = (List<Fabricante>)request.getAttribute("fabricantes");

%>
<div class="container mt-5">
    <h2 class="mb-4 text-primary">📋 Productos disponibles</h2>

<% if(productos != null && !productos.isEmpty())  {%>
    <div class="table-responsive">
        <table class="table table-bordered table-custom">
            <thead class="table-dark">
            <tr>
                <th scope="col">Código</th>
                <th scope="col">Nombre</th>
                <th scope="col">Precio</th>
                <th scope="col">Fabricante</th>
                <th scope="col"> Nombre Fabricante </th>
                <th scope="col"> Borrar </th>
                <th scope="col"> Modificar </th>

            </tr>
            </thead>
            <tbody>

            <%
                for (Producto p : productos) { %>

            <tr>
                <td><%= p.getCodigo() %></td>
                <td><%= p.getNombre() %></td>
                <td><%= p.getPrecio() %></td>
                <td><%= p.getCodigo_fabricante() %></td>
                <td><%= Utils.obtenerNombreFabricante(fabricantes, p.getCodigo_fabricante())%></td>
                <td>
                    <form action="borrar" method="POST">
                        <input type="hidden" name="codigo" value="<%=p.getCodigo()%>">
                        <button type="submit" class="btn btn-sm btn-danger" onsubmit="return confirm('seguro que deseas borrar?')">Borrar</button>
                    </form>
                </td>
                <td>
                    <form action="borrar" method="POST">
                        <button type="submit" class="btn btn-sm btn-info" onsubmit="return confirm('seguro que deseas modificar?')">Modificar</button>
                    </form>
                </td>
            </tr>
             <%  } %>


            </tbody>
        </table>
    </div>


    <%  } else {
    %>
    <div class="alert alert-warning" role="alert">
        ⚠️ No hay productos disponibles
    </div>
    <%
        }
    %>
</div>

<!-- Bootstrap JS (opcional, solo si usas componentes dinámicos) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>