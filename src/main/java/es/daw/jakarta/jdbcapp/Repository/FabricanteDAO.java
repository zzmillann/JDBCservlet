package es.daw.jakarta.jdbcapp.Repository;

import es.daw.jakarta.jdbcapp.Model.Fabricante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FabricanteDAO implements GenericDAO<Fabricante, Integer> {

    private Connection connection;

    public FabricanteDAO() throws SQLException {
        connection = DBConnection.getConnection();
    }

    @Override
    public void save(Fabricante entity) throws SQLException {

        try (PreparedStatement ps = connection.prepareStatement("Insert into Fabricante (codigo, nombre) VALUES (?, ?)")) {
            ps.setInt(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();

        }


    }

    @Override
    public Optional<Fabricante> findById(Integer integer) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Fabricante WHERE codigo = ?")) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Fabricante fr = new Fabricante(resultSet.getInt("codigo"), resultSet.getString("nombre"));
                return Optional.of(fr);


            }
        }
        return Optional.empty();
    }


    @Override
    public List<Fabricante> findAll() throws SQLException {
        List<Fabricante> fabricantes = new ArrayList<>();
        String sql = "SELECT * FROM fabricante";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fabricantes.add(
                        new Fabricante(
                                resultSet.getInt("codigo"),
                                resultSet.getString("nombre")


                        ));

            }
            return fabricantes;
        }

    }

    @Override
    public void update(Fabricante entity) throws SQLException {

        try (PreparedStatement ps = connection.prepareStatement("UPDATE fabricante SET nombre = ? WHERE codigo = ?;")) {
            ps.setString(1, entity.getNombre());
            ps.setInt(2, entity.getCodigo());
            ps.executeUpdate();

        }
    }

    @Override
    public void delete(Integer codigoFabricante) throws SQLException {
        // Primero borramos los productos asociados
        try (PreparedStatement ps1 = connection.prepareStatement(
                "DELETE FROM producto WHERE codigo_fabricante = ?")) {
            ps1.setInt(1, codigoFabricante);
            ps1.executeUpdate();
        }

        // Luego borramos el fabricante
        try (PreparedStatement ps2 = connection.prepareStatement(
                "DELETE FROM fabricante WHERE codigo = ?")) {
            ps2.setInt(1, codigoFabricante);
            ps2.executeUpdate();
        }
    }
}
