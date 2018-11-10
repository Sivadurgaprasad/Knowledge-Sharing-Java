package com.jw.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jw.model.TechInfo;

public interface DropDownRepository extends MongoRepository<TechInfo, String> {

	@Query(value="{}" ,fields="{'blog':1}")
	public List<TechInfo> findAllDropDownTechnologies();
	
	@Query(value="{'id':?0}", fields="{'subTechs':1}")
	public TechInfo findAllDropDownSubTechs(String technologyId);
}
