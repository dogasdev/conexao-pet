package br.com.adotapet.adota_pet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            JwtEncoder jwtEncoder
    ) {
        this.jwtEncoder = jwtEncoder;
    }

    public String gerarToken(String identificador) {

        Instant momentoAtual = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(identificador)
                .issuedAt(momentoAtual)
                .expiresAt(momentoAtual.plus(2, ChronoUnit.HOURS))
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtEncoderParameters parametros = JwtEncoderParameters.from(header, claims);

        return jwtEncoder.encode(parametros).getTokenValue();
    }
}