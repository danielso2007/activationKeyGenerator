package com.akg;

import com.akg.lang.SigningKeyInvalidException;
import com.akg.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ActivationKeyGeneratorApplicationTests {

    private final String ISSUER = "me";
    private final String SUBJECT = "me";
    private final String ID = UUID.randomUUID().toString();

    @Value("${secret.key}")
    private String secretKey;

    @Value("${file.destination}")
    private String fileDestination;

    @Autowired
    private JwtService jwtService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void jwtTokenGenerationTest() {
        String token = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, -1L);
        Assert.assertNotNull(token);
    }

    @Test
    public void decodeJWTTest() {
        Claims claims = null;
        String token = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, -1L);
        Assert.assertNotNull(token);
        try {
            claims = jwtService.decodeJWT(token, secretKey);
        } catch (SigningKeyInvalidException | ExpiredJwtException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        Assert.assertNotNull(claims);
        Assert.assertEquals(claims.getIssuer(), ISSUER);
        Assert.assertEquals(claims.getSubject(), SUBJECT);
        Assert.assertEquals(claims.getId(), ID);
    }

    @Test
    public void decodeJWTExpiredJwtExceptionTest() {
        String token = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 1L);
        Assert.assertNotNull(token);
        try {
            jwtService.decodeJWT(token, secretKey);
        } catch (SigningKeyInvalidException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (ExpiredJwtException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void decodeJWTSigningKeyInvalidExceptionTest() {
        String token = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 1L);
        Assert.assertNotNull(token);
        try {
            jwtService.decodeJWT(token, "KJASHDKH$#@&%#$");
        } catch (SigningKeyInvalidException e) {
            Assert.assertTrue(true);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void checkForWorldTimeAPITokenExpirationTest() {
        String secretKey = "KJAHSKJDHKJASHDKJAS";
        String jwt = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 10000L);
        Assert.assertTrue(jwtService.checkForWorldTimeAPITokenExpiration(jwt, secretKey));
    }

    @Test
    public void checkForWorldTimeAPITokenExpirationIsTrueTest() {
        String jwt = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 1000L);
        try {
            Thread.sleep(1000);
            boolean check = jwtService.checkForWorldTimeAPITokenExpiration(jwt, secretKey);
            Assert.assertFalse(check);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void createFileTest() {
        try {
            String jwt = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 2000L);
            jwtService.createFile(jwt, fileDestination);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void readActivationKeyFileTest() {
        String jwt = null;
        try {
            jwt = jwtService.readActivationKeyFile(fileDestination);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertNotNull(jwt);
    }

    @Test
    public void creatingAndReadingActivationKeyFileTest() {
        String jwt = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 2000L);
        try {
            jwtService.createFile(jwt, fileDestination);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        String jwtReadFromFile = null;
        try {
            jwtReadFromFile = jwtService.readActivationKeyFile(fileDestination);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertEquals(jwt, jwtReadFromFile);
    }
}
