package io.github.redbeardcodes;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.eclipse.microprofile.config.spi.Converter;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RSAPublicKeyConverter implements Converter<RSAPublicKey> {
	@Override
	public RSAPublicKey convert(String key) throws IllegalArgumentException {
		try {
			String pem = key.replace("-----BEGIN PUBLIC KEY-----", "")
			.replace("-----END PUBLIC KEY-----", "")
			.replaceAll("\\s", "");
			byte[] encoded = Base64.getDecoder().decode(pem);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
			return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to convert string to RSAPublicKey", e);
		}
	}
}
