package com.luis.myapp.model;

public class Producto {

    private Integer idproducto;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Integer stock_min;
    private Integer stock_max;
    private Categoria categoria;

    public Producto() {
    }

    public Producto(Integer idproducto, String nombre, Double precio, Integer stock, Integer stock_min, Integer stock_max, Categoria categoria) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.stock_min = stock_min;
        this.stock_max = stock_max;
        this.categoria = categoria;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStock_min() {
        return stock_min;
    }

    public void setStock_min(Integer stock_min) {
        this.stock_min = stock_min;
    }

    public Integer getStock_max() {
        return stock_max;
    }

    public void setStock_max(Integer stock_max) {
        this.stock_max = stock_max;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
