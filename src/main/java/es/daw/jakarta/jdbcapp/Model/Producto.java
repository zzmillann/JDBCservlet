package es.daw.jakarta.jdbcapp.Model;

import java.math.BigDecimal;

public class Producto {
    private Integer codigo;
    private String nombre;
    private BigDecimal precio;
    // los decimales para precio no es recomendable ni float ni double porque son binariosen coma flotante
    //jdbC mapea el bigdecimal a decimal muyyyyyy bien
    private Integer codigo_fabricante;


    public Producto() {
    }

    public Producto(Integer codigo, String nombre, BigDecimal precio, Integer codigo_fabricante) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.codigo_fabricante = codigo_fabricante;

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCodigo_fabricante() {
        return codigo_fabricante;
    }

    public void setCodigo_fabricante(Integer codigo_fabricante) {
        this.codigo_fabricante = codigo_fabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
