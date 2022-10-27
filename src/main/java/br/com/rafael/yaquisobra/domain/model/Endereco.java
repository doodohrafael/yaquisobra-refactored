package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.Data;

@Data
@Embeddable
public class Endereco {

	@Column(name = "endereco_logradouro", length = 70)
	private String logradouro;

	@Column(name = "endereco_numero", length = 20)
	private String numero;

	@Column(name = "endereco_bairro", length = 70)
	private String bairro;

	@Column(name = "endereco_cep", length = 9)
	private String cep;

	@Column(name = "endereco_complemento", length = 70)
	private String complemento;

	@Embedded
	@AttributeOverrides({
        @AttributeOverride(name="nome",
                           column=@Column(name="endereco_cidade")),
        @AttributeOverride(name="estado.nome",
                           column=@Column(name="endereco_estado"))
    })
	private Cidade cidade;
	
}