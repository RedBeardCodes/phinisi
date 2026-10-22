package io.github.redbeardcodes;

import java.util.Optional;

import javax.sql.DataSource;

import org.eclipse.microprofile.config.ConfigProvider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class SqliteDatasource {
	DataSource ds;

	@PostConstruct
	public void setup() {
		String jdbcUrl = ConfigProvider.getConfig().getValue("phinisi.jdbc.jdbc_url", String.class);
		Optional<String> username = ConfigProvider.getConfig().getOptionalValue("phinisi.jdbc.username", String.class);
		Optional<String> password = ConfigProvider.getConfig().getOptionalValue("phinisi.jdbc.password", String.class);
		Optional<Integer> maxPoolSize = ConfigProvider.getConfig().getOptionalValue("phinisi.jdbc.max_pool_size", Integer.class);
		Optional<Long> connectionTimeoutMs = ConfigProvider.getConfig().getOptionalValue("phinisi.jdbc.connection_timeout_ms", Long.class);
		Optional<Boolean> isAutoCommit = ConfigProvider.getConfig().getOptionalValue("phinisi.jdbc.auto_commit", Boolean.class);
		
		HikariConfig hkConfig = new HikariConfig();
		hkConfig.setJdbcUrl(jdbcUrl);
		hkConfig.setUsername(username.orElse(""));
		hkConfig.setPassword(password.orElse(""));
		hkConfig.setMaximumPoolSize(maxPoolSize.orElse(4));
		hkConfig.setConnectionTimeout(connectionTimeoutMs.orElse(1000l));
		hkConfig.setAutoCommit(isAutoCommit.orElse(true));
		hkConfig.setDriverClassName("org.sqlite.JDBC");
		ds = new HikariDataSource(hkConfig);
	}

	@ApplicationScoped
	@Produces
	public DataSource getDataSource() {
		return this.ds;
	}
}
