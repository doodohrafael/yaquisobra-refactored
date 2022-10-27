package br.com.rafael.yaquisobra.util;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.rafael.yaquisobra.beans.LoginMBean;
import br.com.rafael.yaquisobra.domain.model.Usuario;

public class AutenticacaoPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	// Agir depois do RESTORE_VIEW
	public void afterPhase(PhaseEvent event) {

		// Descobre a página atual/corrente em que estamos.
		FacesContext facesContext = event.getFacesContext();

		// Pega a página corrente. uiViewRoot Navega na árvore de componentes.
		UIViewRoot uiViewRoot = facesContext.getViewRoot();

		// getViewId() Descobre qual página desparou o evento. Retorna o nome da página.
		String paginaAtual = uiViewRoot.getViewId();
		System.out.println("Página atual: " + paginaAtual);

		// Páginas públicas
		boolean paginaAutenticacao = paginaAtual.contains("index.xhtml");
		System.out.println("Pagina Autenticação: " + paginaAutenticacao);

		// Páginas privadas
		if (!paginaAutenticacao) {
			try {
				// Pega dados dessa aplicação.
				ExternalContext externalContext = facesContext.getExternalContext();

				// Mapa que mantém as variáveis da sessao da aplicação. Os manegedbean sesssion
				// scoped estão nele.
				Map<String, Object> mapa = externalContext.getSessionMap();

				// Pega o loginMBean do mapa.
				LoginMBean loginMBean = (LoginMBean) mapa.get("loginMBean");
				/*
				 * Obs: Os managed beans só são criados após serem usados pela primeira vez.
				 * Então o login vai ser criado quando clicarmos no botão "Entrar". Por isso
				 * então, algumas vezes com o @Named ele retorna nulo.
				 */
				System.out.println("LoginMBean: " + loginMBean);

				Usuario usuario = loginMBean.getUsuarioLogado();
				System.out.println("Usuário: " + usuario);

				if (usuario.getFuncao() == null) {
					Application application = facesContext.getApplication();
					NavigationHandler navigationHandler = application.getNavigationHandler();
					navigationHandler.handleNavigation(facesContext, null, Constantes.telaLogin);
					Mensagens.MensagemErro("Usuário não autenticado.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Erroooooooooooo:" + e.getMessage());
			}
		}
	}

	public void beforePhase(PhaseEvent event) {
	}

//	Define em qual fase o phaselistener vai agir
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW; // Age no momento de montar a árvore de componentes
		// internamenteo JSF armazena uma árvore com a estrtura de todas as páginas
	}

}
