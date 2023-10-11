package com.cbo.mongo.to.t24.services.impl;

import com.cbo.mongo.to.t24.persistence.models.AccountInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Service
public class SoapClient {

    @Value("${ecx.password}")
    private String password;

    @Value("${ecx.username}")
    private String username;

    public AccountInfo sendRequest(String accountNumber) throws UnirestException {


        AccountInfo accountInfo = new AccountInfo();
        try{
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://10.1.245.45:8080/TWSECX/services")
                .header("Content-Type", "text/xml")
                .body(getBody(accountNumber))
                .asString();


        String xmlString = response.getBody();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(xmlString));

        Document doc = builder.parse(src);
        if (doc.getElementsByTagName("messages").getLength() != 0){
            return null;
        }

        NodeList acctNumberList = doc.getElementsByTagName("ns2:ACCTNUMBER");
        NodeList shortTitleList = doc.getElementsByTagName("ns2:SHORTTITLE");
        NodeList workingBalanceList = doc.getElementsByTagName("ns2:WORKINGBALANCE");


            for (int i = 0; i < acctNumberList.getLength(); i++) {
                Element acctNumberElement = (Element) acctNumberList.item(i);
                Element shortTitleElement = (Element) shortTitleList.item(i);
                Element workingBalanceElement = (Element) workingBalanceList.item(i);
                acctNumberElement.getTextContent();
                String shortTitle = shortTitleElement.getTextContent();
                String workingBalance = workingBalanceElement.getTextContent();

                if (workingBalance == null || workingBalance.isEmpty()) {
                    accountInfo.setAmount(0.00);

                } else {
                    accountInfo.setAmount(Double.valueOf(workingBalance));
                }


                accountInfo.setAccountNumber(accountNumber);
                accountInfo.setFullName(shortTitle);
                return accountInfo;
            }
            return null;

        }catch (UnirestException | ParserConfigurationException | IOException | SAXException e){
            throw new UnirestException("Failed on request to t24 server");
        }
    }


    private String getBody(String accountNumber){

        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tws=\"http://temenos.com/TWSECX\">" +
                "\r\n   <soapenv:Header/>" +
                "\r\n   <soapenv:Body>" +
                "\r\n      <tws:WSECXACCT>" +
                "\r\n         <WebRequestCommon>" +
                "\r\n            <!--Optional:-->" +
                "\r\n            <company></company>" +
                "\r\n            <password>"+password+"</password>" +
                "\r\n            <userName>"+username+"</userName>" +
                "\r\n         </WebRequestCommon>" +
                "\r\n         <ECXACCTLISTType>" +
                "\r\n            <!--Zero or more repetitions:-->" +
                "\r\n            <enquiryInputCollection>" +
                "\r\n               <!--Optional:-->" +
                "\r\n               <columnName>ACCOUNT.NUMBER</columnName>" +
                "\r\n               <!--Optional:-->" +
                "\r\n               <criteriaValue>"+accountNumber+"</criteriaValue>" +
                "\r\n               <!--Optional:-->" +
                "\r\n               <operand>EQ</operand>" +
                "\r\n            </enquiryInputCollection>" +
                "\r\n         </ECXACCTLISTType>" +
                "\r\n      </tws:WSECXACCT>" +
                "\r\n   </soapenv:Body>" +
                "\r\n</soapenv:Envelope>";
    }

}
