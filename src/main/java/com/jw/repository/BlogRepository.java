package com.jw.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jw.model.Blog;

public interface BlogRepository extends MongoRepository<Blog, String> {
	
	public Blog findBySubTech(String subTech);
	
	@Query(fields="{'tech':1, 'subTech':1}")
	public List<Blog> findTechAndSubTechnologies();
}
