package com.cbo.mongo.to.t24.services;

import com.cbo.mongo.to.t24.persistence.models.AccountInfo;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

public interface AccountInfoService {
    AccountInfo registerAccountInfo(AccountInfo committeeDTO);

    List<AccountInfo> findAllAccountInfos();

    void updateAccountInfoBySys(List<AccountInfo> accountInfos);

    List<AccountInfo> accountInfosForSys();

    AccountInfo findAccountInfoById(Long id);

    AccountInfo getAccountInf(String accountNumber) throws UnirestException;

    String addAccounts();

}
