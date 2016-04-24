package br.ueg.openodonto.controle.exception;

@SuppressWarnings("serial")
public class LoginInvalidoException extends RuntimeException {

    public LoginInvalidoException() {
    }

    public LoginInvalidoException(String messaString) {
	super(messaString);
    }

}
