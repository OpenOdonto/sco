package br.ueg.openodonto.controle.servico;

import java.io.Serializable;
import java.util.List;

import br.com.vitulus.simple.validator.EmptyValidator;
import br.com.vitulus.simple.validator.NullValidator;
import br.com.vitulus.simple.validator.Validator;
import br.ueg.openodonto.dominio.Telefone;
import br.ueg.openodonto.dominio.constante.TiposTelefone;
import br.ueg.openodonto.validator.ValidatorFactory;
import br.ueg.openodonto.visao.ApplicationView;

/**
 * @author Vinicius
 * 
 */
public class ManageTelefone implements Serializable {

	private static final long serialVersionUID = 8556507770756119797L;

	private Telefone         telefone;
	private String           editNumber;	
	private TiposTelefone    editType;	
	private List<Telefone>   telefones;	
	private Validator        validatorType;
	private Validator        validatorNumber;	
	private String           saidaContato;	
	private String           saidaEditarContato;
	private ApplicationView  view;
	private boolean          sucessEdit;

	public ManageTelefone(List<Telefone> telefones,ApplicationView view) {
		this.telefones = telefones;
		this.view = view;
		this.validatorNumber = ValidatorFactory.newStrRangeLen(15, 4, true);
		this.validatorType = ValidatorFactory.newNull();
	}

	private void buildSaidaContato(){
		this.saidaContato =    this.view.getProperties().get("formularioSaida") + ":" + "messageTelefone";
	}
	
	private void buildSaidaEditarContato() {
		this.saidaEditarContato = "formAlterarTelefone:messageEditTelefone";
	}
	
	private void renew(){
		setEditType(null);
		setEditNumber(null);
		setTelefone(new Telefone());
	}

	public void acaoCancelarEdicao(){
		renew();
		setSucessEdit(false);
	}
	
	public void acaoCarregarEdicao(){
		setEditType(getTelefone().getTipoTelefone());
		setEditNumber(getTelefone().getNumero());
	}
	
	public void acaoEditar(){
		setSucessEdit(false);
		if(validarSalvar(getSaidaEditarContato())){
			salvarTelefone();
			renew();
		}
	}
	
	public void acaoInserirTelefone() {
		if(validarSalvar(getSaidaContato())){
			setTelefone(new Telefone());
			salvarTelefone();
			getTelefones().add(getTelefone());
			renew();
		}
	}

	private boolean validarSalvar(String saida){
		this.validatorNumber.setValue(getEditNumber());
		this.validatorType.setValue(getEditType());
		boolean valid = true;
		if(!validatorNumber.isValid()){
			this.view.addResourceDynamicMenssage("* Numero : " + validatorNumber.getErrorMessage(),	saida);
			valid = false;
		}
		if(!validatorType.isValid()){
			valid = false;
		}
		if(!valid){
			if(validatorType.getSource() instanceof NullValidator ||
					validatorNumber.getSource() instanceof NullValidator ||
					validatorNumber.getSource() instanceof EmptyValidator){
				this.view.addResourceDynamicMenssage("* Tipo e Numero são obrigatórios !",	saida);
			}
		}
		return valid;
	}
	
	private void salvarTelefone(){
		getTelefone().setNumero(getEditNumber());
		getTelefone().setTipoTelefone(getEditType());
		setSucessEdit(true);
	}
	
	public void acaoRemoverTelefone() {
		if (this.telefone != null){
			getTelefones().remove(this.telefone);
			setTelefone(new Telefone());
		}
	}

	public Telefone getTelefone() {
		return this.telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Telefone> getTelefones() {
		return this.telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}	

	public TiposTelefone getEditType() {
		return editType;
	}

	public void setEditType(TiposTelefone editType) {
		this.editType = editType;
	}
	
	public String getEditNumber() {
		return editNumber;
	}

	public void setEditNumber(String editNumber) {
		this.editNumber = editNumber;
	}

	public void setSaidaContato(String saidaContato) {
		this.saidaContato = saidaContato;
	}	
	
	public boolean getSucessEdit() {
		return sucessEdit;
	}

	public void setSucessEdit(boolean sucessEdit) {
		this.sucessEdit = sucessEdit;
	}

	private String getSaidaEditarContato(){
		if(saidaEditarContato == null){
			buildSaidaEditarContato();
		}
		return saidaEditarContato;
	}	

	private String getSaidaContato() {
		if(saidaContato == null){
			buildSaidaContato();
		}
		return saidaContato;
	}
	
	
}
