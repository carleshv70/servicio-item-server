package com.chuix.formacio.springboot.app.oauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chuix.formacio.springboot.app.oauth.clients.UsuarioFeignClient;
import com.chuix.formacio.springboot.app.usuarios.commons.models.entity.Usuario;

import feign.FeignException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService {

	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignClient client;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {

			Usuario usuario = client.findByUsername(username);

			List<GrantedAuthority> authorities = usuario.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))
					.peek(authoriry -> log.info(String.format("Role: %s", authoriry.getAuthority())))
					.collect(Collectors.toList());

			log.info(String.format("Usuario autentificado: %s", username));

			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
					authorities);

		} catch (FeignException e) {
			log.error(String.format("Error en el login, no existe el usuario '%s' en el sistema.", username));
			throw new UsernameNotFoundException(
					String.format("Error en el login, no existe el usuario '%s' en el sistema.", username));
		}
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {

		return client.update(usuario, id);
	}

}
