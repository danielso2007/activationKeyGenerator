package com.akg.service;

import com.akg.lang.SigningKeyInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;

public interface JwtService {

    public String createJWT(String id, String issuer, String subject, String secretKey, long ttlMillis);

    public void createFile(String token, String fileDestination) throws IOException;

    String readActivationKeyFile(String fileDestination);

    public Claims decodeJWT(String jwt, String secretKey) throws SigningKeyInvalidException, ExpiredJwtException;

    boolean checkForWorldTimeAPITokenExpiration(String jwt, String secretKey);
}
