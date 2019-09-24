package com.chuix.formacio.springboot.appzuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);
	
	// si este método devuelve true se ejecuta el método run
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
					
		log.info("Entrando a post");

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		Long tiempoInicio = (Long)request.getAttribute("tiempoInicio");
		Long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
		
		log.info(String.format("Tiempo transcurrido en segundos %s seg", tiempoTranscurrido.doubleValue()/1000.00));
		
		return null;
	}

	// En este metodo tenemos que indicar si es un filtro de tipo pre, post, route
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
