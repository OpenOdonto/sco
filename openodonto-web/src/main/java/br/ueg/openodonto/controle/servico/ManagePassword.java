package br.ueg.openodonto.controle.servico;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import br.com.vitulus.simple.jdbc.orm.old.OrmFormat;
import br.com.vitulus.simple.jdbc.orm.old.OrmTranslator;
import br.com.vitulus.simple.jdbc.sql.old.CrudQuery;
import br.com.vitulus.simple.jdbc.sql.old.IQuery;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.dominio.Usuario;
import br.ueg.openodonto.persistencia.dao.DaoUsuario;
import br.ueg.openodonto.util.ShaUtil;
import br.ueg.openodonto.util.WordFormatter;
import br.ueg.openodonto.validator.ValidatorFactory;
import br.ueg.openodonto.visao.ApplicationView;

public class ManagePassword {

	private static String       falsePwd;
	private Validator           validatorSenha;
	private Validator           validatorNovaSenha;
	private Validator           validatorNovaSenhaConfirma;
	private String              senhaCadastro;
	private String              senha;
	private String              novaSenha;
	private String              confirmaNovaSenha;
	private Usuario             usuario;
	private boolean             sucessChange;
	private boolean             enableChangePassword;
	private ApplicationView     view;
	
	static{
		falsePwd = "****************";	
	}
	
	public ManagePassword(Usuario usuario,ApplicationView view) {
		setUsuario(usuario);
		this.validatorSenha = ValidatorFactory.newStrRangeLen(32, 3, true);
		this.validatorNovaSenha = ValidatorFactory.newStrRangeLen(32, 3, true);
		this.validatorNovaSenhaConfirma = ValidatorFactory.newStrRangeLen(32, 3, true);
		this.view = view;
	}

	public void acaoCancelar(){
		setSenha("");
		setNovaSenha("");
		setConfirmaNovaSenha("");
	}
	
	public void acaoMudarSenha(){
		setSucessChange(false);
		if(validarMudarSenha()){
			String novaSenha = ShaUtil.hash(getNovaSenha());
			getUsuario().setSenha(novaSenha);
			acaoCancelar();
			setSucessChange(true);
		}
	}
	
	private boolean validarMudarSenha(){
		validatorSenha.setValue(getSenha());
		validatorNovaSenha.setValue(getNovaSenha());
		validatorNovaSenhaConfirma.setValue(getConfirmaNovaSenha());
		boolean valid = true;
		if(!validatorSenha.isValid()){
			showPwdValidatorError("Senha",validatorSenha);
			valid = false;
		}else{			
			String hashPwd = ShaUtil.hash(getSenha());
			if(!hashPwd.equalsIgnoreCase(restorePwd())){
				this.view.addResourceDynamicMenssage("A senha fornecida esta incorreta",getSaida());
				valid = false;
			}
		}
		if(!validatorNovaSenha.isValid()){
			showPwdValidatorError("Nova senha",validatorNovaSenha);
			valid = false;
		}
		if(!validatorNovaSenhaConfirma.isValid()){
			showPwdValidatorError("Confirmar nova senha",validatorNovaSenhaConfirma);
			valid = false;
		}
		if(valid && !getNovaSenha().equals(getConfirmaNovaSenha())){
			this.view.addResourceDynamicMenssage("Senhas não correspondem.",getSaida());
			valid = false;
		}
		return valid;
	}
	
	private String restorePwd(){
		DaoUsuario dao = new DaoUsuario();
		OrmFormat format = new OrmFormat(new Usuario(getUsuario().getCodigo()));
		IQuery query = CrudQuery.getSelectQuery(
				Usuario.class,
				OrmFormat.getCleanFormat(format.format("codigo")),
				"senha");
		try {
			List<Map<String, Object>> pwdList = dao.getSqlExecutor().executarUntypedQuery(query.getQuery(), query.getParams(), 1);
			if(pwdList.size() == 1){
				Map<String, Object> formatedPwd = pwdList.get(0);
				OrmTranslator translator = new OrmTranslator(dao.getFields());
				return formatedPwd.get(translator.getColumn("senha")).toString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void showPwdValidatorError(String label,Validator validator){
		this.view.addResourceDynamicMenssage(
				WordFormatter.formatErrorMessage(
						label,
						WordFormatter.ocult(validator.getValue().toString()),
						validator.getErrorMessage()),
				getSaida());
	}
	
	public boolean getEnableChangePassword(){
		return enableChangePassword;
	}
	
	public void setEnableChangePassword(boolean enableChangePassword) {
		this.enableChangePassword = enableChangePassword;
    }
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isSucessChange() {
		return sucessChange;
	}

	public void setSucessChange(boolean sucessChange) {
		this.sucessChange = sucessChange;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaNovaSenha() {
		return confirmaNovaSenha;
	}

	public void setConfirmaNovaSenha(String confirmaNovaSenha) {
		this.confirmaNovaSenha = confirmaNovaSenha;
	}

	public String getSenhaCadastro() {
		return getEnableChangePassword() ? senhaCadastro : falsePwd;
	}

	public void setSenhaCadastro(String senhaCadastro) {
		this.senhaCadastro = senhaCadastro;
	}
	
	public String getSaida(){
		return "formAlterarSenha:messageEditTelefone";
	}
}
 