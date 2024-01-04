package com.cbo.mongo.to.t24.services;


import com.cbo.mongo.to.t24.persistence.models.ReportModel;
import com.cbo.mongo.to.t24.services.impl.SoapClient;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
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

            List<ReportModel> listAccount = accountInfoService.accountInfosForSys();
            System.out.println(listAccount);
            for (ReportModel reportModel : listAccount) {

                ReportModel updatedAcc = updateBalance(1L);

                if (updatedAcc != null){
                    reportModel.setNoTr(updatedAcc.getNoTr());
                    reportModel.setNoDebit(updatedAcc.getNoDebit());
                    reportModel.setNoCredit(updatedAcc.getNoCredit());
                    reportModel.setTtlAmount(updatedAcc.getTtlAmount());
                    reportModel.setTtlCrAmt(updatedAcc.getTtlCrAmt());
                    reportModel.setTtlDrAmt(updatedAcc.getTtlDrAmt());

                }
                updatedReportModel.add(reportModel);
            }
            if (listAccount.isEmpty()){
                accountInfoService.updateAccountInfoBySys(updatedReportModel);
            }
            accountInfoService.updateAccountInfoBySys(updatedReportModel);

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public ReportModel updateBalance(Long accountNumber) throws UnirestException {

        return soapClient.sendRequest(accountNumber);

    }

}
