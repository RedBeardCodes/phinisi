package io.github.redbeardcodes;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SigningKeyProvider {
	@ConfigProperty(name = "phinisi.jwt.publickey")
	RSAPublicKey publicKey;

	@ConfigProperty(name = "phinisi.jwt.privatekey")
	RSAPrivateKey privateKey;

	@ConfigProperty(name = "phinisi.jwt.issuer")
	String issuer;
	@ConfigProperty(name = "phinisi.jwt.subject")
	String subject;
	@ConfigProperty(name = "phinisi.jwt.audience")
	String audience;

	public String createJwt(String repository) {
		Algorithm algorithm = Algorithm.RSA256PSS(null, privateKey);
		Instant now = Instant.now();
		Instant expires = now.plus(5, ChronoUnit.MINUTES);
		String token = JWT.create()
			.withIssuer(issuer)
			.withSubject(subject)
			.withAudience(audience)
			.withIssuedAt(now)
			.withNotBefore(now)
			.withExpiresAt(expires)
			.withJWTId(UUID.randomUUID().toString())
			.withPayload(Map.of("access", List.of(
				Map.of(
					"type", "repository",
					"name", repository,
					"actions", List.of("pull")
				)
			)))
			.sign(algorithm);
		return token;
	}
}
