package br.ueg.openodonto.dominio;

import br.com.vitulus.simple.jdbc.annotation.Column;
import br.com.vitulus.simple.jdbc.annotation.ForwardKey;
import br.com.vitulus.simple.jdbc.annotation.Inheritance;
import br.com.vitulus.simple.jdbc.annotation.Table;
import br.ueg.openodonto.util.WordFormatter;

@Table(name = "usuarios")
@Inheritance(joinFields = { @ForwardKey(tableField = "id_pessoa", foreginField = "id") })
public class Usuario extends Pessoa {

	private static final long serialVersionUID = -8779459291420609676L;

	@Column(name = "user")
	private String user;

	@Column(name = "senha")
	private String senha;
	
	public Usuario() {
	}
	
	public Usuario(Long codigo) {
		super(codigo);
	}
	
	public Usuario(String user, String senha) {
		this.user = user;
		this.senha = senha;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeApresentacao() {
		return WordFormatter.formatarNome(getNome());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [senha=" + senha + ", user=" + user + "]";
	}	

}
