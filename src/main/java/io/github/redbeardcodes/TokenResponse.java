package io.github.redbeardcodes;

import java.time.Instant;

public record TokenResponse(String token, String accessToken, int expiresIn, Instant issuedAt) {
}
