package io.github.redbeardcodes;

import org.eclipse.microprofile.config.ConfigProvider;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("token")
public class AuthResource {
	
	private String realm;

	private static final String DOCKER_API_VERSION_HEADER = "Docker-Distribution-Api-Version";
	private static final String DOCKER_API_VERSION_VALUE = "registry/2.0";
	private static final String CHALLENGE_HEADER = "WWW-Authenticate";

	@PostConstruct
	public void setup() {
		realm = ConfigProvider.getConfig().getValue("realm", String.class);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateAndAuthorize(
		@QueryParam("service") String service,
		@QueryParam("scope") String scope,
		@QueryParam("account") String account,
		@QueryParam("client_id") String clientId,
		@QueryParam("offline_token") String offlineToken
	) {
		StringBuilder errorSbr = new StringBuilder();
		errorSbr.append("Bearer ");
		errorSbr.append("realm=https://").append(realm);
		if (service != null)
			errorSbr.append(",service=")
				.append(service);
		if (scope != null)
			errorSbr.append(",scope=")
				.append(scope);
		return Response.status(Status.UNAUTHORIZED)
			.header(DOCKER_API_VERSION_HEADER, DOCKER_API_VERSION_VALUE)
			.header(CHALLENGE_HEADER, errorSbr.toString())
			.entity("{\"errors\":[{\"code\":\"UNAUTHORIZED\",\"message\":\"authentication required\"}]}")
			.build();
	}
}
