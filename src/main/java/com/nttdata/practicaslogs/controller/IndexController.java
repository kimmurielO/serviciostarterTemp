package com.nttdata.practicaslogs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import starter.Temperatura;

@RestController
public class IndexController {
	
	@Autowired
	private Temperatura temperatura;
	
	@Value("${some.valueFar}")
	private String gradoTest;
	
	private final static Logger logger= LoggerFactory.getLogger(IndexController.class);
	
	private Counter counterConsulta;
	
	public IndexController(MeterRegistry registry) {
		this.counterConsulta = Counter.builder("Invocaciones.contadorConsultaTemp").description("Invocaciones totales").register(registry);
	}
	
	@GetMapping(path="/convertirTemp")
	public String convertirTemp(@RequestParam float tempe) {
		counterConsulta.increment();
		logger.info("Se ha llamado a consulta "+counterConsulta.count()+" veces");
		if(gradoTest.equals("Celsius")) {
			return ""+temperatura.convertirTemperaturaCaF(tempe);
		} else {
			return ""+temperatura.convertirTemperaturaFaC(tempe);
		}
	}
	
	@GetMapping(path="/consultaTemp")
	public String consultaTemp(@RequestParam float tempe) {
		counterConsulta.increment();
		logger.info("Se ha llamado a consulta "+counterConsulta.count()+" veces");
		if(gradoTest.equals("Celsius")) {
			return ""+temperatura.mensajeTemperaturaCel(tempe);
		} else {
			return ""+temperatura.mensajeTemperaturaFar(tempe);
		}
		
	}

}
