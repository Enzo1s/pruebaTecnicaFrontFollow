package com.api.vital.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.vital.models.entity.Producto;
import com.api.vital.models.entity.Usuario;
import com.api.vital.service.ProductoService;
import com.api.vital.service.jwt.JWTService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "productos")
public class ProductoController extends CommonController<Producto, ProductoService> {

	@Autowired
	private JWTService jwt;
	
	@GetMapping("/productos-por-tipo")
	public ResponseEntity<?> obtenerProductosPorTipo(@RequestParam List<Long> ids,@RequestHeader(value = "Authorization") String token) {
		Usuario usuario = jwt.obtenerUsuario(token);
		if(usuario == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Producto producto, BindingResult result, @PathVariable Long id,@RequestHeader(value = "Authorization") String token) {
		Usuario usuario = jwt.obtenerUsuario(token);
		if(usuario == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Producto> o =service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Producto productoDb = o.get();
		productoDb.setNombre(producto.getNombre());
		productoDb.setPrecio(producto.getPrecio());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productoDb));
	}
	
	@GetMapping("filtrar/nombre/{term}") //Modificar luego para filtrar por tipo de alimento
	public ResponseEntity<?> filtrar(@PathVariable String term, @RequestHeader(value = "Authorization") String token) {
		Usuario usuario = jwt.obtenerUsuario(token);
		if(usuario == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		return ResponseEntity.ok(service.findByNombre(term));
	}
	
	@GetMapping("filtrar/tipo/{term}") /*Revisar y modificar para optimizar*/
	public ResponseEntity<?> ObtenerPorTipo(@PathVariable String term, @RequestHeader(value = "Authorization") String token) {
		Usuario usuario = jwt.obtenerUsuario(token);
		if(usuario == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		List<Producto> productos = ((List<Producto>) service.findAll()).stream().map(p -> {
			if(p.getTipo().contains(term)) {
				return p;
			} else {
				return null;
			}
		}).collect(Collectors.toList());
		return ResponseEntity.ok().body(productos);
	}

	
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(Producto producto, @RequestParam MultipartFile archivo, @RequestHeader(value = "Authorization") String token) throws IOException {
		Usuario usuario = jwt.obtenerUsuario(token);
		if(usuario == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if(!archivo.isEmpty()) {
			producto.setFoto(archivo.getBytes());
		}
		return super.crear(producto);
	}
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(Producto producto, @PathVariable Long id, @RequestParam MultipartFile archivo, @RequestHeader(value = "Authorization") String token) throws IOException {
		Usuario usuario = jwt.obtenerUsuario(token);
		if(usuario == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		Optional<Producto> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Producto productoDb = o.get();
		productoDb.setNombre(producto.getNombre());
		productoDb.setDescripcion(producto.getDescripcion());
		productoDb.setPrecio(producto.getPrecio());
		productoDb.setTipo(producto.getTipo());
		if(!archivo.isEmpty()) {
			productoDb.setFoto(archivo.getBytes());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productoDb));
	}
	
}
