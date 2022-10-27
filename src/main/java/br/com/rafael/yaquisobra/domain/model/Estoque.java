package br.com.rafael.yaquisobra.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estoque {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	
	private String descricao;
	
	private String localidade;
	
	// Data e hora exata do ingrediente adicionado ao estoque
	@CreationTimestamp
	@Column(name = "data_hora_entrada", columnDefinition = "datetime")
	private LocalDateTime dataHoraEntrada;

}
