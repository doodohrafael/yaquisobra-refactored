package br.com.rafael.yaquisobra.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal subtotal;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal taxaFrete;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal valorTotal;
	
	
	@Column(nullable = false, columnDefinition = "datetime")
	@CreationTimestamp
	private LocalDateTime dataHoraCriacao;
	
	private LocalDateTime dataHoraConfirmacao;
	
	private LocalDateTime dataHoraCancelamento;
	
	private LocalDateTime dataHoraEntrega;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Cliente cliente;
	
	@Column(nullable = false) 
	private StatusPedido status;
	
	@Embedded
	private Endereco enderecoEntrega;
	
//	private List<Item> itens = new ArrayList<>();
}
