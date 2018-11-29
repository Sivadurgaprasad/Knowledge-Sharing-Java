package com.jw.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jw.model.BlogModel;

public interface DropDownRepository extends MongoRepository<BlogModel, String> {

	@Query(value="{}" ,fields="{'blog':1}")
	public List<BlogModel> findAllDropDownTechnologies();
	
	@Query(value="{'id':?0}", fields="{'subTechs':1}")
	public BlogModel findAllDropDownSubTechs(String id);

}