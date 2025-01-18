package com.api.vital.service;

import java.util.List;
import java.util.Optional;

import com.api.vital.models.entity.Usuario;

public interface UsuarioService{
	
	public Usuario save(Usuario usuario);
	
	public Usuario findByUsername(String term);
	
	public List<Usuario> findAll();
	
	public Optional<Usuario> findById(String id);

}
