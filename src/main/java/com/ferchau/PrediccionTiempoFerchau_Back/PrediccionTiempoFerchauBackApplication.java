package com.ferchau.PrediccionTiempoFerchau_Back;

import com.ferchau.PrediccionTiempoFerchau_Back.application.configuration.ConfigurationPrediccionTiempoMunicipios;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableConfigurationProperties(ConfigurationPrediccionTiempoMunicipios.class)
public class PrediccionTiempoFerchauBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrediccionTiempoFerchauBackApplication.class, args);
	}

}
