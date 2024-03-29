package com.chuix.formacionbdi.springboot.app.item.models;

import com.chuix.formacio.springboot.app.commons.models.entity.Producto;

public class Item {

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public Item(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	private Producto producto;
	private Integer cantidad;

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getTotal() {
		return producto.getPrecio() * this.cantidad.doubleValue();
	}
}
