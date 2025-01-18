package com.api.vital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.vital.models.entity.Usuario;
import com.api.vital.models.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String term) {
		return repository.findByUsername(term);
	}
	
	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) repository.findAll();
	}

	@Override
	public Optional<Usuario> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		Usuario exist = repository.findByUsername(usuario.getUsername());
		if(exist != null)
			return null;
		return repository.save(usuario);
	}

}
