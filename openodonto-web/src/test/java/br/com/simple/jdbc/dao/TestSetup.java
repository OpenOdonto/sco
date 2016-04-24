package br.com.simple.jdbc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.naming.NamingException;

import br.com.vitulus.simple.jdbc.setup.DbcpDatasourceSetup;
import br.com.vitulus.simple.jdbc.setup.DbcpDatasourceSetup.DBCPPoolType;
import br.com.vitulus.simple.jdbc.setup.DbcpDatasourceSetup.DbcpProviderType;
import br.com.vitulus.simple.jdbc.setup.StartupConfig;


public class TestSetup {

	public static void setup()throws NamingException{
		try (InputStream in = new FileInputStream(new File("openodonto.properties"))){
			StartupConfig config = StartupConfig.getConfigProperties(in);		
			DbcpDatasourceSetup.setup(config, DbcpProviderType.APACHE_COMMONS, DBCPPoolType.BASIC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
