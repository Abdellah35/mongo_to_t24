package com.cbo.mongo.to.t24.services;

import com.cbo.mongo.to.t24.persistence.models.ReportModel;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

public interface AccountInfoService {
    List<ReportModel> findAllAccountInfos();

    void updateAccountInfoBySys(List<ReportModel> reportModels);

    List<ReportModel> accountInfosForSys();

    ReportModel getAccountInf(Long id) throws UnirestException;

}
