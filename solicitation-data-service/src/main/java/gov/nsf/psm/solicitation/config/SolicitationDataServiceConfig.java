package gov.nsf.psm.solicitation.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import gov.nsf.psm.solicitation.dao.SolicitationDataServiceDAO;
import gov.nsf.psm.solicitation.dao.SolicitationDataServiceDAOImpl;
import gov.nsf.psm.solicitation.service.SolicitationDataService;
import gov.nsf.psm.solicitation.service.SolicitationDataServiceImpl;

@Configuration
@EnableConfigurationProperties({FLConnectionSettings.class,PARSConnectionSettings.class})
public class SolicitationDataServiceConfig {

    @Autowired
    private FLConnectionSettings databaseSettings;
    
    @Autowired
    private PARSConnectionSettings parsdatabaseSettings;

    @Bean(name = "fldb")
    @Primary
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseSettings.getDriver());
        dataSource.setUrl(databaseSettings.getUrl());
        dataSource.setUsername(databaseSettings.getUsername());
        dataSource.setPassword(databaseSettings.getPassword());
        return dataSource;
    }
    
    @Bean(name = "pars")
    public DataSource getParsDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(parsdatabaseSettings.getDriver());
        dataSource.setUrl(parsdatabaseSettings.getUrl());
        dataSource.setUsername(parsdatabaseSettings.getUsername());
        dataSource.setPassword(parsdatabaseSettings.getPassword());
        return dataSource;
    }
    

   /* @Bean
    public JdbcTemplate psmFLJdbcTemplate(@Qualifier("fldb")) {
        return new JdbcTemplate( getDataSource());
    }
    */
    @Bean(name = "namedPsmJdbcTemplate")
    @Qualifier("fldb")
	public NamedParameterJdbcTemplate namedPsmJdbcTemplate( ) {
		return new NamedParameterJdbcTemplate(getDataSource());
	}

    @Bean(name = "psmFLJdbcTemplate")
    @Qualifier("fldb")
	public JdbcTemplate psmFLJdbcTemplate( ) {
		return new JdbcTemplate(getDataSource());
	}
    
    
    @Bean(name = "psmPARSJdbcTemplate")
    @Qualifier("pars")
  	public JdbcTemplate psmPARSJdbcTemplate( ) {
  		return new JdbcTemplate(getParsDataSource());
  	}
    
    
    //psmPARSJdbcTemplate
    
    
    @Bean
    public SolicitationDataServiceDAO solicitationDao() {
        return new SolicitationDataServiceDAOImpl();
    }

    @Bean
    public SolicitationDataService solicitationDataService() {
        return new SolicitationDataServiceImpl();
    }

    
    @Bean
    BasicAuthenticationFilter basicAuthFilter(AuthenticationManager authenticationManager, BasicAuthenticationEntryPoint basicAuthEntryPoint) {
      return new BasicAuthenticationFilter(authenticationManager, basicAuthEntryPoint());
    }
    
    @Bean
    BasicAuthenticationEntryPoint basicAuthEntryPoint() {
      BasicAuthenticationEntryPoint bauth = new BasicAuthenticationEntryPoint();
      bauth.setRealmName("solicitationData");
      return bauth;
    }
    
}
