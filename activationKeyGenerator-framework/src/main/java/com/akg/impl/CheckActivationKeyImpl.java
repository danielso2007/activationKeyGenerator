package com.akg.impl;

import com.akg.CheckActivationKey;
import com.akg.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public final class CheckActivationKeyImpl implements CheckActivationKey {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean check(String fileDestination, String secretKey, boolean exit) {
        String jwtReadFromFile = null;
        try {
            jwtReadFromFile = jwtService.readActivationKeyFile(fileDestination);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exit(exit);
            return false;
        }
        boolean check = jwtService.checkForWorldTimeAPITokenExpiration(jwtReadFromFile, secretKey);
        if (!check) {
            exit(exit);
        }
        return check;
    }

    private void exit(boolean exit) {
        if (exit) {
            System.exit(0);
        }
    }
}
