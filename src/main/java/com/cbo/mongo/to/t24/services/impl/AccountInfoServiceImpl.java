package com.cbo.mongo.to.t24.services.impl;

import com.cbo.mongo.to.t24.persistence.models.AccountInfo;
import com.cbo.mongo.to.t24.persistence.repository.AccountInfoRepository;
import com.cbo.mongo.to.t24.services.AccountInfoService;
import com.cbo.mongo.to.t24.utils.AccountNumbersList;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("accountInfoService")
@Transactional
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private SoapClient soapClient;

    @Override
    public AccountInfo registerAccountInfo(AccountInfo accountInfo) {

        if(accountInfo.getFullName() == null){
            return null;
        }else{
            List<AccountInfo> accountInfoByAcc = accountInfoRepository.findByAccountNumber(accountInfo.getAccountNumber());
            if(accountInfoByAcc.isEmpty()){

                return accountInfoRepository.save(accountInfo);
            }
        }return null;


    }

    @Override
    public List<AccountInfo> findAllAccountInfos() {

        return accountInfoRepository.findAll(Sort.by("organizationName").ascending());
    }

    @Override
    public void updateAccountInfoBySys(List<AccountInfo> accountInfos) {
        accountInfoRepository.saveAll(accountInfos);
    }

    @Override
    public List<AccountInfo> accountInfosForSys() {
        return accountInfoRepository.findAll();
    }

    @Override
    public AccountInfo findAccountInfoById(Long id) {

        return accountInfoRepository.findById(id).orElse(null);
    }

    @Override
    public AccountInfo getAccountInf(String accountNumber) throws UnirestException {
        try {
            return soapClient.sendRequest(accountNumber);
        }catch (UnirestException ex){
            throw new UnirestException("Unable to read t24 server");
        }
    }

    @Override
    public String addAccounts() {
        List<String> accountNumbers= AccountNumbersList.accountNumbers;

        accountNumbers.forEach(accountNumber -> {
            try {
                AccountInfo accountInfo = getAccountInf(accountNumber);
                registerAccountInfo(accountInfo);
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
        });

        return "successfully added";
    }
}
