package br.ueg.openodonto.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;

import br.com.vitulus.simple.jdbc.dao.AbstractDao;
import br.com.vitulus.simple.jdbc.database.ConnectionFactory;
import br.com.vitulus.simple.jdbc.setup.DbcpDatasourceSetup;
import br.com.vitulus.simple.jdbc.setup.DbcpDatasourceSetup.DBCPPoolType;
import br.com.vitulus.simple.jdbc.setup.DbcpDatasourceSetup.DbcpProviderType;
import br.com.vitulus.simple.jdbc.setup.StartupConfig;

public class OpenOdontoLoadListener implements ServletContextListener {

	private final static String CONTEXT_NAME = "openodonto";
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	@SuppressWarnings("deprecation")
	public void contextInitialized(ServletContextEvent contextEvent) {
		String catalinaHome = System.getProperty("catalina.base", "./");
		if(!catalinaHome.equals("./")){
			catalinaHome += File.separator + "conf";
		}
		File cfgFile = new File(catalinaHome,"openodonto.properties");
		if(!cfgFile.exists() && !cfgFile.canWrite()){
			InputStream in = this.getClass().getResourceAsStream("/openodonto.properties");
			try {
				OutputStream out = new FileOutputStream(cfgFile);
				IOUtils.copy(in, out);
			} catch (Exception e) {
			}
		}
		try (InputStream in = new FileInputStream(cfgFile)){
			StartupConfig config = StartupConfig.getConfigProperties(in);		
			DataSource datasource = setupLocalDatasource(CONTEXT_NAME, config);
			AbstractDao.registerDatasource(datasource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param dataSourceName
	 * @param dsFullPath
	 * @return
	 * @throws SQLException
	 */
	protected DataSource setupContainerDatasource(String dataSourceName, String dsFullPath) throws SQLException {
		return ConnectionFactory.create(dataSourceName, dsFullPath).getDataSource();
	}

	/**
	 * 
	 * @param dataSourceName
	 * @param config
	 * @return
	 * @throws SQLException
	 */
	protected DataSource setupLocalDatasource(String dataSourceName , StartupConfig config) throws SQLException{
		DbcpProviderType provider	= DbcpProviderType.APACHE_COMMONS;
		DBCPPoolType poolType		= DBCPPoolType.BASIC;
		DbcpDatasourceSetup.setup(config, provider, poolType);
		return ConnectionFactory.create(dataSourceName, config).getDataSource();
	}

}
