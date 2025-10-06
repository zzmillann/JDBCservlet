<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Conexión a base de datos</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        h1{
            text-align:center;
        }
        body {
            font-size: 16px;
            font-family: sans-serif;
        }
        form{
            margin: 0px auto;
            width:50%;
        }
        .fieldset-principal {
            padding-left: 2em;
            padding-right: 2em;
            margin-top: 10px;
            margin-bottom: 10px;
            border: 2px solid #395870;
            box-shadow: 3px 6px 2px rgba(0, 0, 0, .3);
        }
        .fieldset-principal>legend {
            padding: 0.2em 0.5em;
            border: 1px solid;
            background: #395870;
            background: linear-gradient(#49708f, #293f50);
            color: white;
        }

        legend {
            font-weight: bold;
        }

        label[for] {
            margin-top: 0.5em;
            font-weight: bold;
            display: block;
        }
        label input[type="checkbox"],
        label input[type="radio"] {
            display: inline;
            font-weight: normal;
        }
        .obligatorio:after {
            content: ' (obligatorio)';
        }
        button {
            padding: 0 5px 0 5px;
            height: 25px;
            line-height: 20px;
            border: 2px solid #395870;
            border-radius: 6px;
            background: #395870;
            background: linear-gradient(#49708f, #293f50);
            font-weight: bold;
            font-size: 16px;
            color: white;
        }

        button:hover {
            background: #314b60;
            box-shadow: inset 0 0 10px 1px rgba(0, 0, 0, .3);
        }
    </style>

</head>
<body>
<h1>PRODUCTOS JDBC DAO</h1>
<form action="productos/ver" method="GET" target="_blank">
    <fieldset class="fieldset-principal">
        <legend>Listado de productos de la tienda:</legend>

        <!-- puedo hacer mas cosillas (filtrar por id o filtar por nombre)-->
        <label for="codigoid">Código:<input type="text" name="codigoid" id="codigoid"/></label>

        <p><input type="submit" value="Obtener listado de productos en formato tabla"/></p>
    </fieldset>
</form>
<form action="productos/ver" method="GET" target="_blank">
    <fieldset class="fieldset-principal">
        <legend>Listado de productos de la tienda:</legend>



        <p><input type="submit" value="Obtener listado de productos en formato tabla"/></p>
    </fieldset>
</form>

<form action="productos/modificar" method="POST" target="_blank">
    <fieldset class="fieldset-principal">
        <legend>Datos del producto</legend>

        <label for="codigo">Código:<input type="text" name="codigo" id="codigo"/></label>

        <label for="nombre">Nombre:<input type="text" name="nombre" id="nombre"/></label>
        <label for="precio">Precio:<input type="text" name="precio" id="precio"/></label>
        <!-- <p>
            <label for="codigo_fabricante">Código del fabricante:<input type="text" name="codigo_fabricante"/></label>
        </p>-->
        <label for="departamento">Fabricantes:</label>
        <select name="codigo_fabricante" id="departamento">
            <option value="1">Asus</option>
            <option value="2">Lenovo</option>
            <option value="3">Hewlett-Packard</option>
            <option value="4">Samsung</option>
            <option value="5">Seagate</option>
            <option value="6">Crucial</option>
            <option value="7">Gigabyte</option>
            <option value="8">Huawei</option>
            <option value="9">Xiaomi</option>
        </select>

    </fieldset>

    <fieldset class="fieldset-principal">
        <legend>Operaciones</legend>
        <label for="operacion1">Insertar nuevo producto con todos los campos:<input type="radio" value="insert" id="operacion1" name="operacion" required/></label>
        <label for="operacion2">Actualizar nombre del producto:<input type="radio" name="operacion" id="operacion2" value="update" required/></label>
        <label for="operacion3">Borrar por código de producto:<input type="radio" name="operacion" id="operacion3" value="delete" required/></label>
        <label for="codigo">Código del producto (actualizar/borrar):<input type="text" name="codigoBorrar" id="codigoborrar"/></label>

    </fieldset>

    <button name="enviar" type="submit">Modificar productos</button>
</form>
</body>
</html>