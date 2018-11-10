package com.jw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jw.model.TechInfo;

public interface TechInfoRepository extends MongoRepository<TechInfo, String> {

}
