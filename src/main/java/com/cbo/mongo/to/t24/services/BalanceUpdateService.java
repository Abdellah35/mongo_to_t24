package com.cbo.mongo.to.t24.services;


import com.cbo.mongo.to.t24.persistence.models.AccountInfo;
import com.cbo.mongo.to.t24.services.impl.SoapClient;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BalanceUpdateService {

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private SoapClient soapClient;



    public String updateBalance() throws JAXBException, IOException {
        List<AccountInfo> updatedAccountInfo = new ArrayList<>();
        try {

            List<AccountInfo> listAccount = accountInfoService.accountInfosForSys();

            for (AccountInfo accoount : listAccount) {

                AccountInfo updatedAcc = updateBalance(accoount.getAccountNumber());

                if (updatedAcc != null){
                    accoount.setAmount(updatedAcc.getAmount());
                }
                updatedAccountInfo.add(accoount);
            }
            accountInfoService.updateAccountInfoBySys(updatedAccountInfo);

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public AccountInfo updateBalance(String accountNumber) throws UnirestException {

        return soapClient.sendRequest(accountNumber);

    }

}
