package com.jw.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

<<<<<<< HEAD
import com.jw.model.Blog;

public interface DropDownRepository extends MongoRepository<Blog, String> {

	@Query(value="{}" ,fields="{'blog':1}")
	public List<Blog> findAllDropDownTechnologies();
	
	@Query(value="{'id':?0}", fields="{'subTechs':1}")
	public Blog findAllDropDownSubTechs(String blog);
=======
import com.jw.model.TechInfoModel;

public interface DropDownRepository extends MongoRepository<TechInfoModel, String> {

	@Query(value="{}" ,fields="{'blog':1}")
	public List<TechInfoModel> findAllDropDownTechnologies();
	
	@Query(value="{'id':?0}", fields="{'subTechs':1}")
	public TechInfoModel findAllDropDownSubTechs(String technologyId);
>>>>>>> c7bd2306febbd18bee65476e96f34e271821d4d0
}
