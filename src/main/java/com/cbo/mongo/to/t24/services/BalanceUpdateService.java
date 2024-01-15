package com.cbo.mongo.to.t24.services;


import com.cbo.mongo.to.t24.persistence.models.ReportModel;
import com.cbo.mongo.to.t24.services.impl.SoapClient;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class BalanceUpdateService {

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private SoapClient soapClient;



    public String updateBalance() throws IOException {
        List<ReportModel> updatedReportModel = new ArrayList<>();
        try {

            ReportModel reportModel = new ReportModel();

            ReportModel updatedAcc = updateBalanceT24();
            reportModel.setNoTr(updatedAcc.getNoTr());
            reportModel.setNoDebit(updatedAcc.getNoDebit());
            reportModel.setNoCredit(updatedAcc.getNoCredit());
            reportModel.setTtlAmount(updatedAcc.getTtlAmount());
            reportModel.setTtlCrAmt(updatedAcc.getTtlCrAmt());
            reportModel.setTtlDrAmt(updatedAcc.getTtlDrAmt());
            reportModel.setLastModified(LocalDateTime.now());
            updatedReportModel.add(reportModel);
            accountInfoService.updateAccountInfoBySys(updatedReportModel);

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public ReportModel updateBalanceT24() throws UnirestException {

        return soapClient.sendRequest();

    }

}
