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
        String sql = "INSERT INTO producto(codigo, nombre , precio, codigo_fabricacion) VALUES(?,?,?,?)";
    }

    @Override
    public Optional<Producto> findById(Integer integer) throws SQLException {
        List<Producto> findById = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                findById.add(
                        new Producto(
                                resultSet.getInt("codigo"),
                                resultSet.getString("nombre"),
                                resultSet.getBigDecimal("precio"),
                                resultSet.getInt("codigo_fabricante")
                        )
                );
            }
            return findById.isEmpty() ? Optional.empty() : Optional.of(findById.get(0));
        }
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

    }

    @Override
    public void delete(Integer integer) throws SQLException {

    }
}
