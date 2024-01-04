package com.cbo.mongo.to.t24.persistence.repository;


import com.cbo.mongo.to.t24.persistence.models.ReportModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface AccountInfoRepository extends MongoRepository<ReportModel, ObjectId> {

}
