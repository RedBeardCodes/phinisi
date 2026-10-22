package io.github.redbeardcodes;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;

@ApplicationScoped
public class PhinisDataSource {
	@Inject
	DataSource ds;

	public void init(@Observes Startup event) {
		Flyway flyway = Flyway.configure().dataSource(ds).locations("classpath:db/migrations").load();
		flyway.migrate();
	}
}
