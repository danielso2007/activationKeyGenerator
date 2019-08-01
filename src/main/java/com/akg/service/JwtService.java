package com.akg.service;

import com.akg.lang.SigningKeyInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface JwtService {

    public String createJWT(String id, String issuer, String subject, String secretKey, long ttlMillis);

    public void createFile(String token);

    public Claims decodeJWT(String jwt, String secretKey) throws SigningKeyInvalidException, ExpiredJwtException;

    boolean checkForWorldTimeAPITokenExpiration(String jwt, String secretKey);
}
