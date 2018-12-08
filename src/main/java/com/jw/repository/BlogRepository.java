package com.jw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jw.model.BlogModel;

public interface BlogRepository extends MongoRepository<BlogModel, String> {
	
	 public BlogModel findBySubTechs_subTech(String subTech);
	
	// @Query(fields="{'tech':1, 'subTech':1}")
	// public List<Blog> findTechAndSubTechnologies();
}
