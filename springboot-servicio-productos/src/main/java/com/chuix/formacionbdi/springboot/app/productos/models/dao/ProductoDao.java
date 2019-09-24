package com.chuix.formacionbdi.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.chuix.formacio.springboot.app.commons.models.entity.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long> {

	
}
