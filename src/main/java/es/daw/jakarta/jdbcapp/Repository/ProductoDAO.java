package es.daw.jakarta.jdbcapp.Repository;

import es.daw.jakarta.jdbcapp.Model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAO implements GenericDAO<Producto, Integer>{

    private Connection connection;

    public ProductoDAO() throws SQLException {
       connection = DBConnection.getConnection();
    }

    @Override
    public void save(Producto entity) throws SQLException {
        //llamaria a findby em el servlet para ver si el codigo esta duplicado
        //forma 1 consultar sql con el find by id
        // forma 2 sqlrecogiendo la excepcion de la bd
        // forma 3 findall y busco en la lista
        //obligatoria mente deno implementar equals hashcode
        String sql = "INSERT INTO producto(codigo, nombre , precio, codigo_fabricante) VALUES(?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, entity.getCodigo());
            statement.setString(2, entity.getNombre());
            statement.setBigDecimal(3, entity.getPrecio());
            statement.setInt(4, entity.getCodigo_fabricante());
            statement.executeUpdate();

        }
    }

    @Override
    public Optional<Producto> findById(Integer integer) throws SQLException {
        String sql = "SELECT * FROM PRODUCTO WHERE codigo = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                        Producto pr = new Producto(
                                resultSet.getInt("codigo"),
                                resultSet.getString("nombre"),
                                resultSet.getBigDecimal("precio"),
                                resultSet.getInt("codigo_fabricante")
                        );
                return Optional.of(pr);

            }

        }
        return Optional.empty();

    }

    @Override
    public List<Producto> findAll() throws SQLException {

        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";


        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productos.add(
                        new Producto(
                            resultSet.getInt("codigo"),
                            resultSet.getString("nombre"),
                            resultSet.getBigDecimal("precio"),
                            resultSet.getInt("codigo_fabricante")
                ));
            }
            return  productos;
        }

    }

    @Override
    public void update(Producto entity) throws SQLException {

        try(PreparedStatement ps = connection.prepareStatement(" UPDATE producto SET nombre = ?, precio = ?, codigo_fabricante = ? WHERE codigo = ?")){
            ps.setString(1, entity.getNombre());
            ps.setBigDecimal(2, entity.getPrecio());
            ps.setInt(3, entity.getCodigo_fabricante());
            ps.setInt(4, entity.getCodigo());
            ps.executeUpdate();

        }

    }

    @Override
    public void delete(Integer integer) throws SQLException {

        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PRODUCTO WHERE codigo = ?")){
            preparedStatement.setInt(1, integer);
            preparedStatement.execute();


        }catch (SQLException e){

        }

    }
}
