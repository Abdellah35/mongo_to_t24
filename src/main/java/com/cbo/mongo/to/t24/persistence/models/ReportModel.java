package com.cbo.mongo.to.t24.persistence.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("daily_report")
public class ReportModel {

    @Id
    private ObjectId id;

    private String fbusinessDate;
    private String noCredit;
    private String noDebit;
    private String noTr;
    private String ttlAmount;
    private String ttlCrAmt;
    private String ttlDrAmt;

    @LastModifiedDate
    private LocalDateTime lastModified;

}
