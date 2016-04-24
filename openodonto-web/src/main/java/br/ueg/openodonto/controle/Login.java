package br.ueg.openodonto.controle;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import br.ueg.openodonto.controle.context.ApplicationContext;
import br.ueg.openodonto.controle.exception.LoginInvalidoException;
import br.ueg.openodonto.dominio.Usuario;
import br.ueg.openodonto.persistencia.LoginManager;
import br.ueg.openodonto.persistencia.dao.DaoLogin;
import br.ueg.openodonto.util.ShaUtil;
import br.ueg.openodonto.visao.ApplicationView;
import br.ueg.openodonto.visao.ApplicationViewFactory;
import br.ueg.openodonto.visao.ApplicationViewFactory.ViewHandler;
import br.ueg.openodonto.web.WebContext;

/**
 * @author Vinicius
 * 
 */
public class Login implements Serializable{

	private static final long serialVersionUID = -7584236627686667592L;
	
	private Usuario usuario;
	private LoginManager loginDao;
	private ApplicationContext context;
	private ApplicationView view;

	public Login() {
		this.usuario = new Usuario();
		this.loginDao = new DaoLogin();
		this.context = new WebContext();
		makeView(Collections.EMPTY_MAP);
	}

	public String acaoAutenticarUsuario() {

		try {
			usuario.setSenha(ShaUtil.hash(usuario.getSenha()));
			Usuario usuarioSessao = this.loginDao.autenticar(this.usuario);
			context.addAttribute("usuarioSessao", usuarioSessao);
			return "index";

		} catch (LoginInvalidoException e) {
			usuario.setSenha(null);
			view.addResourceDynamicMenssage("Usuário/senha incorreto(s).",	"LoginForm:messageLogin");
		} catch (Exception e) {
			usuario.setSenha(null);
			view.addResourceDynamicMenssage("Usuário/senha incorreto(s).","Erro de causa desconhecida.");
			e.printStackTrace();
		}
		return null;
	}

	public String acaoLogout() {
		context.removeAttribute("usuarioSessao");
		return "login";
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void makeView(Map params) {
		this.view = ApplicationViewFactory.getViewInstance(ViewHandler.JSF,	params);
	}
}
