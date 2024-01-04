package com.cbo.mongo.to.t24.services.impl;

import com.cbo.mongo.to.t24.persistence.models.ReportModel;
import com.cbo.mongo.to.t24.persistence.repository.AccountInfoRepository;
import com.cbo.mongo.to.t24.services.AccountInfoService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("accountInfoService")
@Transactional
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private SoapClient soapClient;


    @Override
    public List<ReportModel> findAllAccountInfos() {

        return accountInfoRepository.findAll(Sort.by("fbusinessDate").ascending());
    }

    @Override
    public void updateAccountInfoBySys(List<ReportModel> reportModels) {
        accountInfoRepository.saveAll(reportModels);
    }

    @Override
    public List<ReportModel> accountInfosForSys() {
        return accountInfoRepository.findAll();
    }

    @Override
    public ReportModel getAccountInf(Long id) throws UnirestException {
        try {
            return soapClient.sendRequest(id);
        }catch (UnirestException ex){
            throw new UnirestException("Unable to read t24 server");
        }
    }

}
