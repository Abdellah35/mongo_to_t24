package com.cbo.mongo.to.t24.persistence.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("account_info")
public class AccountInfo{

    @Id
    private ObjectId id;
    private String accountNumber;
    private String fullName;
    private double amount;

}
