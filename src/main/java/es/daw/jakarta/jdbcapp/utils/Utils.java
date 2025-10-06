package es.daw.jakarta.jdbcapp.utils;

import es.daw.jakarta.jdbcapp.Model.Fabricante;

import java.util.List;

public class Utils {

    public static String obtenerNombreFabricante(List<Fabricante> fabricantes, Integer codigo) {

        String nombreFabricante = "";
        for (Fabricante fabricante : fabricantes) {
            if (fabricante.getCodigo().equals(codigo)) {
                nombreFabricante = fabricante.getNombre();

            }
        }

        return nombreFabricante;
    }
}
