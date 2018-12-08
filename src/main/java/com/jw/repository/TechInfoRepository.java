package com.jw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jw.model.TechInfoModel;

public interface TechInfoRepository extends MongoRepository<TechInfoModel, String> {

}
