package com.nttdata.practicaslogs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import starter.Temperatura;

@RestController
public class IndexController {
	
	@Autowired
	private Temperatura temperatura;
	
	private final static Logger logger= LoggerFactory.getLogger(IndexController.class);
	
	private Counter counterConsulta;
	
	public IndexController(MeterRegistry registry) {
		this.counterConsulta = Counter.builder("Invocaciones.contadorConsultaTemp").description("Invocaciones totales").register(registry);
	}
	
	@GetMapping(path="/consultaTemp")
	public String consultaTemp() {
		counterConsulta.increment();
		logger.info("Se ha llamado a consulta "+counterConsulta.count()+" veces");
		return ""+temperatura.mensajeTemperatura();
	}

}
