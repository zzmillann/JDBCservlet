package es.daw.jakarta.jdbcapp.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * PATRÓN SINGLETON Este patrón de diseño se encarga de que una clase
 * determinada unicamente pueda tener un único objeto.
 * El patrón Singleton, o patrón de instancia única,
 * es un patrón de diseño encargado de restringir la
 * creación de objetos de una clase (o el valor de un tipo) a un único objeto.
 * Genera una única instancia en la ejecución del programa y proporciona un
 * acceso global a la misma.
 *
 * DBConnection: esta es la clase que me va a permitir conectar a la B.D
 *
 *
 */
public class DBConnection {

    private static Connection con = null;

    private DBConnection() {
    } //De esta forma nadie puede hacer un new de DBConnection con constructor vacío por defecto

    /**
     * Método para obtener la conexión
     * @return
     * @throws SQLException
     *
     */
    public static Connection getConnection() throws SQLException {
        //public static Connection getConnection(String dbSettingsPropsFilePath) throws SQLException {

        if (con == null) {

            Properties props = new Properties();

            try (InputStream input = DBConnection.class.getClassLoader()
                    .getResourceAsStream("JDBC.properties")){

                Class.forName("org.h2.Driver");

                //FileReader fReader = new FileReader(dbSettingsPropsFilePath);
                //props.load(fReader);

                props.load(input);
                con = DriverManager.getConnection(props.getProperty("url"), props);

            }catch (FileNotFoundException ex) {
                ex.printStackTrace();
                throw new SQLException("No se ha encontrado el fichero de propiedades");
            }catch (IOException ex) {
                ex.printStackTrace();
                throw new SQLException("Error cargando el fichero de propiedades");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                throw new SQLException("No se ha encontrado el driver de conexión");
            }
        }
        return con;
    }

    /**
     * Método para cerrar la conexión
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
