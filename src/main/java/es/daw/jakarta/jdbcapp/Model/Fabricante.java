package es.daw.jakarta.jdbcapp.Model;

import java.util.Objects;

public class Fabricante {

    private Integer codigo;
    private String nombre;


    public Fabricante() {
    }


    public Fabricante(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fabricante that = (Fabricante) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre);
    }
}
