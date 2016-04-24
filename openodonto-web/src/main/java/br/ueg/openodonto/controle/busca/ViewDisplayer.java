package br.ueg.openodonto.controle.busca;

import br.ueg.openodonto.servico.busca.MessageDisplayer;
import br.ueg.openodonto.visao.ApplicationView;

public class ViewDisplayer implements MessageDisplayer{

	private String           output;	
	private ApplicationView  view;
	
	public ViewDisplayer(String output,ApplicationView view) {
		this.output = output;
		this.view = view;
	}
	public ViewDisplayer(ApplicationView view) {
		this(null,view);
	}
	@Override
	public void display(String message) {
		display(message, output);
	}		
	public void display(String message,String output) {
		getView().addResourceDynamicMenssage(message, output);
	}		
	@Override
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	@Override
	public ApplicationView getView() {
		return view;
	}
	
}
