package br.ueg.openodonto.persistencia;

import br.ueg.openodonto.dominio.Usuario;

/**
 * @author Vinicius
 * 
 */
public interface LoginManager {

    Usuario autenticar(Usuario usuario);
    Usuario autenticar(String usuario, String senha);

}
