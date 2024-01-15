package com.cbo.mongo.to.t24.services.impl;

import com.cbo.mongo.to.t24.persistence.models.ReportModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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

    @Value("${report.password}")
    private String password;

    @Value("${report.username}")
    private String username;

    @Value("${report.uri}")
    private String uri;

    public ReportModel sendRequest() throws UnirestException {


        ReportModel reportModel = new ReportModel();
        try{
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(uri)
                .header("Content-Type", "text/xml")
                .body(getBody())
                .asString();


        String xmlString = response.getBody();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(xmlString));

        Document doc = builder.parse(src);
        if (doc.getElementsByTagName("messages").getLength() != 0){
            return null;
        }

        NodeList NODEBIT = doc.getElementsByTagName("ns2:NODEBIT");
        NodeList TOTALDEBITAMT = doc.getElementsByTagName("ns2:TOTALDEBITAMT");
        NodeList NOCREDIT = doc.getElementsByTagName("ns2:NOCREDIT");
        NodeList TOTALCREDITAMT = doc.getElementsByTagName("ns2:TOTALCREDITAMT");
        NodeList NOFTTR = doc.getElementsByTagName("ns2:NOFTTR");
        NodeList TOTALFTAMT = doc.getElementsByTagName("ns2:TOTALFTAMT");
        System.out.println("hr "+TOTALFTAMT.item(0).getTextContent());

            for (int i = 0; i < NODEBIT.getLength(); i++) {
                Element noOfCredit = (Element) NOCREDIT.item(i);
                Element noOfDebit = (Element) NODEBIT.item(i);
                Element totalCredit = (Element) TOTALCREDITAMT.item(i);
                Element totalDebit = (Element) TOTALDEBITAMT.item(i);
                Element noFTT = (Element) NOFTTR.item(i);
                Element totalAlf = (Element) TOTALFTAMT.item(i);

                reportModel.setTtlDrAmt(totalDebit.getTextContent());
                reportModel.setNoTr(noFTT.getTextContent());
                reportModel.setTtlAmount(totalAlf.getTextContent());
                reportModel.setNoDebit(noOfDebit.getTextContent());
                reportModel.setNoCredit(noOfCredit.getTextContent());
                reportModel.setTtlCrAmt(totalCredit.getTextContent());
                return reportModel;
            }
            return null;

        }catch (UnirestException | ParserConfigurationException | IOException | SAXException e){
            throw new UnirestException("Failed on request to t24 server");
        }
    }


    private String getBody(){

        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tws=\"http://temenos.com/TWSTRANS\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tws:TOTALTRANSACTION>\n" +
                "         <WebRequestCommon>\n" +
                "            <!--Optional:-->\n" +
                "            <company></company>\n" +
                "            <password>"+password+"</password>\n" +
                "            <userName>"+username+"</userName>\n" +
                "         </WebRequestCommon>\n" +
                "         <TRANSCOUNTType>\n" +
                "            <!--Zero or more repetitions:-->\n" +
                "           \n" +
                "         </TRANSCOUNTType>\n" +
                "      </tws:TOTALTRANSACTION>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

}
