package br.com.rafael.yaquisobra.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.rafael.yaquisobra.controller.VendaController;
import br.com.rafael.yaquisobra.domain.model.FormaPagamento;

@FacesConverter("formaPagamento")
public class FormaPagamentoConverter implements Converter<FormaPagamento> {

	@Override
	public FormaPagamento getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			List<FormaPagamento> formaPagamentos = new ArrayList<>();
			VendaController vendaController = new VendaController();
			
			formaPagamentos = vendaController.listarFormaPagamentos();
			for (FormaPagamento formaPagamento : formaPagamentos) {
				if(formaPagamento.getDescricao().equals(value)) {
					System.out.println("\n getAsObject - Descrição "
							+ "formaPagamento: " + formaPagamento.getDescricao());
					return formaPagamento;
				}
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, FormaPagamento value) {

		if (value != null) {
			FormaPagamento formaPagamento = (FormaPagamento) value;
			System.out.println("\n getAsString - Descrição: " + formaPagamento.getDescricao());
			return formaPagamento.getDescricao();
		}

		return null;
	}


}
