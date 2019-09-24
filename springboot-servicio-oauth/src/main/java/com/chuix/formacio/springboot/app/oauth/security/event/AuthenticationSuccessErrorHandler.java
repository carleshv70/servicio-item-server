package com.chuix.formacio.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.chuix.formacio.springboot.app.oauth.services.IUsuarioService;
import com.chuix.formacio.springboot.app.usuarios.commons.models.entity.Usuario;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails)authentication.getPrincipal();
		String mensaje = "Success login: " + user.getUsername();
		System.out.println(mensaje);
		log.info(mensaje);
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		
		if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en el login: " + exception.getMessage();
		log.error(mensaje);
		System.out.println(mensaje);
		
		try {
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			
			if(usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			
			log.info(String.format("Intentos actual es de: %d", usuario.getIntentos()));
			usuario.setIntentos(usuario.getIntentos() + 1);
			
			log.info(String.format("Intentos después es de: %d", usuario.getIntentos()));
			
			if(usuario.getIntentos() >=3 ) {
				log.error(String.format("El usuario %s des-habilitado por superar el número máximo de intentos", authentication.getName()));
				usuario.setEnabled(false);
			}
			
			usuarioService.update(usuario, usuario.getId());
			
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
		
		
		
	}

}
