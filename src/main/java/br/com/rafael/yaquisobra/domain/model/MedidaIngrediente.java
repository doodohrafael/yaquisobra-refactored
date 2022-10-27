package br.com.rafael.yaquisobra.domain.model;

import lombok.Getter;
import lombok.Setter;

/* Cada ENUM é do própri ENUM. 
 * Atribuindo ele como um atributo/propriedade de uma classe, podemos atribuir valores. 
 * Valores que podem ser cada um desses ENUMs. 
 * Seria algo assim: "MedidaIngrediente medida = MedidaIngrediente.DECALITRO" 
 * (Parecido com atribuição estática).
 * 
 * Um Enum não pode ser instanciado como um novo objeto, ele deve receber um valor. 
 * 
 * A cada momento que formos construir esse enum, queremos que chame o construtor que vai 
 * inicializar a variável que vamos criar, chamada “medida”.*/

public enum MedidaIngrediente {
	
	/*
	 * Unidades de capacidade:
	 * As medidas de capacidade são usadas para determinar a quantidade de um
	 * determinado objeto e podem ser definidas em litro/centilitro/mililitro = 1L.
	 */
	QUILOLITRO("kl"), HECTOLITRO("hl"), DECALITRO("dal"), LITRO("l"), DECILITRO("dl"), CENTILITRO("cl"), MILILITRO("ml"), 

	
	/*
	 * Unidades de massa:
	 * As medidas de massa são usadas quando queremos definir a quantidade exata de
	 * massa de um corpo. No nosso cotidiano, usamos o quilograma e o grama para
	 * medir essa quantidade em determinados objetos.
	 */
	QUILOGRAMA("kg"), HECTOGRAMA("hg"), DECAGRAMA("dag"), GRAMA("g"), DECIGRAMA("dg"), CENTIGRAMA("cg"), MILIGRAMA("mg");

	private MedidaIngrediente(String medida) {
		this.medida = medida;
	}
	
	@Getter @Setter
	private String medida;
}
