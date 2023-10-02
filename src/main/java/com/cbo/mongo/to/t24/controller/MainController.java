package com.cbo.mongo.to.t24.controller;

import com.cbo.mongo.to.t24.services.AccountInfoService;
import com.cbo.mongo.to.t24.services.BalanceUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
public class MainController {

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private BalanceUpdateService balanceUpdateService;

    @GetMapping(value = "api/accounts/add")
    public ResponseEntity<String> addAccounts(){
        return new ResponseEntity<>(accountInfoService.addAccounts(), HttpStatus.OK);
    }

    @GetMapping(value = "api/accounts/balance/update")
    public ResponseEntity<String> updateAccounts() throws JAXBException, IOException {

        return new ResponseEntity<>(balanceUpdateService.updateBalance(), HttpStatus.OK);
    }
 }
