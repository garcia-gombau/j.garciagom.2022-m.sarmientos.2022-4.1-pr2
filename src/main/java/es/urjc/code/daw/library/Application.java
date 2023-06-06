package es.urjc.code.daw.library;

import es.urjc.code.daw.library.features.AppFeatures;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.togglz.core.Feature;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.spi.FeatureProvider;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public FeatureProvider featureProvider() {
		return new EnumBasedFeatureProvider(AppFeatures.class);
	}
}