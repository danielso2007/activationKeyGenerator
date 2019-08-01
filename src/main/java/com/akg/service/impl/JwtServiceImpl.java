package com.akg.service.impl;

import com.akg.lang.SigningKeyInvalidException;
import com.akg.lang.WorldTimeApi;
import com.akg.service.JwtService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${worldtimeapi.url}")
    private String worldtimeapiUrl;

    @Override
    public String createJWT(String id, String issuer, String subject, String secretKey, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // Add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    @Override
    public void createFile(String token) {

    }

    @Override
    public Claims decodeJWT(String jwt, String secretKey) throws SigningKeyInvalidException, ExpiredJwtException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(jwt).getBody();
            return claims;
        } catch (ExpiredJwtException exp) {
            throw exp;
        } catch (SignatureException ex) {
            throw new SigningKeyInvalidException(ex.getMessage());
        }
    }

    @Override
    public boolean checkForWorldTimeAPITokenExpiration(String jwt, String secretKey) {
        Claims claims = null;
        try {
            claims = decodeJWT(jwt, secretKey);
        } catch (ExpiredJwtException exp) {
            exp.printStackTrace();
            return false;
        } catch (SigningKeyInvalidException e) {
            e.printStackTrace();
            return false;
        }
        if (claims == null) {
            return false;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            WorldTimeApi worldTimeApi = restTemplate.getForObject(worldtimeapiUrl, WorldTimeApi.class);

            if (worldTimeApi.getDatetime().after(claims.getExpiration())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
