package com.chuix.formacionbdi.springboot.app.productos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chuix.formacio.springboot.app.commons.models.entity.Producto;
import com.chuix.formacionbdi.springboot.app.productos.models.service.IProductosService;

@RestController
public class ProductoController {
	
	// Mediante el enviroment podemos obtener el puerto.
	
	@Autowired
	private Environment env;
	
	// otra forma de leer de application.properties.
	
	@Value("${server.port}")
	private Integer port;

	@Autowired
	private IProductosService productoService;
	
	/**
	 * La función map tiene que devolver el producto.
	 * Como el método espera una lista usamos al final collect(Collectors.toList);
	 * @return
	 */
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream().map( producto -> {
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
		
	}
	
	@GetMapping("/ver/{id}")
	public Producto ver(@PathVariable Long id) {
		Producto producto = productoService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
		
		/*
		 * boolean ok = true; if( ! ok) { throw new
		 * Exception("No se ha podido cargar el producto"); }
		 */
		
		/*try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		return producto;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED )
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED )
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		
		Producto productoDb = productoService.findById(id);
		if(productoDb != null) {
			productoDb.setNombre(producto.getNombre());
			productoDb.setPrecio(producto.getPrecio());
		}
		return productoService.save(productoDb);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
	}
	

}
