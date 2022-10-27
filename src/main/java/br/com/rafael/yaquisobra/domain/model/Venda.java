package br.com.rafael.yaquisobra.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Venda {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Column(name = "data_hora_cadastro", columnDefinition = "datetime")
	private LocalDateTime dataHoraCadastro;

	@Column(name = "valor_total", precision = 7, scale = 2 )
	private BigDecimal valorTotal;
	
	@Column(name = "quantidade_itens_total")
	private Integer quantidadeItensTotal;
	
	@Column(name = "valor_frete", precision = 7, scale = 2)
	private BigDecimal valorFrete;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy="venda") // Agregação de composição
	private List<FormaPagamento> formaPagamentos = new ArrayList<>();
	
	@OneToMany(mappedBy = "venda") // Agregação de composição
	private List<Item> itens = new ArrayList<>();
	
	@Convert(converter = MapeadorTipoVenda.class)
	private TipoVenda tipoVenda;
	
	@Convert(converter = MapeadorStatusVenda.class)
	private StatusVenda statusVenda;
	
	@Transient
	private FormaPagamento formaPagamento;
	
}