package br.ueg.openodonto.util;

/**
 * @author vinicius.rodrigues
 * 
 */
public class MessageBundle {

    public enum MSG_TIPO {
	dinamica, resource;
    }

    private MSG_TIPO tipo;

    private String mensagem;

    private String outComponent;

    public MessageBundle(MSG_TIPO tipo, String mensagem, String outComponent) {
	this.tipo = tipo;
	this.mensagem = mensagem;
	this.outComponent = outComponent;
    }

    public String getMensagem() {
	return this.mensagem;
    }

    public void setMensagem(String mensagem) {
	this.mensagem = mensagem;
    }

    public String getOutComponent() {
	return this.outComponent;
    }

    public void setOutComponent(String outComponent) {
	this.outComponent = outComponent;
    }

    public MSG_TIPO getTipo() {
	return this.tipo;
    }

    public void setTipo(MSG_TIPO tipo) {
	this.tipo = tipo;
    }

}
