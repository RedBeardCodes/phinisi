package io.github.redbeardcodes;

import javax.sql.DataSource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

	@Inject
	DataSource ds;

}
