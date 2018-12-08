package com.jw.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.exception.DataAccessNotFoundException;
import com.jw.exception.InvalidDataAccessException;
import com.jw.exception.InvalidInputDataException;
import com.jw.model.BlogModel;
import com.jw.repository.BlogRepository;
import com.jw.util.ErrorCode;
import com.jw.util.KSConstants;

@Service
public class BlogServiceImpl implements BlogService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);

	@Autowired
	private BlogRepository homeRepository;
 
	@Autowired
	private ImageManagement imageManagement; 

	@Override
	public BlogModel saveBlog(BlogModel blog) {
		BlogModel blogResponse = null;
		LOGGER.info(
				"Uploading Images from Temp directory, Set Image name to Blog and Delete that image from Temp Directory.");
		imageManagement.uploadBlogImageAndDeleteFromTemp(blog);
		LOGGER.info("New Blog savigng with blog {}", blog);
		blogResponse = homeRepository.insert(blog);
		if (blogResponse != null) {
			LOGGER.error("Blog not saved in mongoDB due to it return null {}", blog);
			throw new InvalidDataAccessException(ErrorCode.KS1108.toString(),
					KSConstants.KS1108.concat(blog.getTech()).concat(blog.getSubTechs().get(0).getSubTech()));
		}
		return blogResponse;
	}

	@Override
	public BlogModel getBlog(String subTech) {
		BlogModel blog = null;
		try {
			LOGGER.debug("Blog fetching with Blog Name :{}", subTech);
		//	blog = homeRepository.findBySubTechs_subTech(subTech)(subTech);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Invalid input for fetching Blog from repository with id :{}", subTech);
			throw new InvalidInputDataException(ErrorCode.KS1105.toString(), KSConstants.KS1105);
		}
		if (blog != null)
			return blog;
		else
			LOGGER.error("Getting blog failed with id {}", subTech);
		throw new DataAccessNotFoundException(ErrorCode.KS1001.toString(), KSConstants.KS1001.concat(subTech));
	}

	@Override
	public List<BlogModel> getAllBlogs() {
		LOGGER.debug("Getting all Blogs");
		return homeRepository.findAll();
	}

	@Override
	public BlogModel updateBlog(BlogModel blog) {
		BlogModel blogResponse = null;
		LOGGER.info(
				"Uploading Images from Temp directory, Set Image name to Blog and Delete that image from Temp Directory.");
		imageManagement.uploadBlogImageAndDeleteFromTemp(blog);
		LOGGER.debug("Updating blog with blog {}", blog);
		blogResponse = homeRepository.save(blog);
		if (blogResponse != null) {
			LOGGER.error("Blog not saved in mongoDB due to it return null {}", blog);
			throw new InvalidDataAccessException(ErrorCode.KS1108.toString(),
					KSConstants.KS1108.concat(blog.getTech()).concat(blog.getSubTechs().get(0).getSubTech()));
		}
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Blog updated Successfully with {}", blog);
		return blogResponse;
	}

	@Override
	public void remove(String id) {
		homeRepository.deleteById(id);
	}

	@Override
	public Map<String, List<String>> getAllBlogTechAndSubTechnologies() {

		return null;
	}

}
