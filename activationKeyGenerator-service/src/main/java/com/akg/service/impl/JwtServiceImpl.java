package com.akg.service.impl;

import com.akg.lang.SigningKeyInvalidException;
import com.akg.lang.WorldTimeApi;
import com.akg.service.JwtService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    @Value("${worldtimeapi.url}")
    private String worldtimeapiUrl;

    @Override
    public String createJWT(String id, String issuer, String subject, String secretKey, long ttlMillis) {
        logger.info(String.format("Creating token: %s", id));
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
            logger.info(String.format("Adding Expiration Time: %d", ttlMillis));
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        logger.info(String.format("Token created: %s", builder.compact().trim()));

        return builder.compact().trim();
    }

    @Override
    public void createFile(String token, String fileDestination) throws IOException {
        logger.info("Creating activation key file...");
        new File(fileDestination).deleteOnExit();
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileDestination));
        writer.write(token.trim());
        writer.close();
        logger.info(String.format("File created in: %s", fileDestination));
    }

    @Override
    public String readActivationKeyFile(String fileDestination) {
        logger.info(String.format("Reading file: %s", fileDestination));
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileDestination))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return sb.toString().trim();
    }

    @Override
    public Claims decodeJWT(String jwt, String secretKey) throws SigningKeyInvalidException, ExpiredJwtException {
        logger.info("Decode jwt...");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(jwt).getBody();
            return claims;
        } catch (ExpiredJwtException exp) {
            logger.error(exp.getMessage(), exp);
            throw exp;
        } catch (SignatureException ex) {
            logger.error(ex.getMessage(), ex);
            throw new SigningKeyInvalidException(ex.getMessage());
        }
    }

    @Override
    public boolean checkForWorldTimeAPITokenExpiration(String jwt, String secretKey) {
        logger.info("Check For WorldTime API Token Expiration...");
        Claims claims = null;
        try {
            claims = decodeJWT(jwt, secretKey);
        } catch (ExpiredJwtException exp) {
            logger.error(exp.getMessage(), exp);
            return false;
        } catch (SigningKeyInvalidException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        if (claims == null) {
            return false;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            WorldTimeApi worldTimeApi = restTemplate.getForObject(worldtimeapiUrl, WorldTimeApi.class);

            if (worldTimeApi.getDatetime().after(claims.getExpiration())) {
                logger.info("Token has expired.");
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
