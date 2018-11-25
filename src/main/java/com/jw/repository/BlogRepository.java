package com.jw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jw.model.Blog;

public interface BlogRepository extends MongoRepository<Blog, String> {
	
	 public Blog findBySubTechs_subTech(String subTech);
	
	// @Query(fields="{'tech':1, 'subTech':1}")
	// public List<Blog> findTechAndSubTechnologies();
}
