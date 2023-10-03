package com.cbo.mongo.to.t24.persistence.repository;


import com.cbo.mongo.to.t24.persistence.models.AccountInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface AccountInfoRepository extends MongoRepository<AccountInfo, Long> {

    List<AccountInfo> findByAccountNumber(String accountNumber);
}
