package br.com.rafael.yaquisobra.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

public enum Status {

	CONECTADO,
	DESCONECTADO;
	
	public void entrada() {
		entrada = LocalDateTime.of(
				LocalDate.now(), LocalTime.now());
	}
	
	public void saida() {
		entrada = LocalDateTime.of(
				LocalDate.now(), LocalTime.now());
	}
	
	@Getter @Setter
	private static LocalDateTime entrada;
	
	@Getter @Setter
	private static LocalDateTime saida;

}
