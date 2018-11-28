package com.jw.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jw.model.TechInfoModel;

public interface DropDownRepository extends MongoRepository<TechInfoModel, String> {

	@Query(value="{}" ,fields="{'blog':1}")
	public List<TechInfoModel> findAllDropDownTechnologies();
	
	@Query(value="{'id':?0}", fields="{'subTechs':1}")
	public TechInfoModel findAllDropDownSubTechs(String technologyId);
}
