package com.akg.service;

import com.akg.lang.SigningKeyInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface JwtService {

    String createJWT(String id, String issuer, String subject, String secretKey, long ttlMillis);

    void createFile(String token, String fileDestination) throws IOException;

    String readActivationKeyFile(String fileDestination) throws FileNotFoundException;

    Claims decodeJWT(String jwt, String secretKey) throws SigningKeyInvalidException, ExpiredJwtException;

    boolean checkForWorldTimeAPITokenExpiration(String jwt, String secretKey);
}
