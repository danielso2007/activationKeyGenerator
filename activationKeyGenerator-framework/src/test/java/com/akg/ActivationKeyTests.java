package com.akg;

import com.akg.service.JwtService;
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

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ActivationKeyTests {

    private final String ISSUER = "me";
    private final String SUBJECT = "me";
    private final String ID = UUID.randomUUID().toString();

    @Value("${file.destination}")
    private String fileDestination;

    private String secretKey = "JASGDJAHSGJDHASGDJHAGSJ";
    private String jwt;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CheckActivationKey checkActivationKey;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void checkActivationKeyTest() {
        this.jwt = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 50000L);
        try {
            jwtService.createFile(this.jwt, fileDestination);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(checkActivationKey.check(fileDestination, secretKey, false));
    }

    @Test
    public void checkActivationKeyTimeoutTest() {
        this.jwt = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 100L);
        try {
            jwtService.createFile(this.jwt, fileDestination);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertFalse(checkActivationKey.check(fileDestination, secretKey, false));
    }
    @Test
    public void checkActivationKeyTimeoutSleepTest() throws InterruptedException {
        this.jwt = jwtService.createJWT(ID, ISSUER, SUBJECT, secretKey, 5000L);
        try {
            jwtService.createFile(this.jwt, fileDestination);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        TimeUnit.SECONDS.sleep(6L);
        Assert.assertFalse(checkActivationKey.check(fileDestination, secretKey, false));
    }

}
