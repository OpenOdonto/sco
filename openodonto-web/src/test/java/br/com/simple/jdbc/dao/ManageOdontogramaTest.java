package br.com.simple.jdbc.dao;

import java.sql.Date;

import br.com.vitulus.simple.jdbc.EntityManager;
import br.ueg.openodonto.dominio.OdontogramaDente;
import br.ueg.openodonto.dominio.OdontogramaDenteProcedimento;
import br.ueg.openodonto.dominio.Paciente;
import br.ueg.openodonto.dominio.Procedimento;
import br.ueg.openodonto.dominio.constante.Dente;
import br.ueg.openodonto.dominio.constante.FaceDente;
import br.ueg.openodonto.dominio.constante.TipoStatusProcedimento;
import br.ueg.openodonto.persistencia.dao.DaoPaciente;


public class ManageOdontogramaTest {

	public static void main(String[] args) throws Exception {

		TestSetup.setup();		
		
		EntityManager<Paciente> daoPaciente = new DaoPaciente();
		Paciente paciente = new Paciente(2L);
		daoPaciente.load(paciente);	
		
		
		OdontogramaDente od = new OdontogramaDente(Dente.DENTE_38, FaceDente.OCLUSAL);		
		OdontogramaDenteProcedimento odp = new OdontogramaDenteProcedimento();
		odp.setData(new Date(System.currentTimeMillis()));
		odp.setStatus(TipoStatusProcedimento.NAO_REALIZADO);
		od.getProcedimentosMap().put(odp, new Procedimento(5L));
		paciente.getOdontogramas().get(0).getOdontogramaDentes().add(od);		
		

		/*
		for(OdontogramaDenteProcedimento odp : paciente.getOdontogramas().get(0).getOdontogramaDentes().last().getProcedimentosMap().keySet()){
			odp.setValor(150.0F);
			odp.setStatus(TipoStatusProcedimento.PAGO);
		}
		*/
		
		//paciente.getOdontogramas().get(0).getOdontogramaDentes().remove(paciente.getOdontogramas().get(0).getOdontogramaDentes().last());	
		
		
		daoPaciente.alterar(paciente);
		daoPaciente.closeConnection();
	}
	
}
