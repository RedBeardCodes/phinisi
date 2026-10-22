package io.github.redbeardcodes;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("token")
public class AuthResource {
	
	private String realm;

	private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

	@PostConstruct
	public void setup() {
		realm = ConfigProvider.getConfig().getValue("phinisi.realm", String.class);
	}

	/**
	 * @param service the service requesting the authentication/authorization
	 * @param scope scopes requested by the registry
	 * @return {@link Response} currently a dummy response
	 **/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateAndAuthorize(
		@QueryParam("service") String service,
		@QueryParam("scope") List<String> scope,
		@QueryParam("account") String account,
		@QueryParam("client_id") String clientId,
		@QueryParam("offline_token") String offlineToken
	) {
		logger.info("Service: " + service);
		for (String s : scope)
			logger.info("Scope: " + s);
		logger.info("Account: " + account);
		logger.info("Client ID: " + clientId);
		return Response.ok().build();
	}
}
