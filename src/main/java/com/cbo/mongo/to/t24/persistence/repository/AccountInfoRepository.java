package com.cbo.mongo.to.t24.persistence.repository;


import com.cbo.mongo.to.t24.persistence.models.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {

    @Query(" SELECT AI FROM AccountInfo AI WHERE AI.accountNumber= :accountNumber ")
    List<AccountInfo> findByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query(" SELECT AI FROM AccountInfo AI WHERE AI.organizationName= :orgName")
    List<AccountInfo> findByOrgName(@Param("orgName") String orgName);
}*/

public interface AccountInfoRepository extends MongoRepository<AccountInfo, Long> {

    List<AccountInfo> findByAccountNumber(String accountNumber);

    List<AccountInfo> findByOrgName(String orgName);
}
